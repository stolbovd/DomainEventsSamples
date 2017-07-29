package ru.inkontext.persons.model;

import ru.inkontext.core.Identifier;

import javax.persistence.Embeddable;

@Embeddable
public class PersonId extends Identifier {
	/**
	 * Creates a new unique identifier for accountancy entries.
	 */
	PersonId() {
		super();
	}

	/**
	 * Only needed for property editor, shouldn't be used otherwise.
	 *
	 * @param identifier The string representation of the identifier.
	 */
	public PersonId(String identifier) {
		super(identifier);
	}
}
