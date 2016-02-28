package ch.demo.dom;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a grade for a given discipline.
 * 
 * @author hostettler
 */

@Entity
@Table(name = "CLASSES")
//@SecondaryTable(name = "STUDENTS_CLASSES", pkJoinColumns = @PrimaryKeyJoinColumn(
//name = "CLASS_ID", referencedColumnName = "CLASS_ID"))
public class StudentClass implements Serializable {

	/** The serial-id. */
	private static final long serialVersionUID = -1369317473509616839L;

	/** The unique id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/** The student that has obtained this grade. */
	@Column(name = "CLASS_ID", nullable = false)
	private String classId;
	
	/** The discipline of this grade. */
	@Column(name = "DISCIPLINE", nullable = false)
	private Discipline discipline;

	/** The actual grade. */
	@ElementCollection
	@CollectionTable(name = "STUDENTS_CLASSES")  
	@Column(name = "STUDENT_ID", nullable = false)
	private List<String> students;



	/**
	 * Constructor that builds a grade for a given discipline.
	 * 
	 * @param discipline
	 *            to grade.
	 */
	public StudentClass(final String classId, final Discipline discipline) {
		setClassId(classId);
		setDiscipline(discipline);
	}


	/**
	 * Constructor that builds a grade for a given discipline.
	 * 
	 * @param discipline
	 *            to grade.
	 */
	public StudentClass(final String classId, final Discipline discipline, List<String> students) {
		this(classId, discipline);
		setStudents(students);
	}
	
	/**
	 * Constructor that builds a grade for a given discipline.
	 * 
	 * @param discipline
	 *            to grade.
	 */
	public StudentClass(final String classId, final Discipline discipline, String... students) {
		this(classId, discipline);
		setStudents(Arrays.asList(students));
	}
	
	/**
	 * Constructor that builds a grade for a given discipline.
	 * 
	 */
	public StudentClass() {
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(classId, discipline, students);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final StudentClass other = (StudentClass) obj;
		return Objects.equals(this.classId, other.classId) && Objects.equals(this.discipline, other.discipline)
				&& Objects.equals(this.students, other.students);
	}

	@Override
	public String toString() { 
		return "{id:" +  id + ", classId:" + classId + ", discipline:" + discipline + ", students:"
				+ students + "}";
	}
	
	/**
	 * @return the discipline
	 */
	public final Discipline getDiscipline() {
		return discipline;
	}

	/**
	 * @param discipline
	 *            to set
	 */
	public final void setDiscipline(final Discipline pDiscipline) {
		this.discipline = pDiscipline;
	}
	/**
	 * @return the id
	 */
	public final long getId() {
		return id;
	}


	/**
	 * @return the classId
	 */
	public String getClassId() {
		return classId;
	}


	/**
	 * @param classId the classId to set
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}


	/**
	 * @return the students
	 */
	public List<String> getStudents() {
		return students;
	}


	/**
	 * @param students the students to set
	 */
	public void setStudents(List<String> students) {
		this.students = students;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	
}
