/**
 * 
 */
package ch.demo.dom;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author hostettler
 * 
 */
@Embeddable
public class Address implements Serializable {

	/** The serial id. */
	private static final long serialVersionUID = 1197893493017932784L;

	/** house number. */
	@Column(name = "NUMBER")
	private String number;

	/** the name of the street. */
	@Column(name = "STREET")
	private String street;

	/** the city name. */
	@Column(name = "CITY")
	private String city;

	/** the postal code. */
	@Column(name = "POSTAL_CODE")
	private String postalCode;

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(final String pNumber) {
		this.number = pNumber;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return this.street;
	}

	/**
	 * @param street
	 *            the street to set
	 */
	public void setStreet(final String pStreet) {
		this.street = pStreet;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(final String pCity) {
		this.city = pCity;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(final String pPostalCode) {
		this.postalCode = pPostalCode;
	}

	@Override
	public String toString() {
		return "Address [number=" + getNumber() + ", street=" + getStreet() + ", postalCode=" + getPostalCode()
				+ ", city=" + getCity() + "]";
	}
}
