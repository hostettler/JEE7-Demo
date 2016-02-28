package ch.demo.helpers;

import org.junit.Assert;
import org.junit.Test;

import ch.demo.dom.PhoneNumber;
import ch.demo.dom.jpa.JPAPhoneNumberConverter;

public class JPAPhoneNumberConverterTest {

	@Test
	public void shouldComposeAsIdentity() {
		PhoneNumber p1 = new PhoneNumber(33, 123, 456);

		JPAPhoneNumberConverter converter = new JPAPhoneNumberConverter();
		PhoneNumber p2 = converter.convertToEntityAttribute(converter.convertToDatabaseColumn(p1));
		Assert.assertEquals("p1 = p2", p1, p2);	
	}
}
