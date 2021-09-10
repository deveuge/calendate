package com.deveuge.calendate.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.deveuge.calendate.model.entity.EventType;
import com.deveuge.calendate.model.entity.key.EventTypeId;

public interface IEventTypeDAO extends CrudRepository<EventType, EventTypeId> {

	List<EventType> findAllByEventTypeIdUserUsername(String username);
	Optional<EventType> findByEventTypeIdUserUsernameAndEventTypeIdUrl(String username, String url);
	void deleteByEventTypeIdUserUsernameAndEventTypeIdUrl(String username, String url);
}
