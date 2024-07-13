package uk.co.furiodenardis.springexercise.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import uk.co.furiodenardis.springexercise.gateway.TruProxyAPI;
import uk.co.furiodenardis.springexercise.model.Company;
import uk.co.furiodenardis.springexercise.service.CompanySearch;

import java.util.List;

@RestController
public class SearchController {

    private final CompanySearch companySearch;

    public SearchController(CompanySearch companySearch) {
        this.companySearch = companySearch;
    }

    @GetMapping("/companies/search")
    public List<Company> searchCompanies(@RequestHeader(name = TruProxyAPI.apiKeyHeader) String apiKey,
                                         @RequestParam(name = "Query") String searchText,
                                         @RequestParam(name = "ActiveOnly", defaultValue = "False") Boolean activeOnly) {


        return companySearch.searchCompany(apiKey, searchText, activeOnly);
    }

}
