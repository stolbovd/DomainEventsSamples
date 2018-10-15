/*
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.inkontext.support;

import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import ru.inkontext.core.Identifier;

import java.io.Serializable;

/**
 * {@link BackendIdConverter} artificially transforming the actual book id into some magic {@link String} and back.
 * 
 * @author Oliver Gierke
 */
//@Component
public class IdConverter implements BackendIdConverter {

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.rest.webmvc.support.BackendIdConverter#fromRequestId(java.lang.String, java.lang.Class)
	 */
	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		return new Identifier(id);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.rest.webmvc.support.BackendIdConverter#toRequestId(java.lang.Object, java.lang.Class)
	 */
	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {

		return ((Identifier) id).getIdentifier();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.plugin.core.Plugin#supports(java.lang.Object)
	 */
	@Override
	public boolean supports(Class<?> delimiter) {
		return Identifier.class.equals(delimiter);
	}
}
