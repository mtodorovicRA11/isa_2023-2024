package com.ftn.isa.dto.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDetailsDTO {
    private String recipient;
    private String subject;
    private String messageBody;
    private String attachment;
}
