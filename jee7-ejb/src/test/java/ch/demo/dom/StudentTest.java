/**
 * 
 */
package ch.demo.dom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author hostettler
 *
 */
public class StudentTest {

	@Test
	public void shouldTestAvg() {
		Student s = new Student("Steve", "Hostettler", new Date());
		s.getGrades().add(new Grade(Discipline.BIOLOGY, 2));
		s.getGrades().add(new Grade(Discipline.MATHEMATICS, 3));
		s.getGrades().add(new Grade(Discipline.FRENCH, 4));
		Assert.assertEquals("#grades=3", 3, s.getGrades().size());
		Assert.assertEquals("avg=3", new Float(3), s.getAvgGrade());
	}

	@Test
	public void shouldTestEq() {
		Student s1 = new Student("Steve", "Hostettler", new Date());
		Student s2 = new Student("Steve", "Hostettler", new Date());

		Assert.assertEquals("s1=s2", s1, s2);
		s1.getGrades().add(new Grade(Discipline.BIOLOGY, 2));
		Assert.assertEquals("s1=s2", s1, s2);
		s1.setGender(Gender.FEMALE);
		s2.setGender(Gender.MALE);
		Assert.assertEquals("s1=s2", s1, s2);
		Address a1 = new Address();
		a1.setNumber("10");
		Address a2 = new Address();
		a1.setNumber("11");
		s1.setAddress(a1);
		s1.setAddress(a2);
		Assert.assertEquals("s1=s2", s1, s2);
	}

	@Test
	public void shouldTestNotEq() {
		Student s1 = new Student("Steve", "Hostettler", new Date());
		Student s2 = new Student("Steve", "Gostettler", new Date());
		Assert.assertFalse("s1=s2", s1.equals(s2));

	}

	@Test
	public void shouldToString() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("2013-12-4");
		
		Student s1 = new Student("Steve", "Hostettler", date);
		Assert.assertEquals("s1.toString()" , "Student [id=null, lastName=Steve, firstName=Hostettler, birthDate=Wed Dec 04 00:00:00 CET 2013, phoneNumber=null, grades=[]]", s1.toString());
	}
}
