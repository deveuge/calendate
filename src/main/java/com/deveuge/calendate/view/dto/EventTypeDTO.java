package com.deveuge.calendate.view.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.deveuge.calendate.enums.Location;
import com.deveuge.calendate.enums.Weekday;

public class EventTypeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Size(min = 3, max = 100)
	private String name;
	@Size(min = 3, max = 50)
	private String url;
	@Size(max = 350)
	private String description;
	private Location location;
	private String locationDetail;
	private String color;
	private Integer duration;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateFrom;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateTo;
	private Boolean confirmation;
	private Boolean reminder;
	@DateTimeFormat(pattern = "HH:mm")
	private EnumMap<Weekday, Date[]> availability = new EnumMap<>(Weekday.class);
	
	private String username;
	
	public EventTypeDTO() {
		super();
	}

	public EventTypeDTO(String url, String name, String description, 
			Location location, String locationDetail, String color, Integer duration, 
			Date dateFrom, Date dateTo, Boolean confirmation, Boolean reminder, 
			EnumMap<Weekday, Date[]> availability) {
		super();
		this.url = url;
		this.name = name;
		this.description = description;
		this.location = location;
		this.locationDetail = locationDetail;
		this.color = color;
		this.duration = duration;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.confirmation = confirmation;
		this.reminder = reminder;
		this.availability = availability;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLocationDetail() {
		return locationDetail;
	}

	public void setLocationDetail(String locationDetail) {
		this.locationDetail = locationDetail;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
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
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTo);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		this.dateTo = cal.getTime();
	}

	public Boolean getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(Boolean confirmation) {
		this.confirmation = confirmation;
	}

	public Boolean getReminder() {
		return reminder;
	}

	public void setReminder(Boolean reminder) {
		this.reminder = reminder;
	}

	public EnumMap<Weekday, Date[]> getAvailability() {
		for (Weekday day : Weekday.values()) { 
			if(!availability.containsKey(day)) {
				availability.put(day, new Date[2]);
			}
		}
		return availability;
	}

	public void setAvailability(EnumMap<Weekday, Date[]> availability) {
		this.availability = availability;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
