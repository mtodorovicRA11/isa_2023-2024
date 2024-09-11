package com.ftn.isa.service.impl;

import com.ftn.isa.dto.complain.ComplainReturnDTO;
import com.ftn.isa.dto.complain.NewComplainDTO;
import com.ftn.isa.dto.complain.NewComplainResponseDTO;
import com.ftn.isa.mapper.ComplainsMapper;
import com.ftn.isa.model.Company;
import com.ftn.isa.model.Complain;
import com.ftn.isa.model.User;
import com.ftn.isa.repository.CompanyReservationRepository;
import com.ftn.isa.repository.ComplainRepository;
import com.ftn.isa.service.CompanyService;
import com.ftn.isa.service.ComplainService;
import com.ftn.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplainServiceImpl implements ComplainService {

    @Autowired
    UserService userService;
    @Autowired
    ComplainsMapper complainsMapper;
    @Autowired
    CompanyService companyService;
    @Autowired
    ComplainRepository complainRepository;
    @Autowired
    CompanyReservationRepository companyReservationRepository;

    @Override
    public void submit(NewComplainDTO newComplainDTO) {
        User user = userService.findById(newComplainDTO.getUserId());
        Company company = newComplainDTO.getCompanyId() != null ? companyService.findById(newComplainDTO.getCompanyId()) : null;
        User administrator = newComplainDTO.getAdministratorId() != null ? userService.findById(newComplainDTO.getAdministratorId()) : null;

        if (company != null && !companyReservationRepository.existsByUserAndCompany(user, company)) {
            throw new RuntimeException("You can only complain about a company you made reservation.");
        }

//        if (administrator != null && !companyReservationRepository.existsByUserAndReservation_Administrator(user, administrator)) {
//            throw new RuntimeException("You can only complain about an administrator you had contact with.");
//        }

        Complain complain = new Complain();
        complain.setUser(user);
        complain.setCompany(company);
        complain.setAdministrator(administrator);
        complain.setDescription(newComplainDTO.getDescription());
        complainRepository.save(complain);
    }

    @Override
    public List<ComplainReturnDTO> getAll(Long userId) {
        User user = userService.findById(userId);
        List<Complain> complains = complainRepository.findByUser(user);
        return complainsMapper.toDTO(complains);
    }

    @Override
    public void respond(NewComplainResponseDTO newComplainResponseDTO) {
        Complain complain = complainRepository.findById(newComplainResponseDTO.getComplainId())
                .orElseThrow(() -> new RuntimeException("Complain not found!"));
        complain.setResponse(newComplainResponseDTO.getResponse());
        complainRepository.save(complain);
    }
}
