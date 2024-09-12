package com.ftn.isa.dto.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewReservationDTO {
    private List<SimpleEquipmentDTO> equipment;
    private Long timeSlotId;
}
