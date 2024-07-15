package org.moraisd.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.moraisd.domain.Company;

@ApplicationScoped
public class CompanyRepository implements PanacheMongoRepository<Company> {

}
