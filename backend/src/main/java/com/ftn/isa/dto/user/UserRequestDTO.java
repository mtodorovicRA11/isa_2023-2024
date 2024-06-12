package com.ftn.isa.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO koji preuzima podatke iz HTML forme za registraciju
@Setter
@Getter
@NoArgsConstructor
public class UserRequestDTO {

    private Long id;

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

}
