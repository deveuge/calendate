package com.deveuge.calendate.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.deveuge.calendate.enums.Location;
import com.deveuge.calendate.enums.Weekday;
import com.deveuge.calendate.model.entity.key.EventTypeId;

@Entity
@Table
public class EventType {

	@EmbeddedId
	private EventTypeId eventTypeId;
	
	@Column(length = 100)
	private String name;
	@Column(length = 350)
	private String description;
	private Location location;
	private String locationDetail;
	private String color;
	@Column
	private Integer duration;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFrom;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTo;
	@Column
	private Boolean confirmationEmail;
	@Column
	private Boolean reminderEmail;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	@JoinColumns({
	  @JoinColumn(name = "url_id", nullable = false),
	  @JoinColumn(name = "username_id", nullable = false)
	})
	private Set<EventTypeAvailability> availability = new HashSet<>();
	
	public EventType() {
		super();
	}
	
	public EventType(User user, String url) {
		super();
		this.eventTypeId = new EventTypeId(user, url);
	}

	public EventType(EventTypeId eventTypeId, String name, String description, 
			Location location, String locationDetail,
			String color, Integer duration, Date dateFrom, Date dateTo,
			Boolean confirmationEmail, Boolean reminderEmail) {
		super();
		this.eventTypeId = eventTypeId;
		this.name = name;
		this.description = description;
		this.location = location;
		this.locationDetail = locationDetail;
		this.color = color;
		this.duration = duration;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.confirmationEmail = confirmationEmail;
		this.reminderEmail = reminderEmail;
	}

	public EventTypeId getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(EventTypeId eventTypeId) {
		this.eventTypeId = eventTypeId;
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
		this.dateTo = dateTo;
	}

	public Boolean getConfirmationEmail() {
		return confirmationEmail;
	}

	public void setConfirmationEmail(Boolean confirmationEmail) {
		this.confirmationEmail = confirmationEmail;
	}

	public Boolean getReminderEmail() {
		return reminderEmail;
	}

	public void setReminderEmail(Boolean reminderEmail) {
		this.reminderEmail = reminderEmail;
	}

	public Set<EventTypeAvailability> getAvailability() {
		return availability;
	}
	
	public Map<Weekday, Date[]> getAvailabilityMap() {
		return availability.stream().collect(
				Collectors.toMap(
						EventTypeAvailability::getWeekday, 
						EventTypeAvailability::getTime));
	}

	public void setAvailability(Set<EventTypeAvailability> availability) {
		this.availability = availability;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
