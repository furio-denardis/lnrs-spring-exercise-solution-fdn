package uk.co.furiodenardis.springexercise.controller.request;

import lombok.Data;

import java.util.Objects;

@Data
@AtLeastOneSearchCriteria
public class SearchCompanyRequest {
    private String companyName;
    private String companyNumber;

    public boolean atLeastOneSearchCriteria() {
        return (Objects.nonNull(companyName) && !companyName.trim().isEmpty())
            || (Objects.nonNull(companyNumber) && !companyNumber.trim().isEmpty());
    }
}
