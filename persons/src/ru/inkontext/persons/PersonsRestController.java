package ru.inkontext.persons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.inkontext.persons.model.PersonId;
import ru.inkontext.persons.model.Role;

@Slf4j
@RestController
@RequestMapping(value = "/rest")
public class PersonsRestController {

	@Autowired
	private PersonService personService;

	@PostMapping("/persons/{personId}/assignToRole/{role}")
	public void assignPersonToRole(@PathVariable("personId") PersonId personId,
								   @PathVariable("role") Role role) {
		personService.assignPersonToRole(personId, role);
	}
}
