package com.example.todoservice.controller.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.todoservice.controller.TestMockData;
import com.example.todoservice.exception.RecordNotFoundException;
import com.example.todoservice.model.User;
import com.example.todoservice.repository.UserRepository;
import com.example.todoservice.service.UserService;

@RunWith(SpringRunner.class)
public class UserServiceTest extends TestMockData {

	@InjectMocks
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	private Optional<User> userData;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		// Init common data for mocks
		initData();

		userData = Optional.of(user);
	}

	@Test
	public void testAuthenticateUser_ResultOk() throws Exception {
		when(userRepository.findOneByUsername(any(String.class))).thenReturn(userData);

		Boolean result = userService.authenticateUser(loginRequest);
		assertThat(result).isTrue();
	}

	@Test(expected = RecordNotFoundException.class)
	public void testAuthenticateUser_InvalidLoginDetails() throws Exception {
		when(userRepository.findOneByUsername(any(String.class))).thenReturn(Optional.empty());

		userService.authenticateUser(loginRequest);
	}

}
