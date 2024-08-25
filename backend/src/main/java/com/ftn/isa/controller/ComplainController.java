package com.ftn.isa.controller;

import com.ftn.isa.dto.complain.ComplainReturnDTO;
import com.ftn.isa.dto.complain.NewComplainDTO;
import com.ftn.isa.dto.complain.NewComplainResponseDTO;
import com.ftn.isa.mapper.ComplainsMapper;
import com.ftn.isa.model.Company;
import com.ftn.isa.model.Complain;
import com.ftn.isa.model.User;
import com.ftn.isa.service.CompanyService;
import com.ftn.isa.service.ComplainService;
import com.ftn.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/complain", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComplainController {

    @Autowired
    ComplainService complainService;

    @GetMapping("/all")
    public ResponseEntity<List<ComplainReturnDTO>> getAll(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(complainService.getAll(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitComplain(NewComplainDTO newComplainDTO) {
        try {
            complainService.submit(newComplainDTO);
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
