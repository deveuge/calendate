package com.deveuge.calendate.view.converter;

import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.deveuge.calendate.enums.Weekday;
import com.deveuge.calendate.model.entity.EventType;
import com.deveuge.calendate.model.entity.EventTypeAvailability;
import com.deveuge.calendate.model.service.EventTypeService;
import com.deveuge.calendate.model.service.UserService;
import com.deveuge.calendate.utils.SecurityUtils;
import com.deveuge.calendate.view.dto.EventTypeDTO;

@Component
public class EventTypeToEntityConverter implements Converter<EventTypeDTO, EventType> {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EventTypeService eventTypeService;

	@Override
	public EventType convert(EventTypeDTO source) {
		EventType originalEvent = eventTypeService.findOne(source.getUrl());
		EventType event = originalEvent == null
				? new EventType(userService.findOne(SecurityUtils.getCurrentUserUsername()), source.getUrl())
				: originalEvent;
				
		event.setName(source.getName());
		event.setDescription(source.getDescription());
		event.setLocation(source.getLocation());
		event.setLocationDetail(source.getLocationDetail());
		event.setColor(source.getColor());
		event.setDuration(source.getDuration());
		event.setDateFrom(source.getDateFrom());
		event.setDateTo(source.getDateTo());
		event.setConfirmationEmail(source.getConfirmation());
		event.setReminderEmail(source.getReminder());
		event.getAvailability().clear();
		event.getAvailability().addAll(convertAvailabilityDTOToAvailability(source.getAvailability()));
				
		return event;
	}
	
	private Set<EventTypeAvailability> convertAvailabilityDTOToAvailability(EnumMap<Weekday, Date[]> availabilityDTO) {
		Set<EventTypeAvailability> availability = new HashSet<>();
		for(Entry<Weekday, Date[]> entry : availabilityDTO.entrySet()) {
			if(entry.getValue()[0] != null || entry.getValue()[1] != null)
				availability.add(new EventTypeAvailability(entry.getKey(), entry.getValue()[0], entry.getValue()[1]));
		}
		return availability;
	}

}
