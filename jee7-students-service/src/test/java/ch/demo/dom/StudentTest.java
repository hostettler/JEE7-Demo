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
	public void shouldBeEqualWhenBusinessKeyEqualAndStudentIdNull() {
		Date birthDate = new Date();
		Student s1 = new Student("Steve", "Hostettler", birthDate, new PhoneNumber("+33-6980-75273"));
		Student s2 = new Student("Steve", "Hostettler", birthDate, new PhoneNumber("+33-6980-75273"));

		Assert.assertEquals("s1=s2", s1.hashCode(), s2.hashCode());
		Assert.assertEquals("s1=s2", s1, s2);
		s1.setGender(Gender.FEMALE);
		s2.setGender(Gender.MALE);
		Assert.assertEquals("s1=s2", s1.hashCode(), s2.hashCode());
		Assert.assertEquals("s1=s2", s1, s2);
		Address a1 = new Address();
		a1.setNumber("10");
		Address a2 = new Address();
		a1.setNumber("11");
		s1.setAddress(a1);
		s1.setAddress(a2);
		Assert.assertEquals("s1=s2", s1, s2);
		Assert.assertEquals("s1=s2", s1.hashCode(), s2.hashCode());
	}

	@Test
	public void shouldBeEqualWhenStudentIdAndBusinessKeyEqual() {
		Date birthDate = new Date();
		Student s1 = new Student("Steve", "Hostettler", birthDate, new PhoneNumber("+33698075273"));
		Student s2 = new Student("Steve", "Hostettler", birthDate, new PhoneNumber("+33698075273"));

		s1.setStudentId(123);
		s2.setStudentId(123);

		Assert.assertEquals("s1=s2", s1.hashCode(), s2.hashCode());
		Assert.assertEquals("s1=s2", s1, s2);
		s1.setGender(Gender.FEMALE);
		s2.setGender(Gender.MALE);
		Assert.assertEquals("s1=s2", s1.hashCode(), s2.hashCode());
		Assert.assertEquals("s1=s2", s1, s2);
		Address a1 = new Address();
		a1.setNumber("10");
		Address a2 = new Address();
		a1.setNumber("11");
		s1.setAddress(a1);
		s1.setAddress(a2);
		Assert.assertEquals("s1=s2", s1, s2);
		Assert.assertEquals("s1=s2", s1.hashCode(), s2.hashCode());
	}

	@Test
	public void shouldNotBeEqualWhenStudentIdOrBusinessKeyNotEqual() {
		Student s1 = new Student("Steve", "Hostettler", new Date(), new PhoneNumber("+33698075273"));
		Student s2 = new Student("Steve", "Gostettler", new Date(), new PhoneNumber("+33698075273"));
		s1.setStudentId(123);
		s2.setStudentId(123);
		Assert.assertFalse("s1=s2", s1.equals(s2));
		Assert.assertFalse("s1=s2", s1.hashCode() == s2.hashCode());

		s1 = new Student("Steve", "Hostettler", new Date(), new PhoneNumber("+33698075273"));
		s2 = new Student("Steve", "Hostettler", new Date(), new PhoneNumber("+33698075273"));
		s1.setStudentId(123);
		s2.setStudentId(124);
		Assert.assertFalse("s1=s2", s1.equals(s2));
		Assert.assertFalse("s1=s2", s1.hashCode() == s2.hashCode());
	}

	@Test
	public void shouldToStringHaveTheRightform() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("2013-12-4");

		Student s1 = new Student("Steve", "Hostettler", date, new PhoneNumber("+33698075273"));
		s1.setGender(Gender.MALE);
		Assert.assertTrue("s1.toString()",
				s1.toString().contains("{id:null, studentId:null, lastName:Steve, firstName:Hostettler, birthDate:Wed Dec 04 00:00:00"));
				;
	}

	@Test
	public void shouldCopyFromStudentBeEqualToSource() {
		Student s1 = new Student("Steve", "Hostettler", new Date(), new PhoneNumber("+33698075273"));
		Student s2 = new Student("John", "Doe", new Date(), new PhoneNumber("+33698075273"));
		s1.copyBusinessFieldsFrom(s2);
		Assert.assertEquals("s1=s2", s1, s2);
	}

}
