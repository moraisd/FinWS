package org.moraisd.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.val;
import org.moraisd.repository.CompanyRepository;

@ApplicationScoped
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Inject
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;

    }

    public void testingRepository() {
//        val result = companyRepository.findAll();
    }
}
