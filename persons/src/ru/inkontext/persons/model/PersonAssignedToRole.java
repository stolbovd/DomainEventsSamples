package ru.inkontext.persons.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class PersonAssignedToRole {
	PersonId personId;
	FullName fullName;
	User user;
	Role role;
}
