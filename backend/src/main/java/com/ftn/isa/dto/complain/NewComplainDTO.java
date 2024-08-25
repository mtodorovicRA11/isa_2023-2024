package com.ftn.isa.dto.complain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewComplainDTO {
    private Long  userId;
    private Long companyId;
    private Long administratorId;
    private String description;
}
