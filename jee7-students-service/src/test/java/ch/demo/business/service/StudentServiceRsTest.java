package ch.demo.business.service;

import java.io.File;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import ch.demo.dom.PhoneNumber;
import ch.demo.dom.Student;
import ch.demo.test.IntegrationTest;
import junit.framework.Assert;

@RunWith(Arquillian.class)
@Category(IntegrationTest.class)
public class StudentServiceRsTest {

	@Deployment
	public static WebArchive create() {
		return ShrinkWrap.create(WebArchive.class, "jee7-demo-integration-test.war").addPackages(true, "ch.demo")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.setWebXML(new File("src/main/webapp/WEB-INF", "/web.xml"))
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/ejb-jar.xml"), "ejb-jar.xml")
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/jboss-web.xml"), "jboss-web.xml")
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
	}

	@Inject
	private StudentServiceRs sut;

	@Test
	public void shouldAllReturnAListOfStudents() throws URISyntaxException, ParseException {
		// We mostly test the return codes, syntax and encoding.
		Response result = sut.getAll();
		Assert.assertEquals("[]",result.getEntity().toString());
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdate = formatter.parse("2016-01-01");
		
		Student student = new Student("Doe", "Jane", birthdate, new PhoneNumber("+33698075273"));
		sut.add(student);
		result = sut.getAll();
		Assert.assertEquals("[{id:1, studentId:0, lastName:Doe, firstName:Jane, birthDate:2016-01-01, phoneNumber:+33-6980-75273, gender:null, address:null}]",result.getEntity().toString());
	}

	@Test
	public void shouldNbStudentReturnTheCurrentOfStudents() {
		// We mostly test the return codes, syntax and encoding.
		String result = sut.getNbStudent();
		Assert.assertEquals("{\"nbStudents\":\"1\"}",result);
	}

	@Test
	public void shouldGetUnkownIdReturn404() {
		// We mostly test the return codes, syntax and encoding.
		Response result = sut.getStudentsByStudentId("2");		
		Assert.assertEquals(404, result.getStatus());
	}

	@Test
	public void shouldCorrectUpdateReturn200() throws URISyntaxException {
		// We mostly test the return codes, syntax and encoding.
		Student student = new Student("Doe", "Jane", new Date(), new PhoneNumber("+33698075273"));
		sut.add(student);
		Response result = sut.getStudentsByStudentId("0");
		Assert.assertEquals(200, result.getStatus());
		Student s = (Student) result.getEntity();
		s.setFirstName("William");
		sut.update(s.getStudentId().toString(), s);
		result = sut.getStudentsByStudentId("0");
		Assert.assertEquals(200, result.getStatus());
		s = (Student) result.getEntity();
		Assert.assertEquals(s.getFirstName(), "William");
	}
	
	@Test
	public void shouldkownIdReturn200() throws URISyntaxException {
		// We mostly test the return codes, syntax and encoding.
		Student student = new Student("Doe", "Jane", new Date(), new PhoneNumber("+33698075273"));
		sut.add(student);
		Response result = sut.getStudentsByStudentId("0");
		Assert.assertEquals(200, result.getStatus());
	}

	@Test
	public void shouldAddReturnTheNewURI() throws URISyntaxException {
		// We mostly test the return codes, syntax and encoding.
		Student student = new Student("Doe", "Jow", new Date(), new PhoneNumber("+33698075273"));
		Response result = sut.add(student);
		Assert.assertEquals(201, result.getStatus());
	}
}
