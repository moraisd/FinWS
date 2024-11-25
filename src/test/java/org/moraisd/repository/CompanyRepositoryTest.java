package org.moraisd.repository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.UpdateOneModel;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import java.math.BigDecimal;
import java.util.List;
import lombok.val;
import org.bson.BsonString;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.moraisd.domain.Company;
import org.moraisd.graphql.Filter;
import org.moraisd.graphql.Filter.SortingOrder;

@QuarkusTest
class CompanyRepositoryTest {

  @InjectMock
  CompanyRepository companyRepository;


  @Test
  void shouldCallFindSymbolOnce() {
    val value = anyString();
    when(companyRepository.findBySymbol(value)).thenCallRealMethod();
    when(companyRepository.find("symbol", value)).thenReturn(mock());

    companyRepository.findBySymbol(value);

    verify(companyRepository).find("symbol", value);
  }

  @Test
  void shouldListAllDefaultFilter() {
    val filter = new Filter("marketCapitalization", SortingOrder.Descending,
        null);
    when(companyRepository.findByFilter(filter)).thenCallRealMethod();

    companyRepository.findByFilter(filter);

    val sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);
    verify(companyRepository).listAll(sortArgumentCaptor.capture());
    val column = sortArgumentCaptor.getValue().getColumns().getFirst();
    assertEquals("marketCapitalization", column.getName());
    assertEquals(Sort.Direction.Descending, column.getDirection());
  }

  @Test
  void bulkWriteStocksAndInsertNewLastUpdate() {
    List<Company> companies = generateCompanies();
    doCallRealMethod().when(companyRepository).updateCompanies(companies);

    val mongoCollectionMock = mock(MongoCollection.class, RETURNS_DEEP_STUBS);

    when(companyRepository.mongoCollection()).thenReturn(
        mongoCollectionMock);

    companyRepository.updateCompanies(companies);

    val operations = ArgumentCaptor.forClass(List.class);
    val bulkWriteOptions = ArgumentCaptor.forClass(BulkWriteOptions.class);
    verify(mongoCollectionMock).bulkWrite(operations.capture(), bulkWriteOptions.capture());

    val mongoFilter1 = ((UpdateOneModel) operations.getValue()
        .getFirst())
        .getFilter()
        .toBsonDocument();

    assertTrue(mongoFilter1
        .containsKey("symbol"));

    assertTrue(mongoFilter1
        .containsValue(new BsonString("V")));

    val mongoFilter2 = ((UpdateOneModel) operations.getValue()
        .get(1))
        .getFilter()
        .toBsonDocument();

    assertTrue(mongoFilter2
        .containsKey("symbol"));

    assertTrue(mongoFilter2
        .containsValue(new BsonString("AAPL")));

    assertFalse(bulkWriteOptions.getValue().isOrdered());

  }

  @Test
  void shouldTestMultipleFilterBy() {
    val filter1Field = "evToEbitda";
    val filter1Operator = "<=";
    val filter1Value = "5.5";

    val filterBy1 = new Filter.FilterBy(filter1Field, filter1Operator, filter1Value);

    val filter2Field = "Sector";
    val filter2Operator = "=";
    val filter2Value = "MANUFACTURING";

    val filterBy2 = new Filter.FilterBy(filter2Field, filter2Operator, filter2Value);

    val filter = new Filter("marketCapitalization", SortingOrder.Descending,
        List.of(filterBy1, filterBy2));

    when(companyRepository.findByFilter(filter)).thenCallRealMethod();
    companyRepository.findByFilter(filter);

    val queryCaptor = ArgumentCaptor.forClass(String.class);
    val sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);
    val paramsArgumentCaptor = ArgumentCaptor.forClass(Object[].class);
    verify(companyRepository).list(queryCaptor.capture(), sortArgumentCaptor.capture(),
        paramsArgumentCaptor.capture());

    assertEquals(
        filter1Field + " " + filter1Operator + " " + "?1 and " + filter2Field + " "
            + filter2Operator + " " + "?2",
        queryCaptor.getValue());

    val column = sortArgumentCaptor.getValue().getColumns().getFirst();
    assertEquals(Sort.Direction.Descending, column.getDirection());
    assertEquals("marketCapitalization", column.getName());

    val params = new Object[2];
    params[0] = new BigDecimal(filter1Value);
    params[1] = filter2Value;
    assertArrayEquals(params, paramsArgumentCaptor.getValue());

  }

  @Test
  void deleteBySymbol() {
    val symbols = generateCompanies().stream().map(Company::getSymbol).toList();
    when(companyRepository.deleteBySymbol(
        symbols)).thenCallRealMethod();

    companyRepository.deleteBySymbol(symbols);

    verify(companyRepository).delete("symbol in ?1", symbols);

  }

  @Test
  void findMostOutdatedStocks() {
    int limit = 10;
    when(companyRepository.findMostOutdatedStocks(anyInt())).thenCallRealMethod();

    PanacheQuery<Company> panacheQueryMock = mock(RETURNS_MOCKS);
    when(companyRepository.findAll(any(Sort.class))).thenReturn(
        panacheQueryMock);

    companyRepository.findMostOutdatedStocks(limit);

    val sortByCaptor = ArgumentCaptor.forClass(Sort.class);
    val limitIndexCaptor = ArgumentCaptor.forClass(Integer.class);

    verify(companyRepository).findAll(sortByCaptor.capture());

    verify(panacheQueryMock).range(eq(0), limitIndexCaptor.capture());

    val column = sortByCaptor.getValue().getColumns().getFirst();
    assertEquals("lastUpdated", column.getName());
    assertEquals(Direction.Ascending, column.getDirection());

    assertEquals(limit - 1, limitIndexCaptor.getValue());


  }

  private static List<Company> generateCompanies() {
    val company1 = new Company();
    company1.setSymbol("V");

    val company2 = new Company();
    company2.setSymbol("AAPL");

    return List.of(company1, company2);
  }

}
