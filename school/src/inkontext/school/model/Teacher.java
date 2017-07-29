package inkontext.school.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;

@Entity
@Table(name = "teacher")
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Getter
@Slf4j
public class Teacher extends AbstractAggregateRoot {
	@EmbeddedId //
	@AttributeOverride(name = "id", column = @Column(name = "tch_id")) //
	private TeacherId teacherId = new TeacherId();

	@Column(name = "tch_publicname")
	private String publicName;

	@Column(name = "tch_username")
	private String username;

	@Column(name = "tch_isactive")
	private Boolean isActive;

	public Teacher(TeacherId teacherId, String publicName, String username, Boolean isActive) {
		this.teacherId = teacherId;
		this.publicName = publicName;
		this.username = username;
		this.isActive = isActive;
	}
}