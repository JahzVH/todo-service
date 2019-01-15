package com.example.todoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todoservice.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
