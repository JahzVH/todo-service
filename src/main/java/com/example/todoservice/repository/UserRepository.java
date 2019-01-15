package com.example.todoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todoservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findOneByUsername(String username);

}
