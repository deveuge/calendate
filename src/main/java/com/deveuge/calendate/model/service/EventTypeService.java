package com.deveuge.calendate.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deveuge.calendate.model.dao.IEventTypeDAO;
import com.deveuge.calendate.model.entity.EventType;
import com.deveuge.calendate.utils.SecurityUtils;
import com.deveuge.calendate.view.dto.EventTypeDTO;
 
@Service
public class EventTypeService {

	@Autowired
	private IEventTypeDAO eventTypeDAO;
	
	@Autowired
	private Converter<EventType, EventTypeDTO> eventTypeConverter;
	@Autowired
	private Converter<EventTypeDTO, EventType> eventTypeDTOConverter;
	
	@Transactional(readOnly=true)
	public List<EventType> findAllByUsername(String username) {
		return eventTypeDAO.findAllByEventTypeIdUserUsername(username);
	}

	@Transactional
	public void save(EventType eventType) {
		eventTypeDAO.save(eventType);
	}

	@Transactional(readOnly=true)
	public EventType findOne(String username, String url) {
		return eventTypeDAO.findByEventTypeIdUserUsernameAndEventTypeIdUrl(username, url).orElse(null);
	}
	
	@Transactional(readOnly=true)
	public EventType findOne(String url) {
		return eventTypeDAO.findByEventTypeIdUserUsernameAndEventTypeIdUrl(SecurityUtils.getCurrentUserUsername(), url).orElse(null);
	}

	@Transactional
	public void delete(String url) {
		eventTypeDAO.deleteByEventTypeIdUserUsernameAndEventTypeIdUrl(SecurityUtils.getCurrentUserUsername(), url);
	}
	
	public EventType convert(EventTypeDTO source) {
		return eventTypeDTOConverter.convert(source);
	}
	
	public EventTypeDTO convert(EventType source) {
		return eventTypeConverter.convert(source);
	}
}
