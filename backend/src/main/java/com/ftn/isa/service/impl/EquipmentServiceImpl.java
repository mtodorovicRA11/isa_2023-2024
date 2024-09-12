package com.ftn.isa.service.impl;

import com.ftn.isa.dto.email.EmailDetailsDTO;
import com.ftn.isa.dto.reservation.NewReservationDTO;
import com.ftn.isa.model.*;
import com.ftn.isa.repository.CompanyRepository;
import com.ftn.isa.repository.EquipmentRepository;
import com.ftn.isa.repository.ReservationRepository;
import com.ftn.isa.repository.TimeSlotRepository;
import com.ftn.isa.service.EmailService;
import com.ftn.isa.service.EquipmentService;
import com.ftn.isa.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private TimeSlotRepository timeslotRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public List<TimeSlot> getAvailableTimeslots(Long equipmentId) {
        return timeslotRepository.findByEquipmentIdAndIsAvailable(equipmentId, true);
    }

    public boolean reserveEquipment(NewReservationDTO newReservationDTO, User user, Long companyId) {
        Long timeslotId = newReservationDTO.getTimeSlotId();
        TimeSlot timeslot = timeslotRepository.findById(timeslotId).orElseThrow();
        Company company = companyRepository.findById(companyId).orElseThrow();

        List<Reservation> reservations = reservationRepository.findAll();
        reservations.forEach(reservation -> {
            if (Objects.equals(company.getId(), reservation.getCompany().getId()) && Objects.equals(user.getId(), reservation.getUser().getId())) {
                throw new RuntimeException("Reservation already exists");
            }
        });

        if (!timeslot.isAvailable()) {
            return false;
        }

        timeslot.setAvailable(false);
        timeslotRepository.save(timeslot);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setTimeSlot(timeslot);
        reservation.setCompany(company);

        Map<Equipment, Integer> equipmentQuantities = new HashMap<>();
        newReservationDTO.getEquipment().forEach(simpleEquipmentDTO -> {
            Equipment equipment = equipmentRepository.findById(simpleEquipmentDTO.getId()).orElseThrow();
            int reservedAmount = simpleEquipmentDTO.getAmount();

            if (equipment.getAmount() < reservedAmount) {
                throw new RuntimeException("Insufficient quantity for equipment: " + equipment.getName());
            }

            equipment.setAmount(equipment.getAmount() - reservedAmount);
            equipmentRepository.save(equipment);

            equipmentQuantities.put(equipment, reservedAmount);
        });

        reservation.setEquipmentQuantities(equipmentQuantities);
        reservationRepository.save(reservation);

        String qrCodeImage = qrCodeService.generateQRCode(user, reservation);
        EmailDetailsDTO details = new EmailDetailsDTO();
        details.setRecipient(user.getEmail());
        details.setSubject("Reservation Confirmation");
        details.setMessageBody("<p>Your reservation has been confirmed. See the attached QR code.</p><img src='cid:qrImage' />");

        emailService.sendEmailWithAttachment(details, qrCodeImage);

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

    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }
}
