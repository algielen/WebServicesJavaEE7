package be.algielen.dal;

import be.algielen.messaging.DocumentWhiteboard;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@RequestScoped
@Transactional(TxType.REQUIRED)
public class DocumentWhiteboardDao implements Serializable {

    private static final Class<DocumentWhiteboard> persistentClass = DocumentWhiteboard.class;
    @PersistenceContext(unitName = "HelloPersistence")
    private EntityManager entityManager;

    public DocumentWhiteboardDao() {
    }


    public void persist(DocumentWhiteboard whiteboard) {
        entityManager.persist(whiteboard);
    }

    public DocumentWhiteboard load(long id) {
        return entityManager.find(persistentClass, id);
    }

    public List<DocumentWhiteboard> findAll() {
        TypedQuery<DocumentWhiteboard> query = entityManager
            .createQuery("FROM DocumentWhiteboard", persistentClass);
        return query.getResultList();
    }
}