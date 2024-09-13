package com.ftn.isa.controller;

import com.ftn.isa.dto.complain.ComplainReturnDTO;
import com.ftn.isa.dto.complain.NewComplainDTO;
import com.ftn.isa.dto.complain.NewComplainResponseDTO;
import com.ftn.isa.model.User;
import com.ftn.isa.service.ComplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/complain", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class ComplainController {

    @Autowired
    ComplainService complainService;

    @GetMapping("/all")
    public ResponseEntity<List<ComplainReturnDTO>> getAll() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = (User) user;
        try {
            return ResponseEntity.ok(complainService.getAll(u));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitComplain(@RequestBody NewComplainDTO newComplainDTO) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = (User) user;
        try {
            complainService.submit(newComplainDTO, u);
            return ResponseEntity.ok("Complain submitted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/respond")
    public ResponseEntity<String> respondToComplain(NewComplainResponseDTO newComplainResponseDTO) {
        try {
            complainService.respond(newComplainResponseDTO);
            return ResponseEntity.ok("Response submitted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
