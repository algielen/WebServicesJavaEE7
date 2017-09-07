package be.algielen.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

//TODO size and hash
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    @Column
    private String filename;
    @Column
    private String extension;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] content;

    public Document() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }
}
