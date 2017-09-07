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

@Entity
public class DocumentWhiteboard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date insertionTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date treatmentTime;
    @Enumerated(EnumType.STRING)
    private State state;
    @JoinColumn
    @OneToOne
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

    public enum State {WAITING, STARTING, PROCESSING, FAILURE, DONE}
}
