package com.example.todoservice.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.todoservice.controller.request.ListRequest;
import com.example.todoservice.model.List;
import com.example.todoservice.service.ListService;

@RestController
@RequestMapping("/list")
public class ListController {
	
	private Logger logger = LoggerFactory.getLogger("TodoServiceApplication");

	@Autowired
	private ListService listService;

	@PostMapping
	public ResponseEntity<List> createList(@RequestBody ListRequest listRequest, Authentication authentication) {
		logger.info("Invoking endpoint POST "+ ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
		List savedList = listService.createList(listRequest, authentication);
		URI listLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedList.getListId()).toUri();

		return ResponseEntity.created(listLocation).build();
	}

	@GetMapping("/{listId}")
	public ResponseEntity<List> findListByListId(@PathVariable Integer listId, Authentication authentication) {
		logger.info("Invoking endpoint GET "+ ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
		return ResponseEntity.ok(listService.findOneByListIdAndUser(listId, authentication));
	}

	@GetMapping
	public ResponseEntity<java.util.List<List>> findAllListsByUser(Authentication authentication) {
		logger.info("Invoking endpoint GET "+ ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
		return ResponseEntity.ok(listService.findAllListsByUser(authentication));
	}

	@PutMapping("/{listId}")
	public ResponseEntity<List> renameList(@PathVariable Integer listId, @RequestBody ListRequest listRequest,
			Authentication authentication) {
		logger.info("Invoking endpoint PUT "+ ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
		return ResponseEntity.ok(listService.renameList(listId, listRequest, authentication));
	}

	@DeleteMapping("/{listId}")
	public ResponseEntity<List> deleteList(@PathVariable Integer listId, Authentication authentication) {
		logger.info("Invoking endpoint DELETE "+ ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
		listService.deleteList(listId, authentication);
		
		return ResponseEntity.noContent().build();
	}

}
