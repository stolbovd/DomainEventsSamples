package ru.inkontext.core;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * This implementation from Salespoint Framework
 * http://st.inf.tu-dresden.de/SalesPoint
 * @author Hannes Weisbach
 * @author Thomas Dedek
 * @author Oliver Gierke
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@RequiredArgsConstructor
@EqualsAndHashCode
public class Identifier implements Serializable {

	private static final long serialVersionUID = -859038235950680970L;

	private final @Column(unique = true) String id;

	public Identifier() {
		this.id = UUID.randomUUID().toString();
	}

	public String getIdentifier() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id;
	}
}
