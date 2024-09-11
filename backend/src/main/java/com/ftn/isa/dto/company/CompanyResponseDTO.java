package com.ftn.isa.dto.company;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyResponseDTO {

    private Long id;
    private String email;
    private String name;
    private String city;
    private String country;
    private String phoneNumber;
    private String companyDescription;

}
