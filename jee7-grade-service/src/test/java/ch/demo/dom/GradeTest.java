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
public class GradeTest {

	@Test
	public void shouldTestHashCode() {
		Date date = new Date();
		Grade g1 = new Grade("0001", date, Discipline.BIOLOGY, 40);
		Grade g2 = new Grade("0001", date, Discipline.BIOLOGY, 40);
		Assert.assertEquals("g1.hashcode=g2.hashcode", g1.hashCode(), g2.hashCode());
	}
	
	@Test
	public void shouldTestEq() {
		Date date = new Date();
		Grade g1 = new Grade("0001", date, Discipline.BIOLOGY, 40);
		Grade g2 = new Grade("0001", date, Discipline.BIOLOGY, 40);
		Assert.assertEquals("g1=g2", g1, g2);
	}

	@Test
	public void shouldTestNotEq() throws InterruptedException {
		Grade g1 = new Grade("0001", new Date(), Discipline.BIOLOGY, 40);
		Assert.assertFalse(g1.equals(null));
		Thread.sleep(3);
		Grade g2 = new Grade("0001", new Date(), Discipline.BIOLOGY, 40);
		Assert.assertFalse("g1!=g2", g1.equals(g2));

	}

	@Test
	public void shouldToString() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse("2013-12-4");
		
		Grade g1 = new Grade("0001", date, Discipline.BIOLOGY, 40);
		Assert.assertTrue("g1.toString()" ,  g1.toString().contains("Grade [id=0, studentId=0001, date=Wed Dec 04 00:00:00"));
	}
}
