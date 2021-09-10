package com.deveuge.calendate.view.dto;

import java.util.Date;

public class EventDTO {

	private String username;
	private String url;
	private String eventName;
	
	private String date;
	private String hour;
	private String location;
	private String name;
	private String email;
	private String phone;
	
	private Date dateFrom;
	private Date dateTo;
	
	
	public EventDTO() {
		super();
	}

	public EventDTO(String username, String url, String eventName, 
			String date, String hour, String location, 
			String name, String email, String phone,
			Date dateFrom, Date dateTo) {
		super();
		this.username = username;
		this.url = url;
		this.eventName = eventName;
		this.date = date;
		this.hour = hour;
		this.location = location;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	
}
