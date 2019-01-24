package com.example.todoservice.service;

import com.example.todoservice.exception.UsertNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todoservice.controller.request.LoginRequest;
import com.example.todoservice.exception.RecordNotFoundException;
import com.example.todoservice.model.User;
import com.example.todoservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @param loginRequest
	 * @return
	 */
	public Boolean authenticateUser(LoginRequest loginRequest) {
		User user = userRepository.findOneByUsername(loginRequest.getUsername())
				.orElseThrow(() -> new UsertNotFoundException("User not found"));

		return new BCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword());

	}

}
