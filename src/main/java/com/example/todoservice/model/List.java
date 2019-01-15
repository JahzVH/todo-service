package com.example.todoservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class List {

	@Id
	@GeneratedValue
	private Integer listId;

	@NonNull
	private String name;

	@OneToMany(mappedBy = "list", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private java.util.List<Item> items;
	
	@NonNull
	@OneToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

}
