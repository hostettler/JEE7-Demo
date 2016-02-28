/**
 * 
 */
package ch.demo.dom.jpa;

import javax.persistence.AttributeConverter;

import ch.demo.dom.PhoneNumber;

/**
 * @author hostettler
 *
 */
public class JPAPhoneNumberConverter implements AttributeConverter<PhoneNumber, String> {

	@Override
	public String convertToDatabaseColumn(PhoneNumber phoneNumber) {
        return PhoneNumber.getAsString((PhoneNumber) phoneNumber);	
	}
	
	@Override
	public PhoneNumber convertToEntityAttribute(String phoneNumber) {
		return PhoneNumber.getAsObject((String) phoneNumber);
	}

    
    
   
}
