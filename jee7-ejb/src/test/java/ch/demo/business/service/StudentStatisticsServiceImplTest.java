package ch.demo.business.service;

import javax.annotation.ManagedBean;
import javax.ejb.NoSuchEJBException;
import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

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
public class StudentStatisticsServiceImplTest {

	@Inject
	StudentStatisticsService ejb;

	@Test
	public void shouldAddANewTicToStatistics() throws Exception {
		EJB31Runner.login("admin", "user1");

		ejb.hit();
		ejb.hit();
		ejb.hit();

		Assert.assertEquals("Expected 3 hits", new Long(3), ejb.getStatistics());
	}

	@Test(expected = NoSuchEJBException.class)
	public void shoulRemoveStateful() throws Exception {
		EJB31Runner.login("admin", "user1");
		ejb.clean();
		ejb.hit();
	}

}
