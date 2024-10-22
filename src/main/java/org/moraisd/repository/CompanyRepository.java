package org.moraisd.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;
import lombok.val;
import org.moraisd.domain.Company;
import org.moraisd.domain.mongodb.CompanySymbol;
import org.moraisd.graphql.Filter;
import org.moraisd.graphql.Filter.FilterBy;

@ApplicationScoped
public class CompanyRepository implements PanacheMongoRepository<Company> {

  public Company findBySymbol(String s) {
    return find("symbol", s).firstResult();
  }

  public long deleteBySymbol(List<String> symbols) {
    return delete("symbol in ?1", symbols);
  }

  public List<String> getAllSymbols() {
    return findAll()
        .project(CompanySymbol.class)
        .stream().map(CompanySymbol::symbol)
        .toList();
  }

  public List<Company> findByFilter(Filter filter) {
    if (filter == null) {
      return listAll();
    }
    val filterByList = filter.getFilterBy();
    val sortBy = Sort.by(filter.getOrderBy(),
        Direction.valueOf((filter.getSortingOrder().name())));

    if (filterByList == null || filterByList.isEmpty()) {
      return listAll(sortBy);
    }

    val query = new StringBuilder();
    val filtersAmount = filterByList.size();
    val params = new Object[filtersAmount];

    for (int index = 0; index < filtersAmount; index++) {
      val filterBy = filterByList.get(index);
      generatePanacheQuery(query, filterBy, index, filtersAmount);
      generateParams(params, filterBy, index);
    }

    return list(query.toString(), sortBy, params);
  }

  private static void generateParams(Object[] params, FilterBy filterBy, int index) {
    val value = filterBy.getValue();
    try {
      params[index] = new BigDecimal(value);
    } catch (NumberFormatException e) {
      params[index] = value;
    }
  }

  private static void generatePanacheQuery(StringBuilder query, FilterBy filterBy, int index,
      int filtersAmount) {
    query.append(filterBy.getField()).append(" ").append(filterBy.getOperator()).append(" ")
        .append("?").append(index + 1);
    if (index != filtersAmount - 1) {
      query.append(" and ");
    }
  }

  public void persistCompanies(List<Company> companies) {

    this.persistOrUpdate(companies);
  }
}
