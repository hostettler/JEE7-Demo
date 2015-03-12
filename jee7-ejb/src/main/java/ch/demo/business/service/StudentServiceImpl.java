package ch.demo.business.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.enterprise.inject.Instance;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ch.demo.business.interceptors.PerformanceInterceptor;
import ch.demo.dom.Address;
import ch.demo.dom.Student;

/**
 * 
 * Provides a set of services for the students objects.
 * 
 * @author hostettler
 * 
 */
@Stateless
@Interceptors({ PerformanceInterceptor.class })
public class StudentServiceImpl implements StudentService {

	/** The serial-id. */
	private static final long serialVersionUID = 1386508985359072399L;

	/**
	 * The entity manager that manages the persistence. As there is only one
	 * persistence unit, it takes it by default.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	Instance<Address> b;
	
	/** The number of times, the service has been invoked. */
	private Long numberOfAccess = 0l;

	/**
	 * The following service is allowed for all connected users. Furthermore,
	 * the @Benchmakable annotation triggers an interceptor that will measure
	 * the time consumed by this method.
	 */
	@Override
	@RolesAllowed({ "user" })
	public List<Student> getAll() {
		numberOfAccess++;
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> c = qb.createQuery(Student.class);
		TypedQuery<Student> query = entityManager.createQuery(c);
		return query.getResultList();
	}

	@Override
	@RolesAllowed({ "user" })
	public int getNbStudent() {
		numberOfAccess++;
		return getAll().size();
	}

	/**
	 * Adding a student to the database is admin stuff.
	 */
	@Override
	@RolesAllowed({ "manager" })
	public void add(final Student student) {
		numberOfAccess++;
		entityManager.persist(student);
	}

	
	/**
	 * Adding a student to the database is admin stuff.
	 */
	@Override
	@RolesAllowed({ "manager" })
	public void update(final Student student) {
		numberOfAccess++;
		Student currentStudent = getStudentById(student.getId());
		currentStudent.setFirstName(student.getFirstName());
		currentStudent.setLastName(student.getLastName());
		currentStudent.setBirthDate(student.getBirthDate());
	}
	
	@Override
	@RolesAllowed({ "user" })
	public final Long getStatistics() {
		return numberOfAccess;
	}

	@Override
	@RolesAllowed({ "user" })
	public final Integer[] getDistribution(final int n) {
		numberOfAccess++;
		Integer[] grades = new Integer[n];

		for (int i = 0; i < n; i++) {
			grades[i] = 0;
		}

		for (Student s : this.getAll()) {
			grades[(s.getAvgGrade().intValue() - 1) / (TOTAL / n)]++;
		}
		return grades;
	}

	@Override
	@RolesAllowed({ "manager" })
	public final Student getStudentById(final Long id) {
		numberOfAccess++;
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
	@RolesAllowed({ "user" })
	public Student getStudentByKey(final String key) {
		numberOfAccess++;
		// Using JPA Typed query system greatly reduces the name mismatch
		// problems.
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> c = qb.createQuery(Student.class);
		Root<Student> from = c.from(Student.class);
		Predicate condition = qb.equal(from.get("id"), key);
		c.where(condition);
		TypedQuery<Student> query = entityManager.createQuery(c);
		return query.getSingleResult();
	}

	@Override
	public List<Long> getAllIds() {
		numberOfAccess++;
		Query q = entityManager.createNativeQuery("SELECT id FROM STUDENTS",
				Long.class);
		@SuppressWarnings("unchecked")
		List<Long> ids = q.getResultList();
		return ids;
	}

	@Override
	@RolesAllowed({ "user" })
	public Student getStudentByLastName(final String lastname) {
		numberOfAccess++;
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Student> c = qb.createQuery(Student.class);
		Root<Student> from = c.from(Student.class);
		Predicate condition = qb.equal(from.get("lastName"), lastname);
		c.where(condition);
		TypedQuery<Student> query = entityManager.createQuery(c);
		return query.getSingleResult();
	}

}
