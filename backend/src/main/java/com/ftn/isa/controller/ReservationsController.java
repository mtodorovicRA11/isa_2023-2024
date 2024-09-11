package com.ftn.isa.controller;

import com.ftn.isa.dto.reservation.NewReservationDTO;
import com.ftn.isa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationsController {

    @Autowired
    ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<String> createReservation(NewReservationDTO newReservationDTO) {
        try {
            reservationService.createReservation(newReservationDTO);
            return ResponseEntity.ok("Reservation booked successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cancel/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        try {
            reservationService.cancelReservation(reservationId);
            return ResponseEntity.ok("Reservation canceled successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
