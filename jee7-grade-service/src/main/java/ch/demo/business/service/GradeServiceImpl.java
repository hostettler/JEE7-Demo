package ch.demo.business.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ch.demo.business.interceptors.PerformanceInterceptor;
import ch.demo.dom.Discipline;
import ch.demo.dom.Grade;

/**
 * 
 * Provides a set of services for the students objects.
 * 
 * @author hostettler
 * 
 */
@Stateless
@Interceptors({ PerformanceInterceptor.class })
public class GradeServiceImpl implements GradeService {

	/** The serial-id. */
	private static final long serialVersionUID = 1386508985359072399L;

	/**
	 * The entity manager that manages the persistence. As there is only one
	 * persistence unit, it takes it by default.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addGrade(Grade g) {
		entityManager.persist(g);
	}

	@Override
	public void deleteGrade(Long gradeId) {
		Grade g = entityManager.find(Grade.class, gradeId);
		entityManager.remove(g);
	}

	@Override
	public List<Grade> getAllGradesForStudent(String studentId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Grade> criteriaQuery = criteriaBuilder.createQuery(Grade.class);
		Root<Grade> grade = criteriaQuery.from(Grade.class);
		// Here there was a bug because I used greaterThan instead of equals
		// (bad copy-paste)
		criteriaQuery.where(criteriaBuilder.equal(grade.get("studentId"), studentId));
		TypedQuery<Grade> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	

	@Override
	public Map<Discipline, Double> getAvgGradesForStudent(String studentId) {
		
		if (getNbGradesForStudent(studentId) == 0) {
			throw new EntityNotFoundException(studentId);
		}
		
		TypedQuery<Object[]> q = entityManager.createQuery(
				"select g.discipline, avg(g.grade) from Grade g where g.studentId=:studentId group by g.discipline",
				Object[].class);
		q.setParameter("studentId", studentId);
		List<Object[]> l = q.getResultList();
		Map<Discipline, Double> map = new HashMap<Discipline, Double>();
		for (Object[] r : l) {
			map.put((Discipline) r[0], (Double) r[1]);
		}
		return map;
	}

	@Override
	public Double getTotalAvgForStudent(String studentId) {
		if (getNbGradesForStudent(studentId) == 0) {
			throw new EntityNotFoundException(studentId);
		}
		
		Query q = entityManager.createQuery("select avg(g.grade) from Grade g where g.studentId=:studentId");
		q.setParameter("studentId", studentId);
		return (Double) q.getSingleResult();

	}

	@Override
	public Double getAvgForStudents(List<String> studentsId) {
		if (getNbGradesForStudents(studentsId) == 0) {
			throw new EntityNotFoundException(studentsId.toString());
		}
		
		Query q = entityManager.createQuery("select avg(g.grade) from Grade g where g.studentId in :studentId");
		q.setParameter("studentId", studentsId);
		return (Double) q.getSingleResult();
	}

	@Override
	public Long getNbGradesForStudents(List<String> studentsIds) {
		Query q = entityManager.createQuery("select count(g.grade) from Grade g where g.studentId in :studentId");
		q.setParameter("studentId", studentsIds);
		return (Long) q.getSingleResult();
	}

	@Override
	public Long getNbGradesForStudents(List<String> studentsIds, Discipline discipline) {
		Query q = entityManager.createQuery("select count(g.grade) from Grade g where  g.discipline = :discipline and  g.studentId in :studentId");
		q.setParameter("studentId", studentsIds);
		q.setParameter("discipline", discipline);
		return (Long) q.getSingleResult();
	}

	@Override
	public Long getNbGradesForStudent(String studentId, Discipline discipline) {
		List<String> studentsIds = new ArrayList<String>();
		studentsIds.add(studentId);
		return getNbGradesForStudents(studentsIds, discipline);
	}
	
	@Override
	public Long getNbGradesForStudent(String studentId) {
		List<String> studentsIds = new ArrayList<String>();
		studentsIds.add(studentId);
		return getNbGradesForStudents(studentsIds);
	}

	
	
	@Override
	public Double getAvgForStudentsAndDiscipline(List<String> studentsId, Discipline discipline) {
		Query q = entityManager.createQuery("select avg(g.grade) from Grade g where g.discipline = :discipline and g.studentId in :studentId");
		q.setParameter("discipline", discipline);
		q.setParameter("studentId", studentsId);
		return (Double) q.getSingleResult();
	}



}
