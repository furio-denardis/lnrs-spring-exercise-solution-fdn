package uk.co.furiodenardis.springexercise.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
public class Company {

    public static final String ACTIVE = "active";

    private String companyNumber;
    private String companyType;
    private String title;
    private String companyStatus;
    private String dateOfCreation;
    private Address address;
    @Setter
    private List<Officer> officers;

}
