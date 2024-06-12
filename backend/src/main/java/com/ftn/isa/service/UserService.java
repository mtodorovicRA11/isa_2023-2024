package com.ftn.isa.service;

import com.ftn.isa.dto.auth.UserRequest;
import com.ftn.isa.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserService {
    User findById(Long id);

    User findByUsername(String username);

    List<User> findAll();

    User save(UserRequest userRequest);
}
