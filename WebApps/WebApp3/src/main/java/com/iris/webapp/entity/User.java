package com.iris.webapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = -1000119078147252957L;

	@Id
	@Column(name = "id", length = 11, nullable = false)
	private int id;

	@Column(name = "name", length = 255, nullable = false)
	private String name;

	@Column(name = "phone", length = 10, nullable = false)
	private String phone;

	@Column(name = "email", length = 500, nullable = true)
	private String email;

	@Column(name = "password", length = 128, nullable = false)
	private String password;

	@Column(name = "role", length = 20, nullable = false)
	private Role userRole;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static enum Role {
		VENDOR, CUSTOMER
	}
}