package com.ftn.isa.repository;

import com.ftn.isa.model.Company;
import com.ftn.isa.model.CompanyReservation;
import com.ftn.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyReservationRepository extends JpaRepository<CompanyReservation, Long> {

    boolean existsByUserAndCompany(User user, Company company);

    boolean existsByUserAndReservation_Administrator(User user, User administrator);
}
