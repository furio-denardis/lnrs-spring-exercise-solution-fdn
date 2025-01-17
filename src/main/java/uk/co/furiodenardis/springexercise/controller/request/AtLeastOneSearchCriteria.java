package uk.co.furiodenardis.springexercise.controller.request;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneSearchCriteriaValidator.class)
public @interface AtLeastOneSearchCriteria {
    String message() default "At least one of companyName or companyNumber must be provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
