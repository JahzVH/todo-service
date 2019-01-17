package com.example.todoservice;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.todoservice.model.Role;
import com.example.todoservice.model.User;
import com.example.todoservice.repository.ListRepository;
import com.example.todoservice.repository.RoleRepository;
import com.example.todoservice.repository.UserRepository;
import com.example.todoservice.util.Helper;

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
			Arrays.asList(new User("jahz", Helper.encodePassword("testpw"), true),
					new User("user1", Helper.encodePassword("testpw"), true),
					new User("user2", Helper.encodePassword("testpw"), true))
					.forEach(user -> userRepository.save(user));

			// Assign roles to users
			Arrays.asList(new Role("user", "jahz"), new Role("user", "user1"), new Role("user", "user2"))
					.forEach(auth -> roleRepository.save(auth));

			System.out.println("Users enabled for API usage");
			roleRepository.findAll().stream().map(r -> r.getUsername()).forEach(System.out::println);
			;
		};
	}

}
