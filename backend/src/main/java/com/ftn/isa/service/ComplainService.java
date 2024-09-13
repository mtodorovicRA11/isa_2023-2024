package com.ftn.isa.service;

import com.ftn.isa.dto.complain.ComplainReturnDTO;
import com.ftn.isa.dto.complain.NewComplainDTO;
import com.ftn.isa.dto.complain.NewComplainResponseDTO;
import com.ftn.isa.model.User;

import java.util.List;

public interface ComplainService {
    void submit(NewComplainDTO newComplainDTO, User user);

    List<ComplainReturnDTO> getAll(User user);

    void respond(NewComplainResponseDTO newComplainResponseDTO);
}
