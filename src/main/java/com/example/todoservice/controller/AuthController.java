package com.example.todoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoservice.controller.request.LoginRequest;
import com.example.todoservice.service.UserService;

@RestController()
@RequestMapping("/login")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<Boolean> authenticateUser(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(userService.authenticateUser(loginRequest));
	}

}
