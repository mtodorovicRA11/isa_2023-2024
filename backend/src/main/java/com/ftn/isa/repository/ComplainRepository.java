package com.ftn.isa.repository;

import com.ftn.isa.model.Complain;
import com.ftn.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplainRepository extends JpaRepository<Complain, Long> {
    List<Complain> findByUser(User user);
}
