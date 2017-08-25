package be.algielen;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public enum SessionManager {
	INSTANCE;
	private final SessionFactory sessionFactory;

	{
		try {
			Configuration configuration = new Configuration();
			configuration.configure();

			sessionFactory = configuration.buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}