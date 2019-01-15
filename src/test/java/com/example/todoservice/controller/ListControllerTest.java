package com.example.todoservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.todoservice.controller.exception.GlobalExceptionHandler;
import com.example.todoservice.controller.request.ListRequest;
import com.example.todoservice.model.List;
import com.example.todoservice.service.ListService;
import com.fasterxml.jackson.core.type.TypeReference;

@RunWith(SpringRunner.class)
public class ListControllerTest extends TestMockData {

	@InjectMocks
	private ListController listController;

	@MockBean
	private ListService listService;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(listController).setControllerAdvice(new GlobalExceptionHandler())
				.build();

		// Init common data for mocks
		initData();
	}

	@Test
	public void testCreateList_ResultOk() throws Exception {
		when(listService.createList(any(ListRequest.class), any())).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/list").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(listRequest));
		mockMvc.perform(requestBuilder).andExpect(status().isCreated());
	}

	@Test
	public void testCreateList_BadRequest() throws Exception {
		when(listService.createList(any(ListRequest.class), any())).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/list").contentType(MediaType.APPLICATION_JSON)
				.content("not a valid body");
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
	}

	@Test
	public void testFindListByListId_ResultOk() throws Exception {
		when(listService.findOneByListIdAndUser(any(Integer.class), any())).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/list/{listId}", 1)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.listId").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"));
	}

	@Test
	public void testFindAllListsByUser_ResultOk() throws Exception {
		when(listService.findAllListsByUser(any())).thenReturn(Arrays.asList(list));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/list").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		java.util.List<List> lists = mapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<java.util.List<List>>() {
				});

		assertThat(lists).isNotEmpty();
		assertThat(lists.size()).isEqualTo(1);
	}

	@Test
	public void testRenameList_ResultOk() throws Exception {
		when(listService.renameList(any(Integer.class), any(ListRequest.class), any())).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/list/{listId}", 1)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(listRequest));
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	public void testDeleteList_ResultOk() throws Exception {
		doNothing().when(listService).deleteList(any(Integer.class), any());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/list/{listId}", 1)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isNoContent());
	}

}
