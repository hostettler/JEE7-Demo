package ch.demo.business.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import ch.demo.dom.Discipline;
import ch.demo.dom.StudentClass;
import ch.demo.test.IntegrationTest;

@RunWith(Arquillian.class)
@Category(IntegrationTest.class)
public class StudentClassServiceImplTest {

	@Deployment
	public static JavaArchive create() {
		return ShrinkWrap.create(JavaArchive.class, "jee7-demo.jar").addPackages(true, "ch.demo")
				.addAsManifestResource("beans.xml", "beans.xml")
				.addAsManifestResource("test-persistence.xml", "persistence.xml");
	}

	@Inject
	StudentClassService service;

	@Test
	public void shouldGetAllReturnAlltheStudent() throws Exception {
		StudentClass sc = new StudentClass();
		sc.setClassId("GER101");
		sc.setDiscipline(Discipline.GERMAN);
		List<String> students=new ArrayList<String>();
		students.add("001");
		students.add("002");
		students.add("003");
		sc.setStudents(students);
		service.createClass(sc);
		
		Double avgGer101 = service.getAvgForStudentsClass("GER101");
		Assert.assertEquals(new Double(10.0d), avgGer101);
	}
	
}
