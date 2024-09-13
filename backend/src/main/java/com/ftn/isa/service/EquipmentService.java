package com.ftn.isa.service;

import com.ftn.isa.dto.reservation.NewReservationDTO;
import com.ftn.isa.model.Equipment;
import com.ftn.isa.model.Reservation;
import com.ftn.isa.model.TimeSlot;
import com.ftn.isa.model.User;

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

    List<TimeSlot> getAvailableTimeslots(Long companyId);

    boolean reserveEquipment(NewReservationDTO newReservationDTO, User user, Long companyId);

    List<Reservation> getReservationsByUserId(Long userId);
}
