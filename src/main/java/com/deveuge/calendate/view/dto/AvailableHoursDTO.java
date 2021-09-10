package com.deveuge.calendate.view.dto;

public class AvailableHoursDTO {

	private String timeFrom;
	private String timeTo;
	
	public AvailableHoursDTO() {
		super();
	}
	
	public AvailableHoursDTO(String timeFrom, String timeTo) {
		super();
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
	}
	
	public String getTimeFrom() {
		return timeFrom;
	}
	
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}
	
	public String getTimeTo() {
		return timeTo;
	}
	
	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}
}
