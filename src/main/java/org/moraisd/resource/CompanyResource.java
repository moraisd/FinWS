package org.moraisd.resource;

import jakarta.inject.Inject;
import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Query;
import org.moraisd.domain.Company;
import org.moraisd.graphql.Filter;
import org.moraisd.repository.CompanyRepository;

@GraphQLApi
public class CompanyResource {

  private final String DEFAULT_FILTER = """
      {
          "orderBy":"marketCapitalization",
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
  public List<String> getSymbols() {
    return repository.getAllSymbols();
  }

  @Query
  public List<Company> getCompanies(@DefaultValue(DEFAULT_FILTER) Filter filter) {
    return repository.findByFilter(filter);
  }

  @Query
  public List<String> findMostOutdatedStocks(int limit) {
    return repository.findMostOutdatedStocks(limit);
  }

  @Mutation
  public void persistSymbols(List<Company> companies) {
    repository.persistSymbols(companies);
  }

  @Mutation
  public void updateCompanies(List<Company> companies) {
    repository.updateCompanies(companies);
  }

  @Mutation
  public long deleteBySymbol(@NonNull List<@NonNull String> symbols) {
    return repository.deleteBySymbol(symbols);
  }
}
