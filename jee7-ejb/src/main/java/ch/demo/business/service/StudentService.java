package ch.demo.business.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import ch.demo.dom.Student;

/**
 * Defines the contract of the student service. This is a local interface that
 * extends the remote interface. The local interface can only be used internally
 * in the same jar.
 * 
 * @author hostettler
 * 
 */
@Local
public interface StudentService extends Serializable {

	/** The domain of the grades. */
	int TOTAL = 100;

	/**
	 * @return all the registered students
	 */
	List<Student> getAll();

	/**
	 * @return the current number of students
	 */
	int getNbStudent();

	/**
	 * Adds a student to the list.
	 * 
	 * @param student
	 *            to add
	 */
	void add(Student student);

	/**
	 * @return an array that contains the distribution of the grades. It
	 *         partitions the grades in "n" parts.
	 * @param n
	 *            : number of parts
	 */
	Integer[] getDistribution(final int n);

	/**
	 * @param id
	 *            of the student
	 * @return the student with the given id.
	 */
	Student getStudentById(Long id);
	
	Student getStudentByKey(String key);

	List<Long> getAllIds();

	/**
	 * @param lastname
	 *            of the student
	 * @return the student with the given lastname.
	 */
	Student getStudentByLastName(String lastname);

	/**
	 * @return the number of time the service has been invoked so far.
	 */
	Long getStatistics();

	void update(Student student);
}