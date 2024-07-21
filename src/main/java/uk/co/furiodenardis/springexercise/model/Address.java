package uk.co.furiodenardis.springexercise.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Address {
    private String locality;
    private String postalCode;
    private String premises;
    private String addressLine1;
    private String country;

}
