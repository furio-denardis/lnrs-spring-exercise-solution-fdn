package uk.co.furiodenardis.springexercise.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CompanyEntity {

    @Id
    private String companyNumber;
    private String companyType;
    private String title;
    private String companyStatus;
    private String dateOfCreation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", unique = true)
    private AddressEntity address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<OfficerEntity> officerEntityList;

    public void addOfficer(OfficerEntity officer) {
        if (officerEntityList == null) {
            officerEntityList = new ArrayList<>();
        }
        officerEntityList.add(officer);
        officer.setCompany(this);
    }
}
