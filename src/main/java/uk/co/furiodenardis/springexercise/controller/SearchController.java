package uk.co.furiodenardis.springexercise.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import uk.co.furiodenardis.springexercise.controller.request.SearchCompanyRequest;
import uk.co.furiodenardis.springexercise.controller.response.SearchCompaniesResponse;
import uk.co.furiodenardis.springexercise.gateway.TruProxyAPI;
import uk.co.furiodenardis.springexercise.model.Company;
import uk.co.furiodenardis.springexercise.service.CompanySearch;

import java.util.List;
import java.util.Objects;

@RestController
public class SearchController {

    private final CompanySearch companySearch;

    public SearchController(CompanySearch companySearch) {
        this.companySearch = companySearch;
    }

    @GetMapping("/companies/search")
    public SearchCompaniesResponse searchCompanies(@RequestHeader(name = TruProxyAPI.apiKeyHeader) String apiKey,
                                         @RequestParam(name = "Query") String searchText,
                                         @RequestParam(name = "ActiveOnly", defaultValue = "False") Boolean activeOnly) {

        List<Company> companies = companySearch.searchCompany(apiKey, searchText, activeOnly);
        return SearchCompaniesResponse.builder()
                .items(companies)
                .total_results(companies.size())
                .build();

    }

    @PostMapping("/companies/search")
    public SearchCompaniesResponse searchCompanies(@RequestHeader(name = TruProxyAPI.apiKeyHeader) String apiKey,
                                                   @RequestParam(name = "ActiveOnly", defaultValue = "False") Boolean activeOnly,
                                                   @Valid @RequestBody SearchCompanyRequest searchCompanyRequest) {

        String searchText = Objects.isNull(searchCompanyRequest.getCompanyNumber()) ?
                searchCompanyRequest.getCompanyName() :
                searchCompanyRequest.getCompanyNumber();

        List<Company> companies = companySearch.searchCompany(apiKey, searchText, activeOnly);
        return SearchCompaniesResponse.builder()
                .items(companies)
                .total_results(companies.size())
                .build();
    }

}
