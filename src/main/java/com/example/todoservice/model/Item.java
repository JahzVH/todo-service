package com.example.todoservice.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"list"})
@Entity
public class Item {

	@Id
	@GeneratedValue
	private Integer itemId;

	private String item;

	private Boolean completed;

	private LocalDateTime dueDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "listId")
	@JsonIgnore
	private List list;

	@OneToOne
	@JoinColumn(name = "userId")
	@JsonIgnore
	private User user;

}
