package uk.co.furiodenardis.springexercise.gateway.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ApiAddressDto {
    private String locality;
    private String postal_code;
    private String premises;
    private String address_line_1;
    private String country;

}
