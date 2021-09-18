package com.deveuge.calendate.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.deveuge.calendate.enums.EmailType;
import com.deveuge.calendate.model.entity.Event;
import com.deveuge.calendate.model.entity.User;
import com.deveuge.calendate.model.service.EmailService;
import com.deveuge.calendate.model.service.EventService;

@Configuration
@EnableScheduling
public class ScheduledTasks {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EmailService emailService;

	@Scheduled(fixedDelay = 60 * 60 * 1000)
	public void sendReminderEvents() {
		List<Event> upcomingEvents = eventService.findUpcomingEvents();
		for(Event e : upcomingEvents) {
			User organizer = e.getEventType().getEventTypeId().getUser();
			emailService.sendEmail(e.getEmail() + "," + organizer.getEmail(), EmailType.REMINDER, e, organizer.getLocale());
		}
	}
}
