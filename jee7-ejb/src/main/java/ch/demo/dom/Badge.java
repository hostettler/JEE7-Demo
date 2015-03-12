/**
 * 
 */
package ch.demo.dom;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Models a security badge.
 * 
 * @author hostettler
 */
@Entity
@Table(name = "BADGES")
@XmlRootElement(name = "badge", namespace = "http://ch.demo.app")
@XmlAccessorType(XmlAccessType.FIELD)
public class Badge implements Serializable {

    /** The serial-id. */
    private static final long serialVersionUID = 3064609886266854069L;

    /** The unique id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    /** The student's security level. */
    @Column(name = "SECURITY_LEVEL")
    @XmlAttribute
    private Long securityLevel;

    /** The student that owns this badge. */
    @OneToOne
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
    @XmlTransient
    private Student student;

    /**
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student
     *            the student to set
     */
    public void setStudent(final Student pStudent) {
        this.student = pStudent;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the securityLevel
     */
    public Long getSecurityLevel() {
        return securityLevel;
    }

    /**
     * @param securityLevel
     *            the securityLevel to set
     */
    public void setSecurityLevel(final Long pSecurityLevel) {
        this.securityLevel = pSecurityLevel;
    }

}
