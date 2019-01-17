package com.example.todoservice.controller;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.example.todoservice.controller.request.ItemRequest;
import com.example.todoservice.controller.request.ListRequest;
import com.example.todoservice.controller.request.LoginRequest;
import com.example.todoservice.model.Item;
import com.example.todoservice.model.List;
import com.example.todoservice.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestMockData {
	
	protected ObjectMapper mapper;
	
	protected MockMvc mockMvc;
	
	protected List list;
	
	protected Item item;
	
	protected User user;
	
	protected ItemRequest itemRequest;
	
	protected ListRequest listRequest;
	
	protected LoginRequest loginRequest;
	
	protected Authentication authentication;
	
	protected void initData() {
		
		mapper = new ObjectMapper();

		user = new User();
		user.setEnabled(true);
		user.setPassword(new BCryptPasswordEncoder().encode("password"));
		user.setUserId(1);
		user.setUsername("username");

		list = new List();
		list.setItems(Arrays.asList(item));
		list.setListId(1);
		list.setName("name");
		list.setUser(user);

		item = new Item();
		item.setCompleted(false);
		item.setDueDate(LocalDateTime.now());
		item.setItem("item");
		item.setItemId(1);
		item.setList(list);

		listRequest = new ListRequest();
		listRequest.setName("name");
		
		itemRequest = new ItemRequest();
		itemRequest.setCompleted(false);
		itemRequest.setDueDate(null);
		itemRequest.setItem("item");
		
		loginRequest = new LoginRequest();
		loginRequest.setPassword("password");
		loginRequest.setUsername("username");
		
	}
	

}
