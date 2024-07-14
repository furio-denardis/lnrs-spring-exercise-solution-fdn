package uk.co.furiodenardis.springexercise.controller.request;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AtLeastOneSearchCriteriaValidatorTest {

    @Mock
    private SearchCompanyRequest searchCompanyRequest;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private AtLeastOneSearchCriteriaValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new AtLeastOneSearchCriteriaValidator();
    }

    @Test
    public void shouldReturnFalseWhenSearchCompanyRequestNull() {
        boolean isValid = validator.isValid(null, constraintValidatorContext);
        assertFalse(isValid);
    }

    @Test
    public void shouldReturnFalseWhenSearchCompanyRequestIsNotNullAndIsValidIsTrue() {
        when(searchCompanyRequest.atLeastOneSearchCriteria()).thenReturn(true);
        boolean isValid = validator.isValid(searchCompanyRequest, constraintValidatorContext);
        assertTrue(isValid);
    }

    @Test
    public void shouldReturnFalseWhenSearchCompanyRequestIsNotNullAndIsValidIsFalse() {
        when(searchCompanyRequest.atLeastOneSearchCriteria()).thenReturn(false);
        boolean isValid = validator.isValid(searchCompanyRequest, constraintValidatorContext);
        assertFalse(isValid);
    }
}
