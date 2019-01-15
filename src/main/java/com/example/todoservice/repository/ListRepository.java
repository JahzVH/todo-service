package com.example.todoservice.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todoservice.model.List;
import com.example.todoservice.model.User;

@Repository
public interface ListRepository extends JpaRepository<List, Integer> {

	Optional<List> findOneByListIdAndUser(Integer listId, User user);

	java.util.List<List> findAllByUser(User user);

	@Transactional
	void deleteByListIdAndUser(Integer listId, User user);

}
