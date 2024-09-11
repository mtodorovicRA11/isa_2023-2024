package com.ftn.isa.mapper;

import com.ftn.isa.dto.complain.ComplainReturnDTO;
import com.ftn.isa.dto.user.UserResponseDTO;
import com.ftn.isa.model.Complain;
import com.ftn.isa.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComplainsMapper {
    UserResponseDTO toDTO(User user);

    List<ComplainReturnDTO> toDTO(List<Complain> complains);
}
