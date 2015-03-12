package ch.demo.web;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://ch.demo.app")
public class StudentDTO implements Serializable {
	
	private static final long serialVersionUID = -7176843078430927028L;

	/** The unique id. */
    private Long id;

	/** The student last name. */
    @NotNull
    @Size(min=2, max=35)
    private String lastName;

    /** The student first name. */
    @NotNull
    @Size(min=2, max=35)
    private String firstName;

    /** The student birth date. */
    @NotNull
    @XmlSchemaType(name="date")
    private Date birthDate;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

    @Override
    public int hashCode() {
        if (this.lastName == null) {
            return -1;
        } else {
            return this.lastName.hashCode() ^ this.firstName.hashCode()
                    ^ this.birthDate.hashCode();
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof StudentDTO) {
            if (this.lastName.equals(((StudentDTO) obj).lastName)
                    && this.firstName.equals(((StudentDTO) obj).firstName)
                    && this.birthDate.equals(((StudentDTO) obj).birthDate)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "StudentDTO [id=" + id + ", lastName=" + lastName
                + ", firstName=" + firstName + ", birthDate=" + birthDate
                + "]";
    }
}
