package com.ftn.isa.repository;

import com.ftn.isa.model.Company;
import com.ftn.isa.model.Reservation;
import com.ftn.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);

    void deleteById(Long id);

    boolean existsByUserAndCompany(User user, Company company);

}
