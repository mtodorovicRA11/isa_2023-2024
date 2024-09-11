package com.ftn.isa.service;

import com.ftn.isa.dto.email.EmailDetailsDTO;
import com.ftn.isa.dto.reservation.NewReservationDTO;
import com.ftn.isa.model.Reservation;
import com.ftn.isa.model.User;
import com.ftn.isa.repository.ReservationRepository;
import com.ftn.isa.repository.UserRepository;
import com.ftn.isa.service.impl.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    QRCodeService qrCodeService;

    Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Override
    public void createReservation(NewReservationDTO newReservationDTO) {
        Reservation reservation = reservationRepository.findById(newReservationDTO.getReservationId()).orElseThrow(() -> new RuntimeException(
                "Reservation not found!"));
        User user = userRepository.findById(newReservationDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found!"));

        if (!reservation.isFree()) {
            throw new RuntimeException("Reservation is already booked!");
        }

        boolean hasConflict = reservationRepository.existsByCompanyAndStartTimeAndUserAndIsFreeFalse(reservation.getCompany(),
                reservation.getStartTime(), user);
        if (hasConflict) {
            throw new RuntimeException("You cannot book the same reservation twice in the same company!");
        }

        reservation.setFree(false);
        reservation.setUser(user);

        reservationRepository.save(reservation);

        sendConfirmationEmail(user, reservation);
    }

    @Override
    public void cancelReservation(Long reservationId, Long userId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation not found!"));

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

        if (!reservation.getUser().equals(user)) {
            throw new RuntimeException("You can only cancel your own reservations!");
        }

        long hoursUntilReservation = Duration.between(LocalDateTime.now(), reservation.getStartTime()).toHours();
        int penalty = (hoursUntilReservation < 24) ? 2 : 1;

        user.setPenaltyPoints(user.getPenaltyPoints() + penalty);
        reservation.setFree(true);
        reservation.setUser(null);

        reservationRepository.save(reservation);
        userRepository.save(user);
    }

    private void sendConfirmationEmail(User user, Reservation reservation) {
        try {
            String qrCodeImage = qrCodeService.generateQRCode(user, reservation);

            EmailDetailsDTO details = new EmailDetailsDTO();
            details.setRecipient(user.getEmail());
            details.setSubject("Reservation Confirmation");
            details.setMessageBody("<p>Your reservation has been confirmed. See the attached QR code.</p><img src='cid:qrImage' />");

            emailService.sendEmailWithAttachment(details, qrCodeImage);
        } catch (Exception e) {
            throw new RuntimeException("Confirmation email not sent!");
        }
    }
}
