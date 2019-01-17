package com.example.todoservice.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HelperTest {

	@Test
	public void testEncodePassword_ResultOk() throws Exception {
		String password = "password";
		
		String encodedPassword = Helper.encodePassword(password);
		assertThat(password).isNotEqualTo(encodedPassword);
	}

}
