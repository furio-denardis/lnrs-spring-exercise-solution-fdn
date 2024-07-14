package uk.co.furiodenardis.springexercise.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uk.co.furiodenardis.springexercise.model.Company;

import java.util.List;

@Getter
@Setter
@Builder
public class SearchCompaniesResponse {
    private int total_results;
    private List<Company> items;
}
