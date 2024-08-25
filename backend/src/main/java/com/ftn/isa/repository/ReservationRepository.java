package com.ftn.isa.repository;

import com.ftn.isa.model.Company;
import com.ftn.isa.model.Reservation;
import com.ftn.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByCompanyAndStartTimeAndUserAndIsFreeFalse(Company company, LocalDateTime startTime, User user);
}
