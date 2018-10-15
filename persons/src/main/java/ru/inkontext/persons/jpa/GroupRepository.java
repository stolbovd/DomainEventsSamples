package ru.inkontext.persons.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inkontext.persons.model.Group;
import ru.inkontext.persons.model.Role;

public interface GroupRepository extends JpaRepository<Group, Role> {
}
