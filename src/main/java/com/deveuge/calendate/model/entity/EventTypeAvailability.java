package com.deveuge.calendate.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.deveuge.calendate.enums.Weekday;

@Entity
@Table(name = "availability")
public class EventTypeAvailability {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private Weekday weekday;
	@Column
	@Temporal(TemporalType.TIME)
	private Date timeFrom;
	@Column
	@Temporal(TemporalType.TIME)
	private Date timeTo;
	
	public EventTypeAvailability() {
		super();
	}

	public EventTypeAvailability(Weekday weekday, Date timeFrom, Date timeTo) {
		super();
		this.weekday = weekday;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Weekday getWeekday() {
		return weekday;
	}

	public void setWeekday(Weekday weekday) {
		this.weekday = weekday;
	}

	public Date getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Date timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Date getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Date timeTo) {
		this.timeTo = timeTo;
	}
	
	public Date[] getTime() {
		return new Date[] {getTimeFrom(), getTimeTo()};
	}

}
