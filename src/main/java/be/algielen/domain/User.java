package be.algielen.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "hello_users")
@XmlRootElement
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlAttribute(name = "id")
	private long id;

	@XmlAttribute(name = "name")
	private String name;

	public User(String name) {
		this.name = name;
	}

	public User() {
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}
}
