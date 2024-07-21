package uk.co.furiodenardis.springexercise.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.co.furiodenardis.springexercise.exceptions.CompanyNotFoundException;
import uk.co.furiodenardis.springexercise.gateway.TruProxyAPI;
import uk.co.furiodenardis.springexercise.model.Company;
import uk.co.furiodenardis.springexercise.model.CompanyMapper;
import uk.co.furiodenardis.springexercise.model.Officer;
import uk.co.furiodenardis.springexercise.model.OfficerMapper;
import uk.co.furiodenardis.springexercise.persistence.entity.CompanyEntity;
import uk.co.furiodenardis.springexercise.persistence.repository.CompanyRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanySearch {

    private final TruProxyAPI truProxyAPI;

    private final CompanyRepository companyRepository;

    public List<Company> searchCompany(final String apiKey, final String searchText, boolean activeOnly) {
        Predicate<Company> activeCompanyPredicate = activeOnly ?
            company -> Company.ACTIVE.equals(company.getCompanyStatus()) : company -> true;

        List<Company> listOfCompanies = truProxyAPI.searchCompanies(apiKey, searchText)
            .stream()
            .map(CompanyMapper::mapFromApi)
            .filter(activeCompanyPredicate)
            .map(company -> setOfficers(apiKey, company))
            .toList();

        listOfCompanies.stream()
                .forEach(c -> saveCompany(c));

        return listOfCompanies;
    }

    public Company getCompanyByNumber(final String searchText) {

        Optional<CompanyEntity> companyEntity = companyRepository.findById(searchText);
        return companyEntity.map(CompanyMapper::mapFromEntity)
                .orElseThrow(() -> new CompanyNotFoundException("not found: " + searchText));
    }

    private void saveCompany(final Company company) {
        CompanyEntity saved = companyRepository.save(CompanyMapper.mapToEntity(company));
        log.info("Saved company {} to db", saved.getTitle());
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
            .map(OfficerMapper::mapFromApi)
            .toList();
    }

}
