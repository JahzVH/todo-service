package com.example.todoservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.todoservice.controller.request.ItemRequest;
import com.example.todoservice.exception.RecordNotFoundException;
import com.example.todoservice.model.Item;
import com.example.todoservice.model.List;
import com.example.todoservice.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ListService listService;

	/**
	 * 
	 * @param listId
	 * @param itemRequest
	 * @param authentication
	 * @return
	 */
	public Item createItem(Integer listId, ItemRequest itemRequest, Authentication authentication) {
		List list = listService.findOneByListIdAndUser(listId, authentication);
		Item item = new Item();
		this.generateItem(itemRequest, item, list);

		return itemRepository.save(item);
	}

	/**
	 * 
	 * @param listId
	 * @param itemId
	 * @param itemRequest
	 * @param authentication
	 * @return
	 */
	public Item updateItem(Integer listId, Integer itemId, ItemRequest itemRequest, Authentication authentication) {
		List list = listService.findOneByListIdAndUser(listId, authentication);
		Item itemToUpdate = itemRepository.findOneByItemIdAndListAndUser(itemId, list, list.getUser())
				.orElseThrow(() -> new RecordNotFoundException("Item not found using itemId: " + itemId));
		this.generateItem(itemRequest, itemToUpdate, list);

		return itemRepository.save(itemToUpdate);

	}
	
	/**
	 * 
	 * @param listId
	 * @param itemId
	 * @param authentication
	 */
	public void deleteItem(Integer listId, Integer itemId, Authentication authentication) {
		List list = listService.findOneByListIdAndUser(listId, authentication);
		itemRepository.deleteByItemIdAndListAndUser(itemId, list, list.getUser());
	}

	private void generateItem(ItemRequest itemRequest, Item item, List list) {
		BeanUtils.copyProperties(itemRequest, item, "user");
		item.setList(list);
		item.setUser(list.getUser());
	}

}
