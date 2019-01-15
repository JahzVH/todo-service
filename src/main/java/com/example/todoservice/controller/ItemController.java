package com.example.todoservice.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.todoservice.controller.request.ItemRequest;
import com.example.todoservice.model.Item;
import com.example.todoservice.service.ItemService;

@RestController
@RequestMapping("/list")
public class ItemController {
	
	private Logger logger = LoggerFactory.getLogger("TodoServiceApplication");

	@Autowired
	private ItemService itemService;

	@PostMapping("/{listId}/item")
	public ResponseEntity<Item> createItem(@PathVariable Integer listId, @RequestBody ItemRequest itemRequest,
			Authentication authentication) {
		logger.info("Invoking endpoint POST "+ ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
		Item savedItem = itemService.createItem(listId, itemRequest, authentication);
		URI itemLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedItem.getItemId()).toUri();

		return ResponseEntity.created(itemLocation).build();
	}

	@PutMapping("/{listId}/item/{itemId}")
	public ResponseEntity<Item> updateItem(@PathVariable Integer listId, @PathVariable Integer itemId,
			@RequestBody ItemRequest itemRequest, Authentication authentication) {
		logger.info("Invoking endpoint PUT "+ ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
		return ResponseEntity.ok(itemService.updateItem(listId, itemId, itemRequest, authentication));
	}

	@DeleteMapping("/{listId}/item/{itemId}")
	public ResponseEntity<Item> deleteItem(@PathVariable Integer listId, @PathVariable Integer itemId,
			Authentication authentication) {
		logger.info("Invoking endpoint DELETE "+ ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
		itemService.deleteItem(listId, itemId, authentication);

		return ResponseEntity.noContent().build();
	}

}
