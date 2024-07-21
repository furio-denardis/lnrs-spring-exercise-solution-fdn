package uk.co.furiodenardis.springexercise.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OfficerEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long officerId;

    private String name;
    private String role;
    private String appointedOn;

    @Setter
    @ManyToOne
    @JoinColumn(name = "company_number")
    private CompanyEntity company;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", unique = true)
    private AddressEntity address;
}
