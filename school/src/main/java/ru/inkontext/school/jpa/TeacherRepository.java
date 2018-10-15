package ru.inkontext.school.jpa;

import ru.inkontext.school.model.Teacher;
import ru.inkontext.school.model.TeacherId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, TeacherId> {
}
