package ch.demo.dom;

import org.junit.Assert;
import org.junit.Test;

public class PhoneNumberTest {

	@Test
	public void shouldToStringHaveTheRightform() throws Exception {
		PhoneNumber p1 = new PhoneNumber(33, 123, 456);
		Assert.assertEquals("a.toString()", "+33-0123-0456",
				p1.toString());
		PhoneNumber p4 = new PhoneNumber("+33-0123-0456");
		Assert.assertEquals("a.toString()", "+33-0123-0456",
				p4.toString());	
	}

	@Test
	public void shouldIdenticalPhoneNumberBeEquals() throws Exception {
		PhoneNumber p1 = new PhoneNumber(33, 123, 456);
		PhoneNumber p4 = new PhoneNumber("+33-0123-0456");
		Assert.assertEquals("p1 = p4", p1, p4);	
	}
	
	@Test
	public void shouldIdenticalPhoneNumberHaveSameHashcode() throws Exception {
		PhoneNumber p1 = new PhoneNumber(33, 123, 456);
		PhoneNumber p4 = new PhoneNumber("+33-0123-0456");
		Assert.assertEquals("p1.hashCode() = p4.hashCode()", p1.hashCode(), p4.hashCode());	
	}

}
