package uk.co.furiodenardis.springexercise.controller.request;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class AtLeastOneSearchCriteriaValidator implements ConstraintValidator<AtLeastOneSearchCriteria, SearchCompanyRequest> {

    @Override
    public boolean isValid(SearchCompanyRequest searchCompanyRequest, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.nonNull(searchCompanyRequest) &&
                searchCompanyRequest.atLeastOneSearchCriteria();
    }
}
