package uk.co.furiodenardis.springexercise.model;

import uk.co.furiodenardis.springexercise.gateway.response.ApiOfficerDto;
import uk.co.furiodenardis.springexercise.persistence.entity.AddressEntity;
import uk.co.furiodenardis.springexercise.persistence.entity.OfficerEntity;

import java.util.List;
import java.util.stream.Collectors;

public class OfficerMapper {
    public static Officer mapFromApi(final ApiOfficerDto officerDto) {
        return Officer.builder()
                .name(officerDto.getName())
                .appointedOn(officerDto.getAppointed_on())
                .role(officerDto.getOfficer_role())
                .address(AddressMapper.mapFromApi(officerDto.getAddress()))
                .build();
    }

    public static OfficerEntity mapToEntity(final Officer officer) {
        AddressEntity addressEntity = AddressMapper.mapToEntity(officer.getAddress());
        return OfficerEntity.builder()
                .address(addressEntity)
                .name(officer.getName())
                .role(officer.getRole())
                .appointedOn(officer.getAppointedOn())
                .build();
    }

    public static List<OfficerEntity> mapToEntityList(final List<Officer> officers) {
        return officers.stream()
                .map(OfficerMapper::mapToEntity)
                .collect(Collectors.toList());
    }

    public static Officer mapFromEntity(final OfficerEntity officerEntity) {
        Address addressEntity = AddressMapper.mapFromEntity(officerEntity.getAddress());
        return Officer.builder()
                .address(addressEntity)
                .name(officerEntity.getName())
                .role(officerEntity.getRole())
                .appointedOn(officerEntity.getAppointedOn())
                .build();
    }

    public static List<Officer> mapFromEntityList(final List<OfficerEntity> officers) {
        return officers.stream()
                .map(OfficerMapper::mapFromEntity)
                .collect(Collectors.toList());
    }
}
