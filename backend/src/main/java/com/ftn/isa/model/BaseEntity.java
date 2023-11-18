package com.ftn.isa.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@MappedSuperclass
@Getter
public class BaseEntity implements Persistable<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Transient
    public boolean isNew() {
        return false;
    }
}
