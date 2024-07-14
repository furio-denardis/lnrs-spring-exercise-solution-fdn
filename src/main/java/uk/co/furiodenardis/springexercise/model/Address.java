package uk.co.furiodenardis.springexercise.model;

import lombok.Builder;
import lombok.Getter;
import uk.co.furiodenardis.springexercise.gateway.response.ApiAddressDto;

@Builder
@Getter
public class Address {
    private String locality;
    private String postalCode;
    private String premises;
    private String addressLine1;
    private String country;

    static Address mapFromApi(ApiAddressDto addressDto) {
        return builder()
                .locality(addressDto.getLocality())
                .addressLine1(addressDto.getAddress_line_1())
                .postalCode(addressDto.getPostal_code())
                .premises(addressDto.getPremises())
                .country(addressDto.getCountry())
                .build();
    }
}
