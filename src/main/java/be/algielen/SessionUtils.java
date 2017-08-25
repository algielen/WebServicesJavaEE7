package be.algielen;

import org.hibernate.Transaction;

public class SessionUtils {
	private SessionUtils() {

	}

	public static void rollback(Transaction transaction) {
		if (transaction != null) {
			transaction.rollback();
			System.out.println("Transaction was rolled back.");
		}
	}
}
