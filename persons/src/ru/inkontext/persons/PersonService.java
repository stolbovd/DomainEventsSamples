package ru.inkontext.persons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.inkontext.persons.jpa.GroupRepository;
import ru.inkontext.persons.jpa.PersonRepository;
import ru.inkontext.persons.model.*;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Slf4j
public class PersonService extends AbstractRepositoryEventListener {

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Transactional
	void assignPersonToRole(PersonId personId, Role role) {
		Person person = personRepository.findOne(personId);
		if (person == null)
			throw new RuntimeException("person with id " + personId + " not found");
		Group group = groupRepository.findOne(role);
		if (group == null)
			group = new Group(role);
		else {
			Set<PersonId> personIds = group.getPersonIds();
			if (personIds != null && personIds.contains(personId)) {
				log.info(personId + " was assigned to " + role + " early");
				return;
			}
		}
		group.addMember(person);

		groupRepository.save(group);
	}

	@TransactionalEventListener
	public void handlePersonAssignedToRoleEvent(PersonAssignedToRole event) {
		FullName fullName = event.getFullName();
		Role role = event.getRole();

		log.info(fullName + " assign to Role " + role + " completed");

		rabbitTemplate.convertAndSend("direct.events", "personsKey", event,
				message -> {
					log.info("Message in rabbitTemplate.convertAndSend: " + message);
					return message;
				});
		rabbitTemplate.convertAndSend("direct.events", "personsKey", event.getFullName());
	}

//	@RabbitListener(queues = "#{personsQueue.name}")
//	public void receive(PersonAssignedToRole event) throws InterruptedException {
//		log.info("event " + event.getClass() + " received by @RabbitListener " + event);
//	}

	@RabbitListener(queues = "#{personsQueue.name}")
	public void receiveFullName(FullName event) throws InterruptedException {
		log.info("event " + event.getClass() + " received by @RabbitListener " + event);
	}

	@Override
	public void onAfterCreate(Object entity) {
		if (entity instanceof Person)
			log.info(((Person) entity) + " created ");
		else if (entity instanceof Group)
			log.info(((Group) entity) + " created ");
	}

	@EventListener
	public void on(PersonAssignedToRole event) {
		log.info("event " + event.getClass() + " received by @EventListener " + event);
	}
}
