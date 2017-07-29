package inkontext.school.jpa;

import inkontext.school.model.Teacher;
import inkontext.school.model.TeacherId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, TeacherId> {
}
