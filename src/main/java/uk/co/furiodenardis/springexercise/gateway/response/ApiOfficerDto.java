package uk.co.furiodenardis.springexercise.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ApiOfficerDto {
    private String name;
    private String officer_role;
    private String appointed_on;
    @JsonProperty(required = false)
    private String resigned_on;
    private ApiAddressDto address;
}
