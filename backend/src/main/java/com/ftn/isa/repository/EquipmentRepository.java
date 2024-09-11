package com.ftn.isa.repository;

import com.ftn.isa.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
        List<Equipment> findAllByCompanyId(Long id);
        List<Equipment> findByNameContainingIgnoreCase(String name);

}
