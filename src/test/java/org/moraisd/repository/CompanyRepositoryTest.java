package org.moraisd.repository;

import io.quarkus.panache.common.Sort;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.moraisd.domain.Company;
import org.moraisd.graphql.Filter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@QuarkusTest
class CompanyRepositoryTest {

    @InjectMock
    CompanyRepository companyRepository;

    private static Filter generateDefaultFilter() {
        val filter = new Filter();
        filter.setOrderBy("MarketCapitalization");
        filter.setSortingOrder(Filter.SortingOrder.Descending);
        return filter;
    }

    @Test
    void shouldReturnSingleCompanyBySymbol() {
        val expected = new Company();
        val symbol = anyString();
        when(companyRepository.findBySymbol(symbol)).thenReturn(expected);

        val result = companyRepository.findBySymbol(symbol);

        verify(companyRepository, atMostOnce()).findBySymbol(symbol);
        verifyNoMoreInteractions(companyRepository);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void returnAllStocksWithDefaultFilter() {
        val filter = generateDefaultFilter();
        when(companyRepository.findByFilter(filter)).thenCallRealMethod();

        companyRepository.findByFilter(filter);

        ArgumentCaptor<Sort> sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);
        verify(companyRepository).listAll(sortArgumentCaptor.capture());
        val column = sortArgumentCaptor.getValue().getColumns().getFirst();
        Assertions.assertEquals("MarketCapitalization", column.getName());
        Assertions.assertEquals(Sort.Direction.Descending, column.getDirection());
    }
}
