package com.ftn.isa.controller;

import com.ftn.isa.dto.auth.JwtAuthenticationRequest;
import com.ftn.isa.dto.auth.UserTokenState;
import com.ftn.isa.dto.email.EmailDetailsDTO;
import com.ftn.isa.dto.user.UserRequestDTO;
import com.ftn.isa.exception.ResourceConflictException;
import com.ftn.isa.model.User;
import com.ftn.isa.service.UserService;
import com.ftn.isa.service.impl.EmailService;
import com.ftn.isa.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.SecureRandom;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    TokenUtils tokenUtils;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody UserRequestDTO userRequestDTO) {
        User existingUser = userService.findByUsername(userRequestDTO.getUsername());

        if (existingUser != null) {
            throw new ResourceConflictException(existingUser.getId(), "Username already exists");
        }
        String verification = new BigInteger(30, new SecureRandom()).toString(32).toUpperCase();

        userRequestDTO.setVerificationCode(verification);
        User user = userService.save(userRequestDTO);

        EmailDetailsDTO email = new EmailDetailsDTO();
        email.setRecipient(user.getEmail());
        email.setSubject("Account verification!");
        email.setMessageBody("In order to verify your account, you need to go to the following link : " + "http://localhost:8080/auth/activate?code" +
                "=" + verification);
        emailService.sendEmail(email);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/activate")
    public ResponseEntity<User> activateUser(@RequestParam String code) {
        User user = userService.getByVerificationCode(code);
        return new ResponseEntity<>(userService.activate(user), HttpStatus.CREATED);
    }
}