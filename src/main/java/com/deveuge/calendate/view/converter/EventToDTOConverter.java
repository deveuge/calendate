package com.deveuge.calendate.view.converter;

import java.text.SimpleDateFormat;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.deveuge.calendate.model.entity.Event;
import com.deveuge.calendate.view.dto.EventDTO;

@Component
public class EventToDTOConverter implements Converter<Event, EventDTO>{

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public EventDTO convert(Event source) {
		String[] date = sdf.format(source.getDateFrom()).split(" ");

		EventDTO event = new EventDTO(
				source.getEventType().getEventTypeId().getUser().getUsername(),
				source.getEventType().getEventTypeId().getUrl(),
				source.getEventType().getName(),
				date[0],
				date[1],
				source.getEventType().getLocation().toString(),
				source.getName(), 
				source.getEmail(), 
				source.getPhone(),
				source.getDateFrom(),
				source.getDateTo());
		return event;
	}

}
