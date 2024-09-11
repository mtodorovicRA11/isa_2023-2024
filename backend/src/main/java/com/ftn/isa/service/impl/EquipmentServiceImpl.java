package com.ftn.isa.service.impl;

import com.ftn.isa.model.Equipment;
import com.ftn.isa.model.TimeSlot;
import com.ftn.isa.repository.EquipmentRepository;
import com.ftn.isa.repository.TimeSlotRepository;
import com.ftn.isa.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private TimeSlotRepository timeslotRepository;

    public List<TimeSlot> getAvailableTimeslots(Long equipmentId) {
        return timeslotRepository.findByEquipmentIdAndIsAvailable(equipmentId, true);
    }

    public boolean reserveEquipment(Long equipmentId, Long timeslotId) {
        TimeSlot timeslot = timeslotRepository.findById(timeslotId).orElseThrow();
        if (!timeslot.isAvailable()) {
            return false;
        }
        timeslot.setAvailable(false);
        timeslotRepository.save(timeslot);
        return true;
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public Optional<Equipment> getEquipmentById(Long id) {
        return equipmentRepository.findById(id);
    }

    public Equipment saveEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public Equipment updateEquipment(Long id, Equipment updatedEquipment) {
        return equipmentRepository.findById(id).map(equipment -> {
            equipment.setName(updatedEquipment.getName());
            equipment.setAmount(updatedEquipment.getAmount());
            return equipmentRepository.save(equipment);
        }).orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + id));
    }

    public void deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
    }

    @Override
    public List<Equipment> getAllEquipmentForCompany(Long id) {
        return equipmentRepository.findAllByCompanyId(id);
    }

    public List<Equipment> searchEquipmentByName(String name) {
        return equipmentRepository.findByNameContainingIgnoreCase(name);
    }
}
