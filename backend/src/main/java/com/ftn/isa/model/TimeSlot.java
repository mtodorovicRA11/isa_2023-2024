package com.ftn.isa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "timeslot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;
}