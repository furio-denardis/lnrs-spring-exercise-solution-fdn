package uk.co.furiodenardis.springexercise.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.furiodenardis.springexercise.controller.request.SearchCompanyRequest;
import uk.co.furiodenardis.springexercise.controller.response.SearchCompaniesResponse;
import uk.co.furiodenardis.springexercise.model.Company;
import uk.co.furiodenardis.springexercise.service.CompanySearch;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    public static final String API_KEY = "api-key";
    public static final String ACME_REG_NO = "12345";
    public static final String ACME = "acme";
    @Mock
    private CompanySearch companySearchService;

    @InjectMocks
    private SearchController searchController;

    @Test
    public void testSearchCompanyByName() throws Exception {
        List<Company> companies = getListOfCompanies();

        when(companySearchService.searchCompany(API_KEY, ACME, false))
                .thenReturn(companies);

        SearchCompanyRequest request = new SearchCompanyRequest(ACME, null);
        SearchCompaniesResponse response = searchController.searchCompanies(API_KEY, false, request);

        assertEquals(companies, response.getItems());
        assertEquals(companies.size(), response.getTotal_results());
    }

    @Test
    public void testSearchCompanyByRegistrationNumber() {
        List<Company> companies = getListOfCompanies();

        when(companySearchService.searchCompany(API_KEY, ACME_REG_NO, false))
                .thenReturn(companies);

        SearchCompanyRequest request = new SearchCompanyRequest(null, ACME_REG_NO);
        SearchCompaniesResponse response = searchController.searchCompanies(API_KEY, false, request);

        assertEquals(companies, response.getItems());
        assertEquals(companies.size(), response.getTotal_results());
    }

    @Test
    public void testSearchCompanyByRegistrationNumberAndName() {
        List<Company> companies = getListOfCompanies();

        when(companySearchService.searchCompany(API_KEY, ACME_REG_NO, false))
                .thenReturn(companies);

        SearchCompanyRequest request = new SearchCompanyRequest(ACME, ACME_REG_NO);
        SearchCompaniesResponse response = searchController.searchCompanies(API_KEY, false, request);

        assertEquals(companies, response.getItems());
        assertEquals(companies.size(), response.getTotal_results());
    }

    @Test
    public void testSearchCompanyActiveOnlyTrue() {
        List<Company> companies = getListOfCompanies();

        when(companySearchService.searchCompany(API_KEY, ACME_REG_NO, true))
                .thenReturn(companies);

        SearchCompanyRequest request = new SearchCompanyRequest(ACME, ACME_REG_NO);
        SearchCompaniesResponse response = searchController.searchCompanies(API_KEY, true, request);

        assertEquals(companies, response.getItems());
        assertEquals(companies.size(), response.getTotal_results());
    }

    private List<Company> getListOfCompanies() {
        return List.of(Company.builder()
                .title(ACME)
                .companyStatus("active")
                .companyType("co")
                .companyNumber(ACME_REG_NO)
                .dateOfCreation("1938-09-30")
                .build());
    }


}
