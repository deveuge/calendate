package com.deveuge.calendate.model.entity.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.deveuge.calendate.model.entity.User;


@Embeddable
public class EventTypeId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_username", nullable = false)
	private User user;
	
	@Column(length = 50)
	private String url;

	public EventTypeId() {
		super();
	}

	public EventTypeId(User user, String url) {
		super();
		this.user = user;
		this.url = url;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
