package uk.co.furiodenardis.springexercise.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uk.co.furiodenardis.springexercise.gateway.response.ApiCompanyDto;

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

    public static Company mapFromApi(final ApiCompanyDto companyDto) {
        return Company.builder()
                .companyNumber(companyDto.getCompany_number())
                .companyStatus(companyDto.getCompany_status())
                .companyType(companyDto.getCompany_type())
                .title(companyDto.getTitle())
                .dateOfCreation(companyDto.getDate_of_creation())
                .address(Address.mapFromApi(companyDto.getAddress()))
                .build();

    }

}
