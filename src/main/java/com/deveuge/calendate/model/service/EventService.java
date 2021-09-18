package com.deveuge.calendate.model.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deveuge.calendate.model.dao.IEventDAO;
import com.deveuge.calendate.model.entity.Event;
import com.deveuge.calendate.view.dto.AvailableHoursDTO;
import com.deveuge.calendate.view.dto.EventDTO;

@Service
public class EventService {

	@Autowired
	EntityManager em;
	
	@Autowired
	private IEventDAO eventDAO;
	
	@Autowired
	private Converter<Event, EventDTO> eventConverter;
	@Autowired
	private Converter<EventDTO, Event> eventDTOConverter;
	
	@Transactional(readOnly=true)
	public List<Event> findAllByUsername(String username) {
		return eventDAO.findAllByEventType_EventTypeIdUserUsername(username);
	}

	@Transactional
	public void save(Event event) {
		eventDAO.save(event);
	}
	
	@Transactional(readOnly=true)
	public Event findOne(Long id) {
		return eventDAO.findById(id).orElse(null);
	}

	@Transactional(readOnly=true)
	public List<Event> findAllByUsernameAndUrl(String username, String url) {
		return eventDAO.findAllByEventType_EventTypeIdUserUsernameAndEventType_EventTypeIdUrl(username, url);
	}
	
	@Transactional(readOnly=true)
	public List<Event> findAllBetweenDates(String username, Date dateFrom, Date dateTo) {
		return eventDAO.findAllByEventType_EventTypeIdUserUsernameAndDateFromBetweenOrderByDateFrom(username, dateFrom, dateTo);
	}
	
	@Transactional(readOnly=true)
	public List<Object[]> countAllBetweenDates(String username, Date dateFrom, Date dateTo) {
		return eventDAO.countAllBetweenDates(username, dateFrom, dateTo);
	}
	
	@Transactional(readOnly=true)
	public List<AvailableHoursDTO> findHoursReserved(String username, Date dateFrom, Date dateTo) {
		return eventDAO.findHoursReserved(username, dateFrom, dateTo);
	}
	
	@Transactional(readOnly=true)
	public List<Event> findUpcomingEvents() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Event> cq = cb.createQuery(Event.class);
		Root<Event> root = cq.from(Event.class);
		Join<Object, Object> join = (Join<Object, Object>) root.fetch("eventType");
		Join<Object, Object> join2 = (Join<Object, Object>) join.fetch("eventTypeId");
		Join<Object, Object> join3 = (Join<Object, Object>) join2.fetch("user");
		
		// TODO: Fix for PostgreSQL
		Expression<Long> prod = cb.prod(cb.function("date_part", Long.class, cb.literal("epoch"), root.get("dateFrom")), 1000L);
		Expression<Long> diff = cb.diff(prod, new Date().getTime());
		cq
			.where(cb.equal(join.get("reminderEmail"), true))
			.where(cb.between(diff, 0L, 60 * 60 * 1000L))
		;
		 
		TypedQuery<Event> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Transactional
	public void delete(Long id) {
		eventDAO.deleteById(id);
	}
	
	public Event convert(EventDTO source) {
		return eventDTOConverter.convert(source);
	}
	
	public EventDTO convert(Event source) {
		return eventConverter.convert(source);
	}
}
