package uk.co.furiodenardis.springexercise.model;

import uk.co.furiodenardis.springexercise.gateway.response.ApiCompanyDto;
import uk.co.furiodenardis.springexercise.persistence.entity.AddressEntity;
import uk.co.furiodenardis.springexercise.persistence.entity.CompanyEntity;
import uk.co.furiodenardis.springexercise.persistence.entity.OfficerEntity;

import java.util.List;

import static uk.co.furiodenardis.springexercise.model.OfficerMapper.mapFromEntityList;

public class CompanyMapper {
    public static Company mapFromApi(final ApiCompanyDto companyDto) {
        return Company.builder()
                .companyNumber(companyDto.getCompany_number())
                .companyStatus(companyDto.getCompany_status())
                .companyType(companyDto.getCompany_type())
                .title(companyDto.getTitle())
                .dateOfCreation(companyDto.getDate_of_creation())
                .address(AddressMapper.mapFromApi(companyDto.getAddress()))
                .build();
    }

    public static CompanyEntity mapToEntity(final Company company) {

        AddressEntity addressEntity = AddressMapper.mapToEntity(company.getAddress());
        List<OfficerEntity> officerEntityList = OfficerMapper.mapToEntityList(company.getOfficers());

        CompanyEntity companyEntity = CompanyEntity.builder()
                .companyNumber(company.getCompanyNumber())
                .companyStatus(company.getCompanyStatus())
                .title(company.getTitle())
                .dateOfCreation(company.getDateOfCreation())
                .companyType(company.getCompanyType())
                .address(addressEntity)
                .build();

        officerEntityList.stream().forEach(officer -> companyEntity.addOfficer(officer));

        return companyEntity;

    }

    public static Company mapFromEntity(final CompanyEntity companyEntity) {

        Address address = AddressMapper.mapFromEntity(companyEntity.getAddress());
        List<Officer> officerList = mapFromEntityList(companyEntity.getOfficerEntityList());

        return  Company.builder()
                .companyNumber(companyEntity.getCompanyNumber())
                .companyStatus(companyEntity.getCompanyStatus())
                .title(companyEntity.getTitle())
                .dateOfCreation(companyEntity.getDateOfCreation())
                .companyType(companyEntity.getCompanyType())
                .address(address)
                .officers(officerList)
                .build();

    }
}
