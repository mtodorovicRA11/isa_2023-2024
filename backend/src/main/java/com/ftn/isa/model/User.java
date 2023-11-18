package com.ftn.isa.model;

import com.ftn.isa.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Role role;
    @Email
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;
    @Column(name = "position", nullable = false)
    private String position;
    @Column(name = "companyDescription", nullable = false)
    private String companyDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(city, user.city) && Objects.equals(country, user.country) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(position, user.position) && Objects.equals(companyDescription, user.companyDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, firstName, lastName, city, country, phoneNumber, position, companyDescription);
    }
}
