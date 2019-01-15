package com.example.todoservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.todoservice.controller.exception.GlobalExceptionHandler;
import com.example.todoservice.controller.request.ItemRequest;
import com.example.todoservice.service.ItemService;

@RunWith(SpringRunner.class)
public class ItemControllerTest extends TestMockData {

	@InjectMocks
	private ItemController itemController;

	@MockBean
	private ItemService itemService;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(itemController).setControllerAdvice(new GlobalExceptionHandler())
				.build();

		// Init common data for mocks
		initData();
	}

	@Test
	public void testCreateItem_ResultOk() throws Exception {
		when(itemService.createItem(any(Integer.class), any(ItemRequest.class), any())).thenReturn(item);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/list/{listId}/item", 1)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(itemRequest));
		mockMvc.perform(requestBuilder).andExpect(status().isCreated());
	}

	@Test
	public void testCreateItem_BadRequest() throws Exception {
		when(itemService.createItem(any(Integer.class), any(ItemRequest.class), any())).thenReturn(item);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/list/{listId}/item", 1)
				.contentType(MediaType.APPLICATION_JSON).content("not a valid body");
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdateItem_ResultOk() throws Exception {
		when(itemService.updateItem(any(Integer.class), any(Integer.class), any(ItemRequest.class), any()))
				.thenReturn(item);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/list/{listId}/item/{itemId}", 1, 1)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(itemRequest));
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

}
