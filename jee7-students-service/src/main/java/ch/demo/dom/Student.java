package ch.demo.dom;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ch.demo.dom.jpa.JPAPhoneNumberConverter;

/**
 * Models a student.
 * 
 * @author hostettler
 */
@Entity
@NamedQuery(name = "findAllStudentsByFirstName", query = "SELECT s FROM Student s WHERE s.lastName = :lastname")
@Table(name = "STUDENTS")
public class Student implements Serializable {

	/** The serial-id. */
	private static final long serialVersionUID = -6146935825517747043L;

	/** The unique id in a technical sense. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	/** The student unique id in a business sense. */
	@NotNull
	@Column(name = "STUDENT_ID")
	private Integer studentId;

	/** The student last name. */
	@NotNull
	@Size(min = 2, max = 35)
	@Column(name = "LAST_NAME")
	private String lastName;

	/** The student first name. */
	@NotNull
	@Size(min = 2, max = 35)
	@Column(name = "FIRST_NAME")
	private String firstName;

	/** The student birth date. */
	@Temporal(TemporalType.DATE)
	@NotNull
	@Column(name = "BIRTH_DATE")
	private Date birthDate;

	/** The student phone number. */
	@Convert(converter = JPAPhoneNumberConverter.class)
	@Column(name = "PHONE_NUMBER")
	private PhoneNumber phoneNumber;

	/** The student's gender. */
	private transient Gender gender;

	/** The address of the student. */
	@Embedded
	private Address address;

	/** The set of grades of the student. */

	// /** A picture of the student. */
	// @Lob
	// @Basic(fetch = FetchType.LAZY)
	// @Column(table = "PICTURES", name = "PICTURE", nullable = true)
	transient private byte[] picture;

	public Student() {
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
	public Student(final String lastName, final String firstName, final Date birthDate, PhoneNumber phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.phoneNumber = phone;
	}

	@Override
	public int hashCode() {
		return Objects.hash(studentId, firstName, lastName, birthDate);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Student other = (Student) obj;
		return Objects.equals(this.studentId, other.studentId) && Objects.equals(this.lastName, other.lastName)
				&& Objects.equals(this.firstName, other.firstName) && Objects.equals(this.birthDate, other.birthDate);
	}

	@Override
	public String toString() { 
		return "{id:" +  id + ", studentId:" + studentId + ", lastName:" + lastName + ", firstName:"
				+ firstName + ", birthDate:" + birthDate + ", phoneNumber:" + phoneNumber + ", gender:" + gender
				+ ", address:" + address + "}";
	}

	public Long getId() {
		return id;
	}

	/** -----------------Getters and setters. **/
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
	 * @return the gender
	 */
	public final Gender getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public final void setGender(final Gender pGender) {
		gender = pGender;
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

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the studentId
	 */
	public Integer getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId
	 *            the studentId to set
	 */
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public void copyBusinessFieldsFrom(Student student) {
		this.setAddress(student.getAddress());
		this.setBirthDate(student.getBirthDate());
		this.setLastName(student.getLastName());
		this.setFirstName(student.getFirstName());
		this.setPhoneNumber(student.getPhoneNumber());
		this.setGender(student.getGender());
	}
}
