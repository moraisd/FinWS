package org.moraisd.repository;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
class CompanyRepositoryTest {

    @Inject
    CompanyRepository companyRepository;

    @Test
    void testinglol() {
        Log.info(companyRepository.listAll());
    }
}