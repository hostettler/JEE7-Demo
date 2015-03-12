package ch.demo.dom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;

import ch.demo.dom.jpa.JPAPhoneNumberConverter;

/**
 * Models a student.
 * 
 * @author hostettler
 */
@Entity
@NamedQuery(name = "findAllStudentsByFirstName", query = "SELECT s FROM Student s WHERE s.lastName = :lastname")
@Table(name = "STUDENTS")
@SecondaryTable(name = "PICTURES", pkJoinColumns = 
@PrimaryKeyJoinColumn(name = "STUDENT_ID", referencedColumnName = "ID"))
public class Student implements Serializable {

    /** The serial-id. */
    private static final long serialVersionUID = -6146935825517747043L;

    /** The unique id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
		return id;
	}

	/** The student last name. */
    @Column(name = "LAST_NAME", length = 35)
    @NotNull
    @Size(min=2, max=35)
    private String lastName;

    /** The student first name. */
    @Column(name = "FIRST_NAME", nullable = false, length = 35)
    @NotNull
    @Size(min=2, max=35)    
    private String firstName;

    /** The student birth date. */
    @Column(name = "BIRTH_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date birthDate;

    /** The student phone number. */
    @Column(name = "PHONE_NUMBER")
    @Converter(name = "phoneConverter", converterClass = JPAPhoneNumberConverter.class)
    @Convert("phoneConverter")
    private PhoneNumber phoneNumber;

    /** The student's gender. */
    private transient Gender mGender;

    /** The address of the student. */
    @Embedded
    private Address address;

    /** The set of grades of the student. */

    @OrderBy("discipline DSC")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
    private List<Grade> grades;


    /** A picture of the student. */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(table = "PICTURES", name = "PICTURE", nullable = true)
    private byte[] picture;

    /** The Student's badge. */
    @OneToOne(mappedBy = "student")
    private Badge badge;

    /**
     * Empty (default) constructor.
     */
    public Student() {
        this.grades = new ArrayList<Grade>();
 

    }

    /**
     * @return the phoneNumber
     */
    public final PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public final void setPhoneNumber(final PhoneNumber pPhoneNumber) {
        this.phoneNumber = pPhoneNumber;
    }

    /**
     * 
     * @param lastName
     *            The student last name.
     * @param firstName
     *            The student first name.
     * @param birthDate
     *            The student birth date.
     */
    public Student(final String lastName, final String firstName,
            final Date birthDate) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    /**
     * @return an unique identifier
     */
    public String getKey() {
        if (this.getId() != null) {
            return String.valueOf(this.getId());
        } else {
            return String.valueOf(this.hashCode());
        }
    }

    /**
     * @return the lastName
     */
    public final String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public final void setLastName(final String pLastName) {
        this.lastName = pLastName;
    }

    /**
     * @return the firstName
     */
    public final String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public final void setFirstName(final String pFirstName) {
        this.firstName = pFirstName;
    }

    /**
     * @return the birthDate
     */
    public final Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate
     *            the birthDate to set
     */
    public final void setBirthDate(final Date pBirthDate) {
        birthDate = pBirthDate;
    }

    /**
     * @return the grade
     */
    public final Float getAvgGrade() {
        Float avg = 0.0f;
        for (Grade grade : this.grades) {
            if (grade.getGrade() != null) {
                Float f = grade.getGrade().floatValue();
                avg += f;
            }
        }
        return avg / this.grades.size();
    }

    /**
     * @return the grades
     */
    public final List<Grade> getGrades() {
        return this.grades;
    }

    /**
     * @return the actual list of discipline for this student.
     */
    public final Discipline[] getDisciplines() {
        return Discipline.values();
    }

    /**
     * @return the gender
     */
    public final Gender getGender() {
        return mGender;
    }

    /**
     * @param gender
     *            the gender to set
     */
    public final void setGender(final Gender gender) {
        mGender = gender;
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
        if (obj instanceof Student) {
            if (this.lastName.equals(((Student) obj).lastName)
                    && this.firstName.equals(((Student) obj).firstName)
                    && this.birthDate.equals(((Student) obj).birthDate)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Student [id=" + getId() + ", lastName=" + lastName
                + ", firstName=" + firstName + ", birthDate=" + birthDate
                + ", phoneNumber=" + phoneNumber + ", grades=" + grades
                + "]";
    }

    /**
     * @return the address
     */
    public final Address getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public final void setAddress(final Address pAddress) {
        this.address = pAddress;
    }

    /**
     * @return the picture
     */
    public final byte[] getPicture() {
        return picture;
    }

    /**
     * @param picture
     *            the picture to set
     */
    public void setPicture(final byte[] pPicture) {
        this.picture = pPicture;
    }

    /**
     * @return the badge
     */
    public Badge getBadge() {
        return badge;
    }

    /**
     * @param badge
     *            the badge to set
     */
    public void setBadge(final Badge pBadge) {
        this.badge = pBadge;
    }

	public void setId(Long id) {
		this.id = id;
	}
}
