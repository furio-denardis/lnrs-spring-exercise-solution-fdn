package uk.co.furiodenardis.springexercise.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Officer {
    private String name;
    private String role;
    private String appointedOn;
    private Address address;

}
