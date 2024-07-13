package uk.co.furiodenardis.springexercise.gateway.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ApiCompanyDto {
    private String company_number;
    private String company_type;
    private String title;
    private String company_status;
    private String date_of_creation;
    private ApiAddressDto address;
}
