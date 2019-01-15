package com.example.todoservice.controller.exception;

import java.time.LocalDateTime;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.todoservice.exception.RecordNotFoundException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger("TodoServiceApplication");

	@ExceptionHandler({ RecordNotFoundException.class })
	public ResponseEntity<ApiError> handleRecordNotFoundException(RecordNotFoundException e) {
		String message = "No record found with the given search criteria";
		logger.error(message, e);
		ApiError error = new ApiError(message, e.getMessage(), LocalDateTime.now());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ NullPointerException.class, PersistenceException.class })
	public ResponseEntity<ApiError> handleRuntimeException(RuntimeException e) {
		String message = "Unexpected error occurred";
		logger.error(message, e);
		ApiError error = new ApiError(message, e.getMessage(), LocalDateTime.now());

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
