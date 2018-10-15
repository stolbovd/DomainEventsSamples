package ru.inkontext.persons.model;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@ToString
public class FullName {
	private String family;
	private String name;
	private String secName;

	FullName() {
	}

	public FullName(String family, String name, String secName) {
		this.family = family;
		this.name = name;
		this.secName = secName;
	}

	public String nameSecName() {
		return name + " " + secName;
	}
}
