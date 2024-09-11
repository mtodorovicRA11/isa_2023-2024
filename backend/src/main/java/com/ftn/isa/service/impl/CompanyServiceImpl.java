package com.ftn.isa.service.impl;

import com.ftn.isa.dto.company.CompanyResponseDTO;
import com.ftn.isa.mapper.CompanyMapper;
import com.ftn.isa.model.Company;
import com.ftn.isa.repository.CompanyRepository;
import com.ftn.isa.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyMapper companyMapper;

    @Override
    public List<CompanyResponseDTO> getAll() {
        return companyMapper.toResponseDTO(companyRepository.findAll());
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found!"));
    }
}
