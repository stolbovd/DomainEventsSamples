package ru.inkontext.persons.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Getter
public class Group extends AbstractAggregateRoot {
	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	Role role;

	@ElementCollection
	@CollectionTable(name="group_person", joinColumns=@JoinColumn(name="grp_role"))
	@AttributeOverrides({
			@AttributeOverride(name="id", column=@Column(name="grp_per_id"))
	})
	private Set<PersonId> personIds;

	public Group(Role role) {
		super();
		this.role = role;
	}

	public void addMember(@NonNull Person person) {
		if (personIds == null)
			personIds = new HashSet<>();
		personIds.add(person.getPersonId());
		registerEvent(person.createEventAssignedToRole(role));
	}
}