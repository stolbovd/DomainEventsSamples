package ru.inkontext.persons.model;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@ToString
public class User {
	private String username;
	private Boolean enabled;

	User() {}

	public User(String username, Boolean enabled) {
		this.username = username;
		this.enabled = enabled;
	}
}
