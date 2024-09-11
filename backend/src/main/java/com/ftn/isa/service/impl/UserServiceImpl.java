package com.ftn.isa.service.impl;

import java.util.List;
import java.util.Objects;

import com.ftn.isa.dto.user.UserRequestDTO;
import com.ftn.isa.model.Role;
import com.ftn.isa.model.User;
import com.ftn.isa.repository.UserRepository;
import com.ftn.isa.service.RoleService;
import com.ftn.isa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	public User findById(Long id) throws AccessDeniedException {
		return userRepository.findById(id).orElseGet(null);
	}

	public List<User> findAll() throws AccessDeniedException {
		return userRepository.findAll();
	}

	@Override
	public User save(UserRequestDTO userRequestDTO) {
		User user = new User();
		user.setFirstName(userRequestDTO.getFirstname());
		user.setLastName(userRequestDTO.getLastname());
		user.setEmail(userRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
		user.setUsername(userRequestDTO.getUsername());
		user.setEnabled(true);
		user.setVerificationCode(userRequestDTO.getVerificationCode());
		user.setJmbg(userRequestDTO.getJmbg());
		user.setJobTitle(userRequestDTO.getJobTitle());

		List<Role> roles = roleService.findByName("ROLE_USER");
		user.setRoles(roles);
		
		return this.userRepository.save(user);
	}

	@Override
	public User getByVerificationCode(String code){
		return userRepository.findAll().stream()
				.filter(user -> Objects.nonNull(user.getVerificationCode()) && user.getVerificationCode().equals(code))
				.findAny()
				.orElse(null);
	}

	@Override
	public User activate(User user) {
		User old = userRepository.findById(user.getId()).orElseGet(null);
		old.setEnabled(true);
		return userRepository.save(old);
	}
}
