package be.algielen.messaging;

import be.algielen.domain.Document;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class DocumentWhiteboard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlAttribute
    protected long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date insertionTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date treatmentTime;
    @Enumerated(EnumType.STRING)
    @XmlAttribute(name = "whiteboard_state")
    private State state;

    @JoinColumn
    @OneToOne
    @XmlElement
    private Document object;

    public long getId() {
        return id;
    }

    public Date getInsertionTime() {
        return insertionTime;
    }

    public void setInsertionTime(Date insertionTime) {
        this.insertionTime = insertionTime;
    }

    public Date getTreatmentTime() {
        return treatmentTime;
    }

    public void setTreatmentTime(Date treatmentTime) {
        this.treatmentTime = treatmentTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Document getObject() {
        return object;
    }

    public void setObject(Document object) {
        this.object = object;
    }
}
