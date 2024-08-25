package com.ftn.isa.mapper;

import com.ftn.isa.dto.complain.ComplainReturnDTO;
import com.ftn.isa.model.Complain;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComplainsMapper {
    List<ComplainReturnDTO> toDTO(List<Complain> complains);
}
