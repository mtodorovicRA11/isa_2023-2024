package com.ftn.isa.service;

import com.ftn.isa.dto.reservation.NewReservationDTO;

public interface ReservationService {
    void createReservation(NewReservationDTO newReservationDTO);

    void cancelReservation(Long reservationId);
}
