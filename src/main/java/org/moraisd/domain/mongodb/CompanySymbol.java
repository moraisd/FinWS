package org.moraisd.domain.mongodb;

import io.quarkus.mongodb.panache.common.ProjectionFor;
import org.moraisd.domain.Company;

@ProjectionFor(Company.class)
public record CompanySymbol(String symbol) {

}
