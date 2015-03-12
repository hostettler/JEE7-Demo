package ch.demo.business.service;

import java.util.Date;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import ch.demo.dom.Discipline;
import ch.demo.dom.Grade;
import ch.demo.dom.PhoneNumber;
import ch.demo.dom.Student;
import ch.demo.test.utils.EJB31Runner;
import ch.demo.test.utils.IntegrationTest;

/**
 * Test the student service mock implementation.
 * 
 * @author hostettler
 */
@Category(IntegrationTest.class)
@RunWith(EJB31Runner.class)
@ManagedBean
public class StudentServiceImplIntegrationTest {


	@Inject
	StudentService ejb;

	@Test
	public void shouldAddANewStudent() throws Exception {
		EJB31Runner.login("admin", "user1");

		int n = ejb.getAll().size();

		Student s = new Student("Hostettler", "Steve", new Date());
		s.setPhoneNumber(new PhoneNumber(0, 0, 0));
		for (Discipline d : Discipline.values()) {
			Grade g = new Grade(d, 10);
			s.getGrades().add(g);
		}

		ejb.add(s);

		Assert.assertEquals(n + 1, ejb.getAll().size());
		Assert.assertEquals("Hostettler", ejb.getStudentByKey(s.getKey())
				.getLastName());
		
		
		Assert.assertEquals("Hostettler", ejb.getStudentById(1l).getLastName());

		Assert.assertEquals(n + 1, ejb.getNbStudent());
		
		Assert.assertEquals("Student's name is Steve", "Steve", ejb.getStudentByLastName("Hostettler").getFirstName());

		Assert.assertEquals(1, ejb.getAll().size());
		
		Assert.assertEquals(new Long(9), ejb.getStatistics());
	}

}
