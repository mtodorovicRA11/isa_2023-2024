package com.ftn.isa.controller;

import com.ftn.isa.dto.reservation.NewReservationDTO;
import com.ftn.isa.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelReservation(@RequestParam Long reservationId, @RequestParam Long userId) {
        try {
            reservationService.cancelReservation(reservationId, userId);
            return ResponseEntity.ok("Reservation canceled successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
