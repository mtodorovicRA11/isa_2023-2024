package com.ftn.isa.repository;

import com.ftn.isa.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByCompanyIdAndIsAvailable(Long companyId, boolean isAvailable);
}