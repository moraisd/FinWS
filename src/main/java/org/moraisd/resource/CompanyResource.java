package org.moraisd.resource;

import jakarta.inject.Inject;
import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.moraisd.domain.Company;
import org.moraisd.graphql.Filter;
import org.moraisd.repository.CompanyRepository;

@GraphQLApi
public class CompanyResource {

  private final String DEFAULT_FILTER = """
      {
          "orderBy":"MarketCapitalization",
          "sortingOrder":"Descending"
      }""";

private final CompanyRepository repository;

  @Inject
  public CompanyResource(CompanyRepository repository) {
    this.repository = repository;
  }

  @Query
  public Company getCompany(String symbol) {
    return repository.findBySymbol(symbol);
  }

  @Query
  public List<Company> getCompanies(@DefaultValue(DEFAULT_FILTER) Filter filter) {
    return repository.findByFilter(filter);
  }

  @Mutation
  public void persistStocks(List<Company> stock) {
    repository.persist(stock);
  }
}
