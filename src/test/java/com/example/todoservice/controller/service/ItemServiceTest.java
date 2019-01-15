package com.example.todoservice.controller.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.todoservice.controller.TestMockData;
import com.example.todoservice.exception.RecordNotFoundException;
import com.example.todoservice.model.Item;
import com.example.todoservice.model.List;
import com.example.todoservice.model.User;
import com.example.todoservice.repository.ItemRepository;
import com.example.todoservice.service.ItemService;
import com.example.todoservice.service.ListService;

@RunWith(SpringRunner.class)
public class ItemServiceTest extends TestMockData {

	@InjectMocks
	private ItemService itemService;

	@MockBean
	private ItemRepository itemRepository;

	@MockBean
	private ListService listService;

	private Optional<Item> itemData;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		// Init common data for mocks
		initData();

		itemData = Optional.of(item);
		authentication = mock(Authentication.class);
		when(listService.findOneByListIdAndUser(any(Integer.class), any())).thenReturn(list);
		when(itemRepository.save(any(Item.class))).thenReturn(item);
	}

	@Test
	public void testCreateItem_ResultOk() throws Exception {
		Item item = itemService.createItem(1, itemRequest, authentication);
		assertThat(item).isNotNull();
		assertThat(item.getItem()).isEqualTo("item");
	}

	@Test
	public void testUpdateItem_ResultOk() throws Exception {
		when(itemRepository.findOneByItemIdAndListAndUser(any(Integer.class), any(List.class), any(User.class)))
				.thenReturn(itemData);

		itemRequest.setItem("updated item");
		Item item = itemService.updateItem(1, 1, itemRequest, authentication);
		assertThat(item).isNotNull();
		assertThat(item.getItem()).isEqualTo("updated item");
	}

	@Test(expected = RecordNotFoundException.class)
	public void testUpdateItem_NoItemFound() throws Exception {
		when(itemRepository.findOneByItemIdAndListAndUser(any(Integer.class), any(List.class), any(User.class)))
				.thenReturn(Optional.empty());

		itemService.updateItem(1, 1, itemRequest, authentication);
	}

	@Test
	public void testDeleteItem() throws Exception {
		doNothing().when(itemRepository).deleteByItemIdAndListAndUser(any(Integer.class), any(List.class),
				any(User.class));

		itemService.deleteItem(1, 1, authentication);
		verify(itemRepository, times(1)).deleteByItemIdAndListAndUser(1, list, user);
	}

}
