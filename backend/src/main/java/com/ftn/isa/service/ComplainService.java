package com.ftn.isa.service;

import com.ftn.isa.dto.complain.ComplainReturnDTO;
import com.ftn.isa.dto.complain.NewComplainDTO;
import com.ftn.isa.dto.complain.NewComplainResponseDTO;

import java.util.List;

public interface ComplainService {
    void submit(NewComplainDTO newComplainDTO);

    List<ComplainReturnDTO> getAll(Long userId);

    void respond(NewComplainResponseDTO newComplainResponseDTO);
}
