/**
 * 
 */
package ch.demo.dom;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author hostettler
 *
 */
public class StudentClassTest {

	@Test
	public void shouldTestEq() {
		StudentClass s1 = new StudentClass("GER101", Discipline.GERMAN, "001", "002");
		StudentClass s2 = new StudentClass("GER101", Discipline.GERMAN, "001", "002");

		Assert.assertEquals("s1=s2", s1, s2);
	}

	@Test
	public void shouldTestNotEq() {
		StudentClass s1 = new StudentClass("GER101", Discipline.GERMAN, "001", "002");
		StudentClass s2 = new StudentClass("GER102", Discipline.GERMAN, "001", "002");
		Assert.assertFalse("s1=s2", s1.equals(s2));
		s1 = new StudentClass("GER101", Discipline.GERMAN, "001", "002");
		s2 = new StudentClass("GER101", Discipline.FRENCH, "001", "002");
		Assert.assertFalse("s1=s2", s1.equals(s2));
		s1 = new StudentClass("GER101", Discipline.GERMAN, "001", "002");
		s2 = new StudentClass("GER101", Discipline.GERMAN, "001", "003");
		Assert.assertFalse("s1=s2", s1.equals(s2));	
	}

	@Test
	public void shouldToString() throws Exception {
		StudentClass s1 = new StudentClass("GER101", Discipline.GERMAN, "001", "002");
		Assert.assertEquals("s1.toString()" , "{id:0, classId:GER101, discipline:German, students:[001, 002]}", s1.toString());
	}
}
