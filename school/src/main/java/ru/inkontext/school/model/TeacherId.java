package ru.inkontext.school.model;

import ru.inkontext.core.Identifier;

import javax.persistence.Embeddable;

@Embeddable
public class TeacherId extends Identifier {
	/**
	 * Creates a new unique identifier for accountancy entries.
	 */
	TeacherId() {
		super();
	}

	/**
	 * Only needed for property editor, shouldn't be used otherwise.
	 *
	 * @param identifier The string representation of the identifier.
	 */
	public TeacherId(String identifier) {
		super(identifier);
	}
}
