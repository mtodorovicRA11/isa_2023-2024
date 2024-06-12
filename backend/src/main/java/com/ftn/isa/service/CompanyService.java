package com.ftn.isa.service;

import com.ftn.isa.dto.company.CompanyResponseDTO;

import java.util.List;

public interface CompanyService {
    List<CompanyResponseDTO> getAll();
}
