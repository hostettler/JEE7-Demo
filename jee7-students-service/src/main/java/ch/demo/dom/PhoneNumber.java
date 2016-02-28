package ch.demo.dom;

import java.io.Serializable;
import java.util.Formatter;
import java.util.StringTokenizer;

import javax.validation.constraints.Size;

/**
 * Represents a phone number.
 * 
 * @author hostettler
 * 
 */
public class PhoneNumber implements Serializable {

	/** The serial ID. */
	private static final long serialVersionUID = -238688791921667729L;
	/** Phone country code. For instance 41 for Switzerland. */
	private int countryCode;
	/** Area code : 22 for Geneva. */
	private int areaCode;
	/** The number itself. */
	private long number;

	/**
	 * The constructor.
	 * 
	 * @param countryCode
	 *            Phone country code. For instance 41 for Switzerland.
	 * @param areaCode
	 *            Area code : 22 for Geneva.
	 * @param number
	 *            The number itself.
	 */
	public PhoneNumber(final int countryCode, final int areaCode, final long number) {
		this.setCountryCode(countryCode);
		this.setAreaCode(areaCode);
		this.setNumber(number);
	}

	public PhoneNumber(@Size(min = 11, max = 11) String pPhoneNumber) {
		PhoneNumber pn = getAsObject(pPhoneNumber);
		this.setAreaCode(pn.getAreaCode());
		this.setCountryCode(pn.getCountryCode());
		this.setNumber(pn.getNumber());
	}

	/**
	 * @return the countryCode
	 */
	public final int getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            the countryCode to set
	 */
	public final void setCountryCode(final int pCountryCode) {
		this.countryCode = pCountryCode;
	}

	/**
	 * @return the areaCode
	 */
	public final int getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode
	 *            the areaCode to set
	 */
	public final void setAreaCode(final int pAreaCode) {
		this.areaCode = pAreaCode;
	}

	/**
	 * @return the number
	 */
	public final long getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public final void setNumber(final long pNumber) {
		number = pNumber;
	}

	/**
	 * @param value
	 *            to convert from the form +41-0783-302020
	 * @return an object that corresponds to the string representation.
	 */
	public static PhoneNumber getAsObject(final String value) {

		boolean conversionError = false;

		int hyphenCount = 0;
		StringTokenizer hyphenTokenizer = new StringTokenizer(value, "-");

		Integer countryCode = null;
		Integer areaCode = null;
		Long number = null;

		if (hyphenTokenizer.countTokens() == 1) {
			StringTokenizer plusTokenizer = new StringTokenizer(value, "+");
			String phone = value;
			if (plusTokenizer.hasMoreTokens()) {
				phone = plusTokenizer.nextToken();
			}
			countryCode = Integer.parseInt(phone.substring(0, 2));
			areaCode = Integer.parseInt(phone.substring(2, 6));
			number = Long.parseLong(phone.substring(6, 11));
			hyphenCount = 3;
		} else {

			while (hyphenTokenizer.hasMoreTokens()) {
				String token = hyphenTokenizer.nextToken();
				try {
					if (hyphenCount == 0) {
						StringTokenizer plusTokenizer = new StringTokenizer(token, "+");
						if (plusTokenizer.hasMoreTokens()) {
							token = plusTokenizer.nextToken();
						}
						countryCode = Integer.parseInt(token);
					}

					if (hyphenCount == 1) {
						areaCode = Integer.parseInt(token);
					}

					if (hyphenCount == 2) {
						number = Long.parseLong(token);
					}
					hyphenCount++;
				} catch (Exception exception) {
					conversionError = true;
				}
			}
		}

		PhoneNumber phoneNumber = null;

		if (conversionError || (hyphenCount != 3)) {
			throw new IllegalArgumentException();
		} else {
			phoneNumber = new PhoneNumber(countryCode, areaCode, number);
		}

		return phoneNumber;
	}

	/**
	 * @param value
	 *            to convert
	 * @return a string representation for the PhoneNumber object.
	 */
	public static String getAsString(final PhoneNumber phoneNumber) {
		if (phoneNumber == null) {
			return null;
		}
		Formatter f = new Formatter(new StringBuilder());
		String r = f.format("+%02d-%04d-%04d", phoneNumber.getCountryCode(), phoneNumber.getAreaCode(),
				phoneNumber.getNumber()).toString();
		f.close();
		return r;
	}

	@Override
	public String toString() {
		return getAsString(this);
	}

	@Override
	public int hashCode() {
		int hash = this.getAreaCode();
		hash ^= this.getCountryCode();
		hash ^= this.getNumber();
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof PhoneNumber) {
			PhoneNumber that = (PhoneNumber) obj;
			if (this.getAreaCode() == that.getAreaCode() && this.getNumber() == that.getNumber()
					&& this.getCountryCode() == that.getCountryCode()) {
				return true;
			}
		}
		return false;
	}
}