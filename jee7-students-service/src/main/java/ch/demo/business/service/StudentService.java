package ch.demo.business.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

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
	void add(@NotNull  Student student);

	/**
	 * @param id
	 *            of the student
	 * @return the student with the given id.
	 */
	Student getStudentById(Long id);
	
	/**
	 * @param key
	 *            of the student
	 * @return the student with the given key.
	 */
	Student getStudentByKey(String key);

	/**
	 * @param lastname
	 *            of the student
	 * @return the student with the given lastname.
	 */
	List<Student> getStudentByLastName(String lastname);

	/**
	 * @param studentId 
	 * @param student to update in the storage
	 */
	void update(String studentId, @NotNull Student student);
}