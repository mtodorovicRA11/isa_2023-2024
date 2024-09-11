package com.ftn.isa.service;

import com.ftn.isa.model.Equipment;
import com.ftn.isa.model.TimeSlot;

import java.util.List;
import java.util.Optional;

public interface EquipmentService {

    List<Equipment> getAllEquipment();

    Optional<Equipment> getEquipmentById(Long id);

    Equipment saveEquipment(Equipment equipment);

    Equipment updateEquipment(Long id, Equipment updatedEquipment);

    void deleteEquipment(Long id);

    List<Equipment> getAllEquipmentForCompany(Long id);

    List<Equipment> searchEquipmentByName(String name);

    List<TimeSlot> getAvailableTimeslots(Long equipmentId);

    boolean reserveEquipment(Long equipmentId, Long timeslotId);
}
