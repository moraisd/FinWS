package org.moraisd.repository;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.moraisd.domain.Company;

@QuarkusTest
class CompanyRepositoryTest {

    @Inject
    CompanyRepository companyRepository;

    @Test
    void testinglol() {
//        Company company = new Company();
            companyRepository.findBySymbol("DRTSW");
    }
}
