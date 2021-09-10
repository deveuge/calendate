package com.deveuge.calendate.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table
@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFrom;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTo;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private String phone;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	  @JoinColumn(name = "url_id", nullable = false),
	  @JoinColumn(name = "username_id", nullable = false)
	})
	private EventType eventType;

	public Event() {
		super();
	}

	public Event(Date dateFrom, Date dateTo, String name, String email, String phone, EventType eventType) {
		super();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.eventType = eventType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date datetime) {
		this.dateFrom = datetime;
	}
	
	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	
}
