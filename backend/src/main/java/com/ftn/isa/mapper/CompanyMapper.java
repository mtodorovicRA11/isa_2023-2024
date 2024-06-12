package com.ftn.isa.mapper;

import com.ftn.isa.dto.company.CompanyResponseDTO;
import com.ftn.isa.model.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyResponseDTO toResponseDTO(Company company);

    List<CompanyResponseDTO> toResponseDTO(List<Company> companies);
}
