package org.moraisd.resource;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.moraisd.graphql.Filter;
import org.moraisd.repository.CompanyRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyResourceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyResource companyResource;

    @Test
    void shouldInvokeRepositoryOnceOnFilter() {
        val filter = generateDefaultFilter();
        when(companyRepository.findByFilter(filter)).thenReturn(any());
        companyResource.getCompanies(filter);
        verify(companyRepository, only()).findByFilter(filter);
    }

    private Filter generateDefaultFilter() {
        return new Filter();
    }
}