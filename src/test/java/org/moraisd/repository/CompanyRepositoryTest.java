package org.moraisd.repository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.quarkus.panache.common.Sort;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.moraisd.graphql.Filter;

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
    val filter = generateDefaultFilter();
    when(companyRepository.findByFilter(filter)).thenCallRealMethod();

    companyRepository.findByFilter(filter);

    val sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);
    verify(companyRepository).listAll(sortArgumentCaptor.capture());
    val column = sortArgumentCaptor.getValue().getColumns().getFirst();
    assertEquals("MarketCapitalization", column.getName());
    assertEquals(Sort.Direction.Descending, column.getDirection());
  }

  @Test
  void shouldTestMultipleFilterBy() {
    val filter1Field = "EVToEBITDA";
    val filter1Operator = "<=";
    val filter1Value = "5.5";
    val filterBy1 = generateFilterBy(filter1Field, filter1Operator, filter1Value);

    val filter2Field = "Sector";
    val filter2Operator = "=";
    val filter2Value = "MANUFACTURING";
    val filterBy2 = generateFilterBy(filter2Field, filter2Operator, filter2Value);

    val filter = generateDefaultFilter();
    filter.setFilterBy(List.of(filterBy1, filterBy2));

    when(companyRepository.findByFilter(filter)).thenCallRealMethod();
    companyRepository.findByFilter(filter);

    val queryCaptor = ArgumentCaptor.forClass(String.class);
    val sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);
    val paramsArgumentCaptor = ArgumentCaptor.forClass(Object[].class);
    verify(companyRepository).list(queryCaptor.capture(), sortArgumentCaptor.capture(),
        paramsArgumentCaptor.capture());

    assertEquals(
        filter1Field + filter1Operator + "?1 and " + filter2Field + filter2Operator + "?2",
        queryCaptor.getValue());

    val column = sortArgumentCaptor.getValue().getColumns().getFirst();
    assertEquals(Sort.Direction.Descending, column.getDirection());
    assertEquals("MarketCapitalization", column.getName());

    val params = new Object[2];
    params[0] = Double.parseDouble(filter1Value);
    params[1] = filter2Value;
    assertArrayEquals(params, paramsArgumentCaptor.getValue());

  }

  private static Filter generateDefaultFilter() {
    val filter = new Filter();
    filter.setOrderBy("MarketCapitalization");
    filter.setSortingOrder(Filter.SortingOrder.Descending);
    return filter;
  }

  private static Filter.FilterBy generateFilterBy(String field, String operator, String value) {
    val filterBy = new Filter.FilterBy();
    filterBy.setField(field);
    filterBy.setOperator(operator);
    filterBy.setValue(value);
    return filterBy;

  }

}
