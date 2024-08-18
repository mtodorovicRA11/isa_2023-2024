package com.ftn.isa.controller;

import com.ftn.isa.dto.auth.JwtAuthenticationRequest;
import com.ftn.isa.dto.auth.UserTokenState;
import com.ftn.isa.dto.user.UserRequestDTO;
import com.ftn.isa.exception.ResourceConflictException;
import com.ftn.isa.model.User;
import com.ftn.isa.service.UserService;
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

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

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
        User existUser = this.userService.findByUsername(userRequestDTO.getUsername());

        if (existUser != null) {
            throw new ResourceConflictException(userRequestDTO.getId(), "Username already exists");
        }

        User user = this.userService.save(userRequestDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}