package ch.demo.dom;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
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
@Table(name = "GRADES")
public class Grade implements Serializable {

	/** The serial-id. */
	private static final long serialVersionUID = -1369317473509616839L;

	/** The unique id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/** The student that has obtained this grade. */
	@Column(name = "STUDENT_ID", nullable = false)
	private String studentId;

	/** The date of this grade. */
	@Column(name = "DATE", nullable = false)
	private Date date;

	/** The discipline of this grade. */
	@Column(name = "DISCIPLINE", nullable = false)
	private Discipline discipline;

	/** The actual grade. */
	@Column(name = "GRADE", nullable = true)
	private Integer grade;

	/**
	 * Constructor that builds a grade for a given discipline.
	 * 
	 * @param discipline
	 *            to grade.
	 * @param grade
	 *            the actual grade.
	 */
	public Grade(final String studentId, final Date date, final Discipline discipline, final Integer grade) {
		this.setStudentId(studentId);
		this.setDiscipline(discipline);
		this.setDate(date);
		this.setGrade(grade);
	}

	/**
	 * Constructor that builds a grade for a given discipline.
	 * 
	 */
	public Grade() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(studentId, date, discipline, grade);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Grade other = (Grade) obj;
		return Objects.equals(this.studentId, other.studentId) && Objects.equals(this.date, other.date)
				&& Objects.equals(this.discipline, other.discipline) && Objects.equals(this.grade, other.grade);
	}

	@Override
	public String toString() {
		return "Grade [id=" + getId() + ", studentId=" + studentId + ", date=" + date + ", discipline=" + discipline
				+ "]";
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
	 * @return the grade
	 */
	public final Integer getGrade() {
		return grade;
	}

	/**
	 * @param grade
	 *            the grade to set
	 */
	public final void setGrade(final Integer pGrade) {
		this.grade = pGrade;
	}

	/**
	 * @return the id
	 */
	public final long getId() {
		return id;
	}

	/**
	 * @return the studentId
	 */
	public String getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId
	 *            the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
