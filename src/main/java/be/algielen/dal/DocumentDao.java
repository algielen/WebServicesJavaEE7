package be.algielen.dal;

import be.algielen.domain.Document;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RequestScoped
@Transactional(value = Transactional.TxType.REQUIRED)
public class DocumentDao implements Serializable {

    private static final Class<Document> persistentClass = Document.class;
    @PersistenceContext(unitName = "HelloPersistence")
    private EntityManager entityManager;

    public DocumentDao() {
    }

    public void persist(Document document) {
        entityManager.persist(document);
    }

    public Document load(long id) {
        return entityManager.find(persistentClass, id);
    }
}
