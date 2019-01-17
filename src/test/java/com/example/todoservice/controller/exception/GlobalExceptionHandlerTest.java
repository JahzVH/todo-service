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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.todoservice.controller.AuthController;
import com.example.todoservice.controller.ListController;
import com.example.todoservice.controller.TestMockData;
import com.example.todoservice.exception.RecordNotFoundException;
import com.example.todoservice.service.ListService;
import com.example.todoservice.service.UserService;

@RunWith(SpringRunner.class)
public class GlobalExceptionHandlerTest extends TestMockData {

	@InjectMocks
	private ListController listController;

	@InjectMocks
	private AuthController authController;

	@MockBean
	private ListService listService;

	@MockBean
	private UserService userService;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(listController, authController)
				.setControllerAdvice(new GlobalExceptionHandler()).build();

		// Init common data for mocks
		initData();
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
