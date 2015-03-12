package ch.demo.dom;

import java.io.Serializable;
import java.util.Formatter;
import java.util.StringTokenizer;

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
	public PhoneNumber(final int countryCode, final int areaCode,
			final long number) {
		this.setCountryCode(countryCode);
		this.setAreaCode(areaCode);
		this.setNumber(number);
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
	 *            to convert
	 * @return an object that corresponds to the string representation.
	 */
	public static PhoneNumber getAsObject(final String value) {

		boolean conversionError = false;

		int hyphenCount = 0;
		StringTokenizer hyphenTokenizer = new StringTokenizer(value, "-");

		Integer countryCode = null;
		Integer areaCode = null;
		Long number = null;

		while (hyphenTokenizer.hasMoreTokens()) {
			String token = hyphenTokenizer.nextToken();
			try {
				if (hyphenCount == 0) {
					StringTokenizer plusTokenizer = new StringTokenizer(token,
							"+");
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
		Formatter f = new Formatter(new StringBuilder());
		String r = f.format("+%02d-%04d-%04d", phoneNumber.getCountryCode(),
				phoneNumber.getAreaCode(), phoneNumber.getNumber()).toString();
		f.close();
		return r;
	}

}