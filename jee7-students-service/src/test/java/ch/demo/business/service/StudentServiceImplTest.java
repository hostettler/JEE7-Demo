package ch.demo.business.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import ch.demo.dom.Address;
import ch.demo.dom.Gender;
import ch.demo.dom.PhoneNumber;
import ch.demo.dom.Student;
import ch.demo.test.IntegrationTest;

	
@RunWith(Arquillian.class)
@Category(IntegrationTest.class)
public class StudentServiceImplTest {

	@Deployment
	public static JavaArchive create() {
		return ShrinkWrap.create(JavaArchive.class, "jee63-demo.jar").addPackages(true, "ch.demo")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("test-persistence.xml", "persistence.xml");
	}

	@Inject
	StudentService service;

	@Test
	public void shouldGetAllReturnAlltheStudent() throws Exception {
		Integer nbStudents = service.getNbStudent();
		Integer nbStudentsfromGetAll = service.getAll().size();
		Assert.assertSame(nbStudents, nbStudentsfromGetAll);
	}

	@Test
	public void shouldAddReturnAllWithTheNewStudent() {
		Integer nbStudentsBeforeTest = service.getNbStudent();
		service.add(new Student("Doe", "Jane", new Date(), new PhoneNumber("+33698075273")));
		Integer nbStudentsAfterTest = service.getAll().size();
		Assert.assertSame(nbStudentsBeforeTest + 1, nbStudentsAfterTest);
		Assert.assertEquals(service.getAll().get(nbStudentsAfterTest - 1).getLastName(), "Doe");
	}
	
	@Test
	public void shouldGetStudentByIdReturnTheStudentWithGivenTechnicalId() {
		Integer nbStudentsBeforeTest = service.getNbStudent();
		//As we start with 0 "nbStudentsBeforeTest" should refer to the last id.
		service.add(new Student("Daudet", "Alphonse", new Date(), new PhoneNumber("+33698075273")));
		Student lastStudent = service.getStudentById(new Long(nbStudentsBeforeTest + 1));
		Assert.assertEquals(lastStudent.getLastName(), "Daudet");
	}
	
	@Test
	public void shouldGetStudentByKeyReturnTheStudentWithGivenKey() {
		Integer nbStudentsBeforeTest = service.getNbStudent();
		//As we start with 0 "nbStudentsBeforeTest" should refer to the last id.
		service.add(new Student("Hugo", "Victor", new Date(), new PhoneNumber("+33698075273")));
		Student lastStudent = service.getStudentByKey(new Long(nbStudentsBeforeTest).toString());
		Assert.assertEquals(lastStudent.getLastName(), "Hugo");
	}

	@Test
	public void shouldNbStudentReturnTheNumberOfStudentInStore() {
		Integer nbStudentsBeforeTest = service.getNbStudent();
		service.add(new Student("Doe", "John", new Date(), new PhoneNumber("+33698075273")));
		Integer nbStudentsAfterTest = service.getNbStudent();
		Assert.assertSame(nbStudentsBeforeTest + 1, nbStudentsAfterTest);
	}
	
	@Test
	public void shouldAddATheNewStudent() {
		Integer nbStudentsBeforeTest = service.getNbStudent();
		Student s = new Student("Doe", "Jane", new Date(), new PhoneNumber("+33698075273"));
		s.setGender(Gender.MALE);
		Address a = new Address();
		a.setCity("Mulhouse");
		a.setNumber("102");
		a.setPostalCode("68100");
		a.setStreet("Large");		
		s.setAddress(a);
		service.add(s);
		List<Student> students = service.getAll();
		
		Integer nbStudentsAfterTest = students.size();
		Assert.assertSame(nbStudentsBeforeTest + 1, nbStudentsAfterTest);
		Student lastStudent = students.get(nbStudentsAfterTest - 1);
		//Should be null because gender is transient
		Assert.assertNull(lastStudent.getGender());
		Assert.assertEquals(lastStudent.getLastName(), "Doe");
		Assert.assertEquals(lastStudent.getFirstName(), "Jane");
		Assert.assertEquals(lastStudent.getAddress().getStreet(), "Large");
	}


	@Test
	public void shouldReturnTheStudentWithGivenLastName() {
		service.add(new Student("Dumas", "Alexandre", new Date(), new PhoneNumber("+33698075273")));
		List<Student> dumas = service.getStudentByLastName("Dumas");
		Assert.assertSame(1, dumas.size());
		Assert.assertEquals("Dumas", dumas.get(0).getLastName());
		service.add(new Student("Dumas", "Marcel", new Date(), new PhoneNumber("+33698075273")));
		dumas = service.getStudentByLastName("Dumas");
		Assert.assertSame(2, dumas.size());
		Assert.assertEquals("Dumas", dumas.get(0).getLastName());
		Assert.assertEquals("Dumas", dumas.get(1).getLastName());
	}
	
	
	@Test
	public void shouldUpdateAStudent() {
		int dumasBeforeTest = service.getStudentByLastName("Dumas").size();
		service.add(new Student("Dumas", "Alexandre", new Date(), new PhoneNumber("+33698075273")));
		List<Student> dumas = service.getStudentByLastName("Dumas");
		Assert.assertSame(dumasBeforeTest + 1, dumas.size());
		Assert.assertEquals("Dumas", dumas.get(0).getLastName());
		Student s = dumas.get(0);
		s.setLastName("Astier");
		service.update(s.getStudentId().toString(), s);
		dumas = service.getStudentByLastName("Dumas");
		List<Student> astier = service.getStudentByLastName("Astier");
		Assert.assertSame(dumasBeforeTest, dumas.size());
		Assert.assertSame(1, astier.size());
	}
	
}
