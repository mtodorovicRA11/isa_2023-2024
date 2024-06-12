package com.ftn.isa.service;

import com.ftn.isa.dto.user.UserRequestDTO;
import com.ftn.isa.model.User;

import java.util.List;

public interface UserService {
    User findById(Long id);

    User findByUsername(String username);

    List<User> findAll();

    User save(UserRequestDTO userRequestDTO);
}
