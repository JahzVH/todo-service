package com.example.todoservice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Helper {

	/**
	 * 
	 * @param password
	 * @return
	 */
	public static String encodePassword(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

}
