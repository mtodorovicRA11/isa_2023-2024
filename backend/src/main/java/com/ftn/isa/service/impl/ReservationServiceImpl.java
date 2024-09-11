package com.ftn.isa.service.impl;

import com.ftn.isa.dto.email.EmailDetailsDTO;
import com.ftn.isa.dto.reservation.NewReservationDTO;
import com.ftn.isa.model.Reservation;
import com.ftn.isa.model.TimeSlot;
import com.ftn.isa.model.User;
import com.ftn.isa.repository.ReservationRepository;
import com.ftn.isa.repository.TimeSlotRepository;
import com.ftn.isa.repository.UserRepository;
import com.ftn.isa.service.EmailService;
import com.ftn.isa.service.QRCodeService;
import com.ftn.isa.service.ReservationService;
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
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Override
    public void createReservation(NewReservationDTO newReservationDTO) {
        logger.info("Creating new reservation");
    }

    @Override
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation not found"));
        TimeSlot timeslot = reservation.getTimeSlot();
        timeslot.setAvailable(true);
        timeslot.setEquipment(null);
        timeSlotRepository.save(timeslot);
        boolean isWithin24 = isWithin24Hours(reservation.getTimeSlot().getStartTime(), LocalDateTime.now());
        User user = reservation.getUser();
        if (isWithin24) {
            user.setPenaltyPoints(user.getPenaltyPoints() + 2);
        } else {
            user.setPenaltyPoints(user.getPenaltyPoints() + 1);
        }
        userRepository.save(user);
        reservationRepository.deleteById(reservationId);
    }

    public boolean isWithin24Hours(LocalDateTime reservationStartTime, LocalDateTime currentTime) {
        Duration duration = Duration.between(currentTime, reservationStartTime);
        return duration.toHours() < 24 && !duration.isNegative();
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
