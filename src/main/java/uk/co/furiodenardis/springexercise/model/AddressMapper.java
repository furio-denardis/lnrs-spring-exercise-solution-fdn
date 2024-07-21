package uk.co.furiodenardis.springexercise.model;

import uk.co.furiodenardis.springexercise.gateway.response.ApiAddressDto;
import uk.co.furiodenardis.springexercise.persistence.entity.AddressEntity;

public class AddressMapper {
    static Address mapFromApi(final ApiAddressDto addressDto) {
        return Address.builder()
                .locality(addressDto.getLocality())
                .addressLine1(addressDto.getAddress_line_1())
                .postalCode(addressDto.getPostal_code())
                .premises(addressDto.getPremises())
                .country(addressDto.getCountry())
                .build();
    }

    static AddressEntity mapToEntity(final Address address) {
        return AddressEntity.builder()
                .addressLine1(address.getAddressLine1())
                .country(address.getCountry())
                .locality(address.getLocality())
                .postalCode(address.getPostalCode())
                .premises(address.getPremises())
                .build();
    }

    static Address mapFromEntity(final AddressEntity addressEntity) {
        return Address.builder()
                .addressLine1(addressEntity.getAddressLine1())
                .country(addressEntity.getCountry())
                .locality(addressEntity.getLocality())
                .postalCode(addressEntity.getPostalCode())
                .premises(addressEntity.getPremises())
                .build();
    }

}
