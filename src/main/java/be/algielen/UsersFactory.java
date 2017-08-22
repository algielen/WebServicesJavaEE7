package be.algielen;

public class UsersFactory {
	private static long id_sequence = 0;

	public static synchronized User create(String name) {
		return new User(name, id_sequence++);
	}
}
