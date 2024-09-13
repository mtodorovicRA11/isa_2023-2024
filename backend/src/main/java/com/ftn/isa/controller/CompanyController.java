package com.ftn.isa.controller;

import com.ftn.isa.dto.company.CompanyResponseDTO;
import com.ftn.isa.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping("/all")
    public List<CompanyResponseDTO> getAll() {
        return companyService.getAll();
    }
}
