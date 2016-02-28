package ch.demo.business.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import ch.demo.dom.StudentClass;

/**
 * Defines the contract of the grades service.
 * 
 * @author hostettler
 * 
 */
@Local
public interface StudentClassService extends Serializable {
	void addStudentToClass(String studentId, String classId);
	void removeStudentFromClass(String studentId, String classId);
	StudentClass getStudentClass(String studentClassId);
	Double getAvgForStudentsClass(String studentClassId);
	StudentClass createClass(StudentClass sc);
	List<String> getStudentsOfClass(String studentClassId);
}