package org.moraisd.resource;

import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import org.moraisd.domain.Company;
import org.moraisd.repository.CompanyRepository;

import java.util.List;

@GraphQLApi
public class CompanyResource {

    private final CompanyRepository repository;

    @Inject
    public CompanyResource(CompanyRepository repository) {
        this.repository = repository;
    }

    @Query
    public Company getCompany(@Name("symbol") String symbol) {
        return repository.findBySymbol(symbol);
    }

    @Query public List<Company> getAllCompanies(){
        return repository.listAll();
    }
}
