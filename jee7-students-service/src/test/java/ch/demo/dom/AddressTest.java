package ch.demo.dom;

import org.junit.Assert;
import org.junit.Test;

public class AddressTest {

	@Test
	public void shouldToStringHaveTheRightform() throws Exception {
		Address a = new Address();
		a.setCity("Mulhouse");
		a.setNumber("10");
		a.setPostalCode("68100");
		a.setStreet("Large");

		Assert.assertEquals("a.toString()", "Address [number=10, street=Large, postalCode=68100, city=Mulhouse]",
				a.toString());
	}
}
