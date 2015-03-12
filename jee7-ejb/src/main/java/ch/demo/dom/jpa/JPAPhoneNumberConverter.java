/**
 * 
 */
package ch.demo.dom.jpa;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;

import ch.demo.dom.PhoneNumber;

/**
 * @author hostettler
 *
 */
public class JPAPhoneNumberConverter implements Converter {

    /** Serial-Id. */
    private static final long serialVersionUID = 9123897195642729056L;

    @Override
    public Object convertDataValueToObjectValue(final Object value, final Session session) {
        return PhoneNumber.getAsObject((String) value);
    }

    @Override
    public Object convertObjectValueToDataValue(final Object value, final Session session) {
        return PhoneNumber.getAsString((PhoneNumber) value);
    }

    @Override
    public void initialize(final DatabaseMapping mapping, final Session session) {

    }

    @Override
    public boolean isMutable() {
        return true;
    }

}
