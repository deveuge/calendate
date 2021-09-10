package com.deveuge.calendate.view.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.deveuge.calendate.enums.Role;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Size(min = 3, max = 20)
	private String username;
	@Size(min = 3, max = 100)
	private String name;
	@Email
	@Size(min = 3, max = 100)
	private String email;
	@Size(min = 8, max = 20)
	private String password;
	private Role role;
	private String locale;
	private String repeatPassword;
	
	
	public UserDTO() {
		super();
	}

	public UserDTO(String username, String name, String email, String locale) {
		super();
		this.username = username;
		this.name = name;
		this.email = email;
		this.locale = locale;
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

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
}
