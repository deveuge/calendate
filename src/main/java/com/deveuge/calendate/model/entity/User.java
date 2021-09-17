package com.deveuge.calendate.model.entity;

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.deveuge.calendate.enums.Role;

@Entity
@Table(name = "user_data")
public class User {

	@Id
	@Column(length = 20)
	private String username;
	@Column(length = 100)
	private String name;
	@Column(length = 100)
	private String email;
	@Column(length = 255)
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	private boolean enabled;
	private Locale locale;
	
	public User() {
	}

	public User(String username, String name, String email, String password, Role role, boolean enabled) {
		super();
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}
	
	public User(String username, String email) {
		super();
		this.username = username;
		this.name = username;
		this.email = email;
		this.enabled = true;
		this.role = Role.USER;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
}
