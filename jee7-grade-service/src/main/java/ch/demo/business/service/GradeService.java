package ch.demo.business.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import ch.demo.dom.Discipline;
import ch.demo.dom.Grade;

/**
 * Defines the contract of the grades service.
 * 
 * @author hostettler
 * 
 */
@Local
public interface GradeService extends Serializable {

	/** The domain of the grades. */
	int TOTAL = 100;

	void addGrade(Grade g);
	void deleteGrade(Long gradeId);
	List<Grade> getAllGradesForStudent(String studentId);
	Map<Discipline, Double> getAvgGradesForStudent(String studentId);
	Double getTotalAvgForStudent(String studentId);
	Double getAvgForStudents(List<String> studentsId);
	Double getAvgForStudentsAndDiscipline(List<String> studentsId, Discipline discipline);
	Long getNbGradesForStudents(List<String> studentsIds);
	Long getNbGradesForStudents(List<String> studentsIds, Discipline discipline);
	Long getNbGradesForStudent(String studentId);
	Long getNbGradesForStudent(String studentId, Discipline discipline);
}