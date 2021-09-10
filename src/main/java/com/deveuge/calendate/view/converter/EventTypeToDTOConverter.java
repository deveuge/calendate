package com.deveuge.calendate.view.converter;

import java.util.Date;
import java.util.EnumMap;
import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.deveuge.calendate.enums.Weekday;
import com.deveuge.calendate.model.entity.EventType;
import com.deveuge.calendate.model.entity.EventTypeAvailability;
import com.deveuge.calendate.view.dto.EventTypeDTO;

@Component
public class EventTypeToDTOConverter implements Converter<EventType, EventTypeDTO>{

	@Override
	public EventTypeDTO convert(EventType source) {
		EventTypeDTO event = new EventTypeDTO(
				source.getEventTypeId().getUrl(), 
				source.getName(), 
				source.getDescription(),
				source.getLocation(),
				source.getLocationDetail(),
				source.getColor(),
				source.getDuration(), 
				source.getDateFrom(), 
				source.getDateTo(), 
				source.getConfirmationEmail(), 
				source.getReminderEmail(), 
				convertAvailabilityToAvailabilityDTO(source.getAvailability()));
		
		event.setUsername(source.getEventTypeId().getUser().getName());
		return event;
	}
	
	private EnumMap<Weekday, Date[]> convertAvailabilityToAvailabilityDTO(Set<EventTypeAvailability> availability) {
		EnumMap<Weekday, Date[]> availabilityDTO = new EnumMap<>(Weekday.class);
		for(EventTypeAvailability entry : availability) {
			availabilityDTO.put(entry.getWeekday(), new Date[] {entry.getTimeFrom(), entry.getTimeTo()});
		}
		return availabilityDTO;
	}

}
