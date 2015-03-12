/**
 * 
 */
package ch.demo.dom;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hostettler
 * 
 */
@Embeddable
@XmlRootElement(name = "address", namespace = "http://ch.demo.app")
@XmlAccessorType(XmlAccessType.FIELD)
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
    protected String getNumber() {
        return number;
    }

    /**
     * @param number
     *            the number to set
     */
    protected void setNumber(final String pNumber) {
        this.number = pNumber;
    }

    /**
     * @return the street
     */
    protected String getStreet() {
        return this.street;
    }

    /**
     * @param street
     *            the street to set
     */
    protected void setStreet(final String pStreet) {
        this.street = pStreet;
    }

    /**
     * @return the city
     */
    protected String getCity() {
        return this.city;
    }

    /**
     * @param city
     *            the city to set
     */
    protected void setCity(final String pCity) {
        this.city = pCity;
    }

    /**
     * @return the postalCode
     */
    protected String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode
     *            the postalCode to set
     */
    protected void setPostalCode(final String pPostalCode) {
        this.postalCode = pPostalCode;
    }

}
