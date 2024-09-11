package com.ftn.isa.dto.complain;

import com.ftn.isa.dto.company.CompanyResponseDTO;
import com.ftn.isa.dto.user.UserResponseDTO;
import com.ftn.isa.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComplainReturnDTO {

    private long id;
    private User user;
    private CompanyResponseDTO company;
    private String description;
    private String response;
    private UserResponseDTO administrator;
}
