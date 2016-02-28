package ch.demo.business.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ch.demo.business.interceptors.PerformanceInterceptor;
import ch.demo.dom.StudentClass;

/**
 * 
 * Provides a set of services for the students objects.
 * 
 * @author hostettler
 * 
 */
@Stateless
@Interceptors({ PerformanceInterceptor.class })
public class StudentClassServiceImpl implements StudentClassService {

	/** The serial-id. */
	private static final long serialVersionUID = 1386508985359072399L;


	@Inject
	GradeServiceConsumer gradeService;
	
	/**
	 * The entity manager that manages the persistence. As there is only one
	 * persistence unit, it takes it by default.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public StudentClass createClass(StudentClass studentClass) {
		entityManager.persist(studentClass);
		return studentClass;
	}
	
	@Override
	public void addStudentToClass(String studentId, String studentClassId) {
		StudentClass sc = getStudentClass(studentClassId);
		sc.getStudents().add(studentId);
	}

	@Override
	public void removeStudentFromClass(String studentId, String studentClassId) {
		StudentClass sc = getStudentClass(studentClassId);
		entityManager.remove(sc);
	}

	@Override
	public StudentClass getStudentClass(String studentClassId) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<StudentClass> c = qb.createQuery(StudentClass.class);
		Root<StudentClass> from = c.from(StudentClass.class);
		Predicate condition = qb.equal(from.get("classId"), studentClassId);
		c.where(condition);
		TypedQuery<StudentClass> query = entityManager.createQuery(c);
		return query.getSingleResult();
	}
	
	@Override
	public List<String> getStudentsOfClass(String studentClassId) {
		List<String> s = getStudentClass(studentClassId).getStudents();
		// TODO : WTF does the lazy loading not load the collections
		s.toString();
		return s;
	}
	

	@Override
	public Double getAvgForStudentsClass(String studentClassId) {
		StudentClass sc = getStudentClass(studentClassId);
		return gradeService.getAvgGradeForStudents(sc.getStudents());
	}

}
