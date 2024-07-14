package uk.co.furiodenardis.springexercise.model;

import lombok.Builder;
import lombok.Getter;
import uk.co.furiodenardis.springexercise.gateway.response.ApiOfficerDto;

@Builder
@Getter
public class Officer {
    private String name;
    private String role;
    private String appointedOn;
    private Address address;

    public static Officer mapFromApi(final ApiOfficerDto officerDto) {
        return Officer.builder()
                .name(officerDto.getName())
                .appointedOn(officerDto.getAppointed_on())
                .role(officerDto.getOfficer_role())
                .address(Address.mapFromApi(officerDto.getAddress()))
                .build();
    }
}
