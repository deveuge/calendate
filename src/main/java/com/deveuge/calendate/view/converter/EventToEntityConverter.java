package com.deveuge.calendate.view.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.deveuge.calendate.model.entity.Event;
import com.deveuge.calendate.model.entity.EventType;
import com.deveuge.calendate.model.service.EventTypeService;
import com.deveuge.calendate.view.dto.EventDTO;

@Component
public class EventToEntityConverter implements Converter<EventDTO, Event> {
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Autowired
	private EventTypeService eventTypeService;

	@Override
	public Event convert(EventDTO source) {
		try {
			Date date = sdf.parse(source.getDate() + " " + source.getHour());
		    EventType eventType = eventTypeService.findOne(source.getUsername(), source.getUrl());
		    Date dateTo = new Date(date.getTime() + (eventType.getDuration() * 60000));
			
			Event event = new Event(
					date,
					dateTo,
					source.getName(), 
					source.getEmail(), 
					source.getPhone(), 
					eventType);
			return event;
		} catch(Exception ex) {
			return null;
		}
	}

}
