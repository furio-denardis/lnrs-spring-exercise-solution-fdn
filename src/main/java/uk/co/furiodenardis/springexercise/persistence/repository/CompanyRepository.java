package uk.co.furiodenardis.springexercise.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.furiodenardis.springexercise.persistence.entity.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {


}
