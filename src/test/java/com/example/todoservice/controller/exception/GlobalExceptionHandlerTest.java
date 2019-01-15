package com.example.todoservice.controller.exception;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.todoservice.controller.ListController;
import com.example.todoservice.exception.RecordNotFoundException;
import com.example.todoservice.service.ListService;

@RunWith(SpringRunner.class)
public class GlobalExceptionHandlerTest {

	@InjectMocks
	private ListController listController;

	@MockBean
	private ListService listService;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(listController).setControllerAdvice(new GlobalExceptionHandler())
				.build();
	}

	@Test
	public void testHandleRecordNotFoundException() throws Exception {
		doThrow(RecordNotFoundException.class).when(listService).findAllListsByUser(any());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/list").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound());

	}

	@Test
	public void testHandleRuntimeException() throws Exception {
		doThrow(NullPointerException.class).when(listService).findAllListsByUser(any());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/list").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());

	}

}
