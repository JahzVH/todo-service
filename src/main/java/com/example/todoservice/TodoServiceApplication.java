package com.example.todoservice;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.todoservice.model.Role;
import com.example.todoservice.model.User;
import com.example.todoservice.repository.ListRepository;
import com.example.todoservice.repository.RoleRepository;
import com.example.todoservice.repository.UserRepository;

@SpringBootApplication
public class TodoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ListRepository listRepository, UserRepository userRepository,
			RoleRepository roleRepository) {
		return args -> {
			// Create users for testing purposes
			Arrays.asList(new User("tony", encodePassword("testpw"), true),
					new User("jahz", encodePassword("testpw"), true), new User("axel", encodePassword("testpw"), true))
					.forEach(user -> userRepository.save(user));

			// Assign roles to users
			Arrays.asList(new Role("user", "tony"), new Role("user", "jahz"), new Role("user", "axel"))
					.forEach(auth -> roleRepository.save(auth));
		};
	}

	private String encodePassword(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

}
