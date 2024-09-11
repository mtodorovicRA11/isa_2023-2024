package com.ftn.isa.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserRequestDTO {

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

    private String phoneNumber;

    private String jmbg;

    private String verificationCode;

    private String jobTitle;
}
