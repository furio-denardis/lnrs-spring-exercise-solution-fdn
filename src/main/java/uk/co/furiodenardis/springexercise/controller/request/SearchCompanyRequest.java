package uk.co.furiodenardis.springexercise.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
@AtLeastOneSearchCriteria
public class SearchCompanyRequest {
    private String companyName;
    private String companyNumber;

    public boolean atLeastOneSearchCriteria() {
        return (Objects.nonNull(companyName) && !companyName.trim().isEmpty())
            || (Objects.nonNull(companyNumber) && !companyNumber.trim().isEmpty());
    }
}
