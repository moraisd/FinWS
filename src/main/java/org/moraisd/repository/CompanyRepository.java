package org.moraisd.repository;

import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import lombok.val;
import org.bson.Document;
import org.moraisd.domain.Company;
import org.moraisd.domain.mongodb.CompanySymbol;
import org.moraisd.graphql.Filter;
import org.moraisd.graphql.Filter.FilterBy;

@ApplicationScoped
public class CompanyRepository implements ReactivePanacheMongoRepository<Company> {

  public Uni<Company> findBySymbol(String s) {
    return find("symbol", s).firstResult();
  }

  public Uni<Long> deleteBySymbol(List<String> symbols) {
    return delete("symbol in ?1", symbols);
  }

  public Uni<List<String>> getAllSymbols() {
    return findAll()
        .project(CompanySymbol.class)
        .stream()
        .map(CompanySymbol::symbol)
        .collect()
        .asList();
  }

  public Uni<List<Company>> findByFilter(Filter filter) {
    if (filter == null) {
      return listAll();
    }
    val filterByList = filter.filterBy();
    val sortBy = Sort.by(filter.orderBy(),
        Direction.valueOf((filter.sortingOrder().name())));

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

  private static void generatePanacheQuery(StringBuilder query, FilterBy filterBy, int index,
      int filtersAmount) {
    query.append(filterBy.field())
        .append(" ")
        .append(filterBy.operator())
        .append(" ")
        .append("?").append(index + 1);
    if (index < filtersAmount - 1) {
      query.append(" and ");
    }
  }

  private static void generateParams(Object[] params, FilterBy filterBy, int index) {
    val value = filterBy.value();
    try {
      params[index] = new BigDecimal(value);
    } catch (NumberFormatException | NullPointerException e) {
      params[index] = value;
    }
  }

  public void updateCompanies(List<Company> companies) {
    val operations = new ArrayList<WriteModel<Company>>();

    for (val company : companies) {
      company.setLastUpdated(LocalDateTime.now(ZoneOffset.UTC));
      operations.add(
          new UpdateOneModel<>(Filters.eq("symbol", company.getSymbol()),
              new Document("$set", company)));
    }

    this.mongoCollection().bulkWrite(operations, new BulkWriteOptions().ordered(false));
  }

  public void persistSymbols(List<Company> companyList) {
    this.persist(companyList);
  }

  public Uni<List<String>> findMostOutdatedStocks(int limit) {
    return this.findAll(Sort.by("lastUpdated", Direction.Ascending))
        .range(0, limit - 1)
        .project(CompanySymbol.class)
        .stream()
        .map(CompanySymbol::symbol)
        .collect()
        .asList();
  }
}
