package com.example.todoservice.controller.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
	
	@NonNull
	private String item;
	
	@NonNull
	private Boolean completed;
	
	private LocalDateTime dueDate;

}
