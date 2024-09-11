package com.ftn.isa.service;

import com.ftn.isa.model.Role;

import java.util.List;

public interface RoleService {
    Role findById(Long id);

    List<Role> findByName(String name);
}
