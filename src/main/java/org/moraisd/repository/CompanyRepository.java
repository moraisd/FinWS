package org.moraisd.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.moraisd.domain.Company;
import org.moraisd.graphql.Filter;

import java.util.List;

@ApplicationScoped
public class CompanyRepository implements PanacheMongoRepository<Company> {
    public Company findBySymbol(String s) {
        return find("symbol", s).firstResult();
    }

    public List<Company> findByFilter(Filter filter) {
        return listAll(Sort.by(filter.getOrderBy(), Sort.Direction.valueOf((filter.getSortingOrder().name()))));
    }
}
