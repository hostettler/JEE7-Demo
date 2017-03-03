package ch.demo.business.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import ch.demo.dom.Discipline;
import ch.demo.dom.Grade;
import ch.demo.test.IntegrationTest;
import junit.framework.Assert;

@RunWith(Arquillian.class)
@Category(IntegrationTest.class)
public class GradeServiceImplTest {

	@Deployment
	public static JavaArchive create() {
		return ShrinkWrap.create(JavaArchive.class, "jee7-demo.jar").addPackages(true, "ch.demo")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("test-persistence.xml", "persistence.xml");
	}

	@Inject
	GradeService service;

	@PersistenceContext
	EntityManager em;

	@Test
	public void shouldAddAGrade() {

		
		TypedQuery<Grade> query = em.createQuery("SELECT g FROM Grade g", Grade.class);
		List<Grade> results = query.getResultList();
		int numberOfGrades = results.size();

		query = em.createQuery("SELECT g FROM Grade g where g.studentId = :studentId", Grade.class);
		query.setParameter("studentId", "001");
		int numberOf001Grades = query.getResultList().size();
		
		Grade g = new Grade("001", new Date(), Discipline.BIOLOGY, 40);
		service.addGrade(g);
		g = new Grade("001", new Date(), Discipline.MATHEMATICS, 10);
		service.addGrade(g);
		g = new Grade("001", new Date(), Discipline.ENGLISH, 20);
		service.addGrade(g);
		g = new Grade("002", new Date(), Discipline.ENGLISH, 40);
		service.addGrade(g);

		query = em.createQuery("SELECT g FROM Grade g", Grade.class);
		results = query.getResultList();
		Assert.assertEquals(numberOfGrades + 4, results.size());

		query = em.createQuery("SELECT g FROM Grade g where g.studentId = :studentId", Grade.class);
		query.setParameter("studentId", "001");
		results = query.getResultList();
		Assert.assertEquals(numberOf001Grades + 3, results.size());
	}

	@Test
	public void shouldRemoveGrade() {
		Grade g = new Grade("001", new Date(), Discipline.BIOLOGY, 40);
		service.addGrade(g);
		TypedQuery<Grade> query = em.createQuery("SELECT g FROM Grade g", Grade.class);
		List<Grade> results = query.getResultList();
		int numberOfGrades = results.size();
		service.deleteGrade(results.get(0).getId());

		query = em.createQuery("SELECT g FROM Grade g", Grade.class);
		results = query.getResultList();
		Assert.assertEquals(numberOfGrades - 1, results.size());

	}

	@Test
	public void shouldRetriveAllGradesForStudents() {

		List<Grade> results = service.getAllGradesForStudent("001");
		int nbGradeFor001 = results.size();
		results = service.getAllGradesForStudent("002");
		int nbGradeFor002 = results.size();

		Grade g = new Grade("001", new Date(), Discipline.BIOLOGY, 40);
		service.addGrade(g);
		g = new Grade("001", new Date(), Discipline.MATHEMATICS, 10);
		service.addGrade(g);
		g = new Grade("001", new Date(), Discipline.ENGLISH, 20);
		service.addGrade(g);

		g = new Grade("002", new Date(), Discipline.ENGLISH, 40);
		service.addGrade(g);

		results = service.getAllGradesForStudent("001");
		Assert.assertEquals(nbGradeFor001 + 3, results.size());
		results = service.getAllGradesForStudent("002");
		Assert.assertEquals(nbGradeFor002 + 1, results.size());
	}

	@Test
	public void shouldComputeTotalAvgGradeForStudent() {

		List<Grade> results = service.getAllGradesForStudent("001");
		int nbGradeFor001 = results.size();
		Long sum = 0L;
		if (nbGradeFor001 > 0) {
			Query q = em.createQuery("select sum(g.grade) from Grade g where g.studentId=:studentId");
			q.setParameter("studentId", "001");
			sum = (Long) q.getSingleResult();
		}

		Grade g = new Grade("001", new Date(), Discipline.BIOLOGY, 40);
		service.addGrade(g);
		g = new Grade("001", new Date(), Discipline.MATHEMATICS, 10);
		service.addGrade(g);
		g = new Grade("001", new Date(), Discipline.ENGLISH, 20);
		service.addGrade(g);

		Double avg = service.getTotalAvgForStudent("001");
		Assert.assertEquals((sum + 40d + 10d + 20d) / (nbGradeFor001 + 3d), avg);

	}

	@Test
	public void shouldComputeAllAvgGradeForStudent() {

		Grade g = new Grade("001", new Date(), Discipline.BIOLOGY, 40);
		service.addGrade(g);
		g = new Grade("001", new Date(), Discipline.MATHEMATICS, 10);
		service.addGrade(g);
		g = new Grade("001", new Date(), Discipline.ENGLISH, 20);
		service.addGrade(g);
		Map<Discipline, Double> avg = service.getAvgGradesForStudent("001");
		Assert.assertTrue(avg.get(Discipline.BIOLOGY) > 0);
	}

	@Test
	public void shouldAvgForAListOfStudents() {

		List<Grade> results = service.getAllGradesForStudent("001");
		Integer sum001 = 0;
		for (Grade g : results)
			sum001 += g.getGrade();
		int nbGrade001 = results.size();

		results = service.getAllGradesForStudent("002");
		Integer sum002 = 0;
		for (Grade g : results)
			sum002 += g.getGrade();
		int nbGradeFor002 = results.size();

		Grade g = new Grade("001", new Date(), Discipline.FRENCH, 80);
		service.addGrade(g);

		g = new Grade("002", new Date(), Discipline.GERMAN, 20);
		service.addGrade(g);

		Double avg = service.getAvgForStudents(Arrays.asList(new String[] { "001", "002" }));
		Double expected = (sum001 + sum002 + 80d + 20d) / (nbGrade001 + nbGradeFor002 + 2d);
		Assert.assertEquals(expected, avg);
	}

	@Test
	public void shouldAvgForAListOfStudentsAndAGiverDiscipline() {

		List<Grade> results = service.getAllGradesForStudent("001");
		Integer sum001 = 0;
		int nbGrade001 = 0;
		for (Grade g : results)
			if (g.getDiscipline() == Discipline.GERMAN) {
				sum001 += g.getGrade();
				nbGrade001++;
			}

		results = service.getAllGradesForStudent("002");
		Integer sum002 = 0;
		int nbGradeFor002 = 0;
		for (Grade g : results)
			if (g.getDiscipline() == Discipline.GERMAN) {
				sum002 += g.getGrade();
				nbGradeFor002++;
			}

		Grade g = new Grade("001", new Date(), Discipline.GERMAN, 80);
		service.addGrade(g);

		g = new Grade("002", new Date(), Discipline.GERMAN, 20);
		service.addGrade(g);

		Double avg = service.getAvgForStudentsAndDiscipline(Arrays.asList(new String[] { "001", "002" }),
				Discipline.GERMAN);
		Double expected = (sum001 + sum002 + 80d + 20d) / (nbGrade001 + nbGradeFor002 + 2d);
		Assert.assertEquals(expected, avg);
	}
}
