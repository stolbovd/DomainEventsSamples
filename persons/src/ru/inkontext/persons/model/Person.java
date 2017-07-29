package ru.inkontext.persons.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "person")
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Getter
@Slf4j
public class Person extends AbstractAggregateRoot {
	@EmbeddedId //
	@AttributeOverride(name = "id", column = @Column(name = "per_id")) //
	private PersonId personId = new PersonId();

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "family", column = @Column(name = "per_family")),
			@AttributeOverride(name = "name", column = @Column(name = "per_name")),
			@AttributeOverride(name = "secname", column = @Column(name = "per_secname"))
	})
	private FullName fullName;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "username", column = @Column(name = "per_username")),
			@AttributeOverride(name = "enabled", column = @Column(name = "per_userenabled"))
	})
	private User user;

	@Column(name = "per_birthday")
	private LocalDate birthday;

	PersonAssignedToRole createEventAssignedToRole(Role role) {
		return PersonAssignedToRole.of(personId, fullName, user, role);
	}
}