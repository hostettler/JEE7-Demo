package ch.demo.business.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.demo.dom.Discipline;
import ch.demo.dom.Grade;
import ch.demo.dom.Student;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceImplTest {

	@Mock
	EntityManager em;
	
	@Mock
	CriteriaBuilder qb;
	@Mock
	CriteriaQuery<Student> c;
	@Mock	
	TypedQuery<Student> query;
	
	@InjectMocks
	StudentServiceImpl service;
	
	@Test
	public void shouldDistributionBeUniform() {
		List<Student> students = new ArrayList<Student>();
		Student s;
		
		s= new Student();
		s.getGrades().add(new Grade(Discipline.BIOLOGY, 10));
		students.add(s);
		
		s= new Student();
		s.getGrades().add(new Grade(Discipline.BIOLOGY, 40));
		students.add(s);
		
		s= new Student();
		s.getGrades().add(new Grade(Discipline.BIOLOGY, 70));
		students.add(s);
		
		when(em.getCriteriaBuilder()).thenReturn(qb);
		when(qb.createQuery(Student.class)).thenReturn(c);
		when(em.createQuery(c)).thenReturn(query);
		when(query.getResultList()).thenReturn(students);
		
		Integer[] expectedDistribution = new Integer[] {1, 1, 1};
		Assert.assertTrue(Arrays.equals(expectedDistribution, service.getDistribution(3)));
		
	}
	
}
