package com.ftn.isa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "timeslot_id")
    private TimeSlot timeSlot;
    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reservation_equipment", joinColumns = @JoinColumn(name = "reservation_id"))
    @MapKeyJoinColumn(name = "equipment_id")
    @Column(name = "quantity")
    private Map<Equipment, Integer> equipmentQuantities = new HashMap<>();
}
