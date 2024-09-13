package com.ftn.isa.dto.complain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NewComplainDTO {
    private Long companyId;
    private Long administratorId;
    private String description;
}
