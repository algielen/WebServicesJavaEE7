package be.algielen;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;


public enum SessionManager {
	INSTANCE;
	@PersistenceContext(unitName = "HelloPersistence")
	private EntityManagerFactory emf;


	private EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}

	public EntityManager createEntityManager() {
		return emf.createEntityManager();
	}

	public static void main(String[] args) {
		System.out.println("Hello, World");
	}
}