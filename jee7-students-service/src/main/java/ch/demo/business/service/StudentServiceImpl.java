package ch.demo.business.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import ch.demo.dom.Student;

/**
 * 
 * Provides a set of services for the students objects.
 * 
 * @author hostettler
 * 
 */
@Stateless
//@Interceptors({ PerformanceInterceptor.class })
public class StudentServiceImpl implements StudentService {

	/** The serial-id. */
	private static final long serialVersionUID = 1386508985359072399L;

	/**
	 * The entity manager that manages the persistence. As there is only one
	 * persistence unit, it takes it by default.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * The following service is allowed for all connected users order by their technical id. Furthermore,
	 * the @Benchmakable annotation triggers an interceptor that will measure
	 * the time consumed by this method.
	 */
	@Override
	public List<Student> getAll() {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> c = qb.createQuery(Student.class);

		Root<Student> variableRoot = c.from(Student.class);
		c.select(variableRoot);
		c.orderBy(qb.asc(variableRoot.get("id")));
		TypedQuery<Student> query = entityManager.createQuery(c);
		return query.getResultList();
	}

	@Override
	public int getNbStudent() {
		return getAll().size();
	}

	/**
	 * Adding a student to the database is admin stuff.
	 */
	@Override
	public void add(@NotNull final Student student) {
		student.setId(null);
		student.setStudentId(getNextStudentId());
		entityManager.persist(student);
	}

	/**
	 * Adding a student to the database is admin stuff.
	 */
	@Override
	public void update(String studentId, @NotNull final Student student) {
		Student s = getStudentByKey(studentId);
		s.copyBusinessFieldsFrom(student);
	}

	@Override
	public  Student getStudentById(final Long id) {
		// Using JPA Typed query system greatly reduces the name mismatch
		// problems.
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> c = qb.createQuery(Student.class);
		Root<Student> from = c.from(Student.class);
		Predicate condition = qb.equal(from.get("id"), id);
		c.where(condition);
		TypedQuery<Student> query = entityManager.createQuery(c);
		return query.getSingleResult();
	}

	@Override
	public Student getStudentByKey(final String key) {
		// Using JPA Typed query system greatly reduces the name mismatch
		// problems.
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> c = qb.createQuery(Student.class);
		Root<Student> from = c.from(Student.class);
		Predicate condition = qb.equal(from.get("studentId"), key);
		c.where(condition);
		TypedQuery<Student> query = entityManager.createQuery(c);
		return query.getSingleResult();
	}

	@Override
	public List<Student> getStudentByLastName(final String lastname) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> c = qb.createQuery(Student.class);
		Root<Student> from = c.from(Student.class);
		Predicate condition = qb.equal(from.get("lastName"), lastname);
		c.where(condition);
		TypedQuery<Student> query = entityManager.createQuery(c);
		return query.getResultList();
	}
	
	/**
	 * @return a new student id.
	 */
	private Integer getNextStudentId() {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Integer> c = qb.createQuery(Integer.class);
		Root<Student> from = c.from(Student.class);
		c.select(qb.max(from.get("studentId")));		
		TypedQuery<Integer> query = entityManager.createQuery(c);
		System.out.println(query);
		
		Integer nb = query.getSingleResult(); 
		if (nb == null) {
			return 0;
		} else {
			return nb + 1;	
		}
		
	}
}
