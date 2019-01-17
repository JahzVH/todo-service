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
import com.example.todoservice.controller.request.LoginRequest;
import com.example.todoservice.service.UserService;

@RunWith(SpringRunner.class)
public class AuthControllerTest extends TestMockData {

	@InjectMocks
	private AuthController authController;

	@MockBean
	private UserService userService;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(authController).setControllerAdvice(new GlobalExceptionHandler())
				.build();

		// Init common data for mocks
		initData();
	}

	@Test
	public void testAuthenticateUser_ResultOk() throws Exception {
		when(userService.authenticateUser(any(LoginRequest.class))).thenReturn(true);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(loginRequest));
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
}
