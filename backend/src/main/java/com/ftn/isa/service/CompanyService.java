package com.ftn.isa.service;

import com.ftn.isa.dto.company.CompanyResponseDTO;
import com.ftn.isa.model.Company;

import java.util.List;

public interface CompanyService {
    List<CompanyResponseDTO> getAll();
    Company findById(Long id);

}
