package uk.co.furiodenardis.springexercise.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.co.furiodenardis.springexercise.gateway.TruProxyAPI;
import uk.co.furiodenardis.springexercise.model.Company;
import uk.co.furiodenardis.springexercise.model.Officer;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanySearch {

    private final TruProxyAPI truProxyAPI;

    public List<Company> searchCompany(final String apiKey, final String searchText, boolean activeOnly) {
        Predicate<Company> activeCompanyPredicate = activeOnly ?
            company -> Company.ACTIVE.equals(company.getCompanyStatus()) : company -> true;

        return truProxyAPI.searchCompanies(apiKey, searchText)
            .stream()
            .map(Company::mapFromApi)
            .filter(activeCompanyPredicate)
            .map(company -> setOfficers(apiKey, company))
            .toList();
    }

    private Company setOfficers(final String apiKey, Company company) {
        List<Officer> officers = listActiveOfficers(apiKey, company.getCompanyNumber());
        company.setOfficers(officers);
        return company;
    }

    private List<Officer> listActiveOfficers(final String apiKey, final String companyNumber) {
        log.info("Getting officers for company no. " + companyNumber);
        return truProxyAPI.getOfficers(apiKey,companyNumber)
            .stream()
            .filter(officer -> Objects.isNull(officer.getResigned_on()))
            .map(Officer::mapFromApi)
            .toList();
    }

}
