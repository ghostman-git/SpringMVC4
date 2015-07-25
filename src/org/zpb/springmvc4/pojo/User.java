package org.zpb.springmvc4.pojo;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6201872539745981658L;

	private Integer id;
	private String username;
	private String password;
	private String email;
	private Integer age;

	private Adress adress;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password, String email, Integer age) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.age = age;
	}

	public User(Integer id, String username, String password, String email,
			Integer age) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", email=" + email + ", age=" + age + "]";
	}

}
