package com.example.todoservice.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todoservice.model.Item;
import com.example.todoservice.model.List;
import com.example.todoservice.model.User;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	Optional<Item> findOneByItemIdAndListAndUser(Integer itemId, List list, User user);

	@Transactional
	void deleteByItemIdAndListAndUser(Integer itemId, List list, User user);

}
