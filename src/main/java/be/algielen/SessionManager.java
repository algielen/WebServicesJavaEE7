package be.algielen;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public enum SessionManager {
	INSTANCE;
	private final EntityManagerFactory emf;

	{
		emf = Persistence.createEntityManagerFactory("HelloPersistence");
	}


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