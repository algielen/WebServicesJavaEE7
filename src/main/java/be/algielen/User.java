package be.algielen;

import javax.xml.bind.annotation.XmlAttribute;

public class User {
	@XmlAttribute(name = "name")
	private String name;
	@XmlAttribute(name = "id")
	private long id;

	User(String name, long id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}
}
