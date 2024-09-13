package com.ftn.isa.controller;

import com.ftn.isa.dto.reservation.NewReservationDTO;
import com.ftn.isa.model.Equipment;
import com.ftn.isa.model.Reservation;
import com.ftn.isa.model.TimeSlot;
import com.ftn.isa.model.User;
import com.ftn.isa.service.EquipmentService;
import com.ftn.isa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/equipment", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class EquipmentController {

    @Autowired
    ReservationService reservationService;
    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/all")
    public List<Equipment> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }

    @GetMapping("/company/{id}")
    public List<Equipment> getAllEquipmentByCompany(@PathVariable Long id) {
        return equipmentService.getAllEquipmentForCompany(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id) {
        Optional<Equipment> equipment = equipmentService.getEquipmentById(id);
        return equipment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public Equipment createEquipment(@RequestBody Equipment equipment) {
        return equipmentService.saveEquipment(equipment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable Long id, @RequestBody Equipment equipment) {
        try {
            return ResponseEntity.ok(equipmentService.updateEquipment(id, equipment));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Equipment> searchEquipment(@RequestParam String name) {
        return equipmentService.searchEquipmentByName(name);
    }

    @GetMapping("/{companyId}/timeslots")
    public List<TimeSlot> getAvailableTimeslots(@PathVariable Long companyId) {
        return equipmentService.getAvailableTimeslots(companyId);
    }

    @PostMapping("/reserve")
    public String reserveEquipment(@RequestBody NewReservationDTO newReservationDTO, @RequestParam Long companyId) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = (User) user;

        boolean reserved = equipmentService.reserveEquipment(newReservationDTO, u, companyId);
        return reserved ? "Reservation successful" : "Reservation failed. Timeslot is unavailable.";
    }

    @GetMapping("/reservations")
    public List<Reservation> getTimeslotsByUserId() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = (User) user;
        return equipmentService.getReservationsByUserId(u.getId());
    }
}
