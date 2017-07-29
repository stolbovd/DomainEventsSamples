package inkontext.school;

import inkontext.school.jpa.TeacherRepository;
import inkontext.school.model.Teacher;
import inkontext.school.model.TeacherId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Service;
import ru.inkontext.persons.model.FullName;
import ru.inkontext.persons.model.PersonAssignedToRole;
import ru.inkontext.persons.model.Role;

@Service
@Slf4j
public class SchoolService extends AbstractRepositoryEventListener {

	@Autowired
	TeacherRepository teacherRepository;

	@Autowired
	RabbitTemplate rabbitTemplate;

	//ToDo move to Adapter and Translater
	String createTeacherPublicNameFromFullName(FullName fullName) {
		return "Педагог "+fullName.nameSecName();
	}

	@RabbitListener(queues = "#{personsQueue.name}")
	public void receivePersonAssignedToRole(PersonAssignedToRole event) throws InterruptedException {
		log.info("event " + event.getClass() + " received by @RabbitListener " + event);

		if (event.getRole() == Role.TEACHER) {
			Teacher teacher = new Teacher(new TeacherId(event.getPersonId().getIdentifier()),
					createTeacherPublicNameFromFullName(event.getFullName()),
					event.getUser().getUsername(),
					true);

			teacherRepository.save(teacher);
		}
	}

	@RabbitListener(queues = "#{schoolQueue.name}")
	public void receiveTeacherCreated(Teacher teacher) throws InterruptedException {
		log.info("Teacher " + teacher.getClass() + " received by @RabbitListener " + teacher);
	}

	@Override
	public void onAfterCreate(Object entity) {
		if (entity instanceof Teacher) {
			Teacher teacher = (Teacher) entity;

			rabbitTemplate.convertAndSend("direct.events", "schoolKey", teacher,
					message -> {
						log.info("Message in rabbitTemplate.convertAndSend: " + message);
						return message;
					});
			log.info(teacher + " created");
		} else
			log.error("created entity is not Teacher "+entity);
	}
}
