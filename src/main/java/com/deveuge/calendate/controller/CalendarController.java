package com.deveuge.calendate.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deveuge.calendate.enums.Weekday;
import com.deveuge.calendate.model.entity.Event;
import com.deveuge.calendate.model.entity.EventType;
import com.deveuge.calendate.model.service.EventService;
import com.deveuge.calendate.model.service.EventTypeService;
import com.deveuge.calendate.utils.SecurityUtils;
import com.deveuge.calendate.view.dto.AvailableHoursDTO;
import com.deveuge.calendate.view.dto.EventDTO;

@RestController
@RequestMapping("/api/")
public class CalendarController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventTypeService eventTypeService;
	
	@Autowired
	private Converter<Event, EventDTO> eventToDTOConverter;
	
	@Autowired
	private MessageSource messageSource;
	
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	/**
	 * Get events from specific month
	 * @param year
	 * @param month
	 * @return
	 */
	@GetMapping("/cal/{year}/{month}")
	public List<Object[]> getMonthEvents(@PathVariable int year, @PathVariable int month) {
		LocalDate dateFrom = LocalDate.of(year, month, 1);
		
		return eventService.countAllBetweenDates(
				SecurityUtils.getCurrentUserUsername(),
				getStartDate(year, month, 1), 
				getEndDate(year, month, dateFrom.lengthOfMonth()));
	}
	
	/**
	 * Get events from a specific day
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	@GetMapping("/cal/{year}/{month}/{day}")
	public List<EventDTO> getDayEvents(@PathVariable int year, @PathVariable int month, @PathVariable int day, Locale locale) {
		List<Event> events = eventService.findAllBetweenDates(
				SecurityUtils.getCurrentUserUsername(), 
				getStartDate(year, month, day), 
				getEndDate(year, month, day));
		
		return events.stream()
        	.map(e -> {
        		EventDTO event = eventToDTOConverter.convert(e);
        		event.setLocation(messageSource.getMessage("form.label.location." + event.getLocation(), null, locale));
        		return event;
        		})
        	.collect(Collectors.toList());
	}
	
	/**
	 * Get availability for specific month
	 * @param username
	 * @param url
	 * @param year
	 * @param month
	 * @return
	 */
	@GetMapping("/aval/{username}/{url}/{year}/{month}")
	public String[] getEventDayAvailability(@PathVariable String username, @PathVariable String url, 
			@PathVariable int year, @PathVariable int month) {
		List<String> result = new ArrayList<>();
		LocalDate dateFrom = LocalDate.of(year, month, 1);
		
		for(int i = 0; i < dateFrom.lengthOfMonth(); i++) {
			String[] availability = getEventHourAvailability(username, url, year, month, i + 1);
			if(availability != null && availability.length > 0) {
				result.add(String.format("%02d", i + 1));
			}
		}
		
		return result.toArray(new String[result.size()]);
	}
	
	/**
	 * Get availability for specific day
	 * @param username
	 * @param url
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	@GetMapping("/aval/{username}/{url}/{year}/{month}/{day}")
	public String[] getEventHourAvailability(@PathVariable String username, @PathVariable String url, 
			@PathVariable int year, @PathVariable int month, @PathVariable int day) {
		Date date = getStartDate(year, month, day);
		EventType eventType = eventTypeService.findOne(username, url);
		
		// Do not calculate availability for unavailable days
		if(date.before(new Date()) || date.before(eventType.getDateFrom()) || date.after(eventType.getDateTo())) {
			return new String[] {};
		}
		
		Weekday weekday = getWeekday(year, month, day);
		Date[] availability = eventType.getAvailabilityMap().get(weekday);
		if(availability == null) {
			return new String[] {};
		}
		
		List<AvailableHoursDTO> reservedHours = eventService.findHoursReserved(
				username, 
				getStartDate(year, month, day), 
				getEndDate(year, month, day));
		
		String stringDateFrom = (availability[0] == null ? "00:00" : availability[0].toString().substring(0, 5));
		String stringDateTo = (availability[1] == null ? "23:59" : availability[1].toString().substring(0, 5));
		
		String[] availableHours = getAvailableHours(
				LocalTime.parse(stringDateFrom, TIME_FORMATTER), 
				LocalTime.parse(stringDateTo, TIME_FORMATTER), 
				eventType.getDuration(), 
				reservedHours);
		
		return availableHours;
	}
	
	private Date getStartDate(int year, int month, int day) {
		return Date.from(LocalDate.of(year, month, day)
				.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	private Date getEndDate(int year, int month, int day) {
		return Date.from(LocalDate.of(year, month, day)
				.atTime(LocalTime.MAX)
				.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	private Weekday getWeekday(int year, int month, int day) {
		Date date = getStartDate(year, month, day);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return Weekday.values()[c.get(Calendar.DAY_OF_WEEK) - 1];
	}
	
	private String[] getAvailableHours(LocalTime timeFrom, LocalTime timeTo, int duration, List<AvailableHoursDTO> reservedHours) {
		List<String> availableHours = new ArrayList<>();
		
		while(timeFrom.plusMinutes(duration).isBefore(timeTo)) {
			// If next day, stop adding hours
			if(timeFrom.plusMinutes(duration).isAfter(LocalTime.parse("23:59", TIME_FORMATTER)) || 
					timeFrom.plusMinutes(duration).isBefore(timeFrom)) {
				break;
			}
			boolean addHour = true;
			for(AvailableHoursDTO hours : reservedHours) {
				LocalTime hourFrom = LocalTime.parse(hours.getTimeFrom(), TIME_FORMATTER);
				LocalTime hourTo = LocalTime.parse(hours.getTimeTo(), TIME_FORMATTER);
				// If hour reserved, don't add it
				if((timeFrom.isAfter(hourFrom) || timeFrom.equals(hourFrom)) && 
						timeFrom.isBefore(hourTo)) {
					addHour = false;
					break;
				}
			}
			if(addHour) {
				availableHours.add(timeFrom.toString());
			}
			timeFrom = timeFrom.plusMinutes(duration);
		}
		
		return availableHours.toArray(new String[availableHours.size()]);
	}
	
}
