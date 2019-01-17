package com.example.todoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.todoservice.controller.request.ListRequest;
import com.example.todoservice.exception.RecordNotFoundException;
import com.example.todoservice.model.List;
import com.example.todoservice.model.User;
import com.example.todoservice.repository.ListRepository;
import com.example.todoservice.repository.UserRepository;

@Service
public class ListService {

	@Autowired
	private ListRepository listRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * 
	 * @param listRequest
	 * @param authentication
	 * @return
	 */
	public List createList(ListRequest listRequest, Authentication authentication) {
		User user = this.getAuthorizedUser(authentication);

		return listRepository.save(new List(listRequest.getName(), user));
	}

	/**
	 * 
	 * @param listId
	 * @param authentication
	 * @return
	 */
	public List findOneByListIdAndUser(Integer listId, Authentication authentication) {
		User user = this.getAuthorizedUser(authentication);

		return listRepository.findOneByListIdAndUser(listId, user)
				.orElseThrow(() -> new RecordNotFoundException("List not found using listId: " + listId));
	}

	/**
	 * 
	 * @param authentication
	 * @return
	 */
	public java.util.List<List> findAllListsByUser(Authentication authentication) {
		User user = this.getAuthorizedUser(authentication);

		return listRepository.findAllByUser(user);
	}

	/**
	 * 
	 * @param listId
	 * @param listRequest
	 * @param authentication
	 * @return
	 */
	public List renameList(Integer listId, ListRequest listRequest, Authentication authentication) {
		User user = this.getAuthorizedUser(authentication);
		List listToUpdate = listRepository.findOneByListIdAndUser(listId, user)
				.orElseThrow(() -> new RecordNotFoundException("List not found using listId: " + listId));
		listToUpdate.setName(listRequest.getName());

		return listRepository.save(listToUpdate);
	}

	/**
	 * 
	 * @param listId
	 * @param authentication
	 */
	public void deleteList(Integer listId, Authentication authentication) {
		User user = this.getAuthorizedUser(authentication);
		listRepository.deleteByListIdAndUser(listId, user);
	}

	private User getAuthorizedUser(Authentication authentication) {
		return userRepository.findOneByUsername(authentication.getName())
				.orElseThrow(() -> new RecordNotFoundException("User not found"));
	}

}
