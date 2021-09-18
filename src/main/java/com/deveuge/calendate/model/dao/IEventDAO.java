package com.deveuge.calendate.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.deveuge.calendate.model.entity.Event;
import com.deveuge.calendate.view.dto.AvailableHoursDTO;

public interface IEventDAO extends CrudRepository<Event, Long> {

	List<Event> findAllByEventType_EventTypeIdUserUsername(String username);
	List<Event> findAllByEventType_EventTypeIdUserUsernameAndEventType_EventTypeIdUrl(String username, String url);
	List<Event> findAllByEventType_EventTypeIdUserUsernameAndDateFromBetweenOrderByDateFrom(String username, Date dateFrom, Date dateTo);
	
	@Query(value = "SELECT to_char(e.dateFrom, 'dd'), COUNT(e.id), date_trunc('day', e.dateFrom) FROM Event e WHERE (dateFrom >= :dateFrom AND dateFrom <= :dateTo) AND username_id = :username GROUP BY 1,3 ORDER BY 3")
	List<Object[]> countAllBetweenDates(@Param(value = "username") String username, @Param(value = "dateFrom") Date dateFrom, @Param(value = "dateTo") Date dateTo);
	
	@Query(value = "SELECT new com.deveuge.calendate.view.dto.AvailableHoursDTO(to_char(e.dateFrom, 'HH24:MI'), to_char(e.dateTo, 'HH24:MI')) FROM EventType t " + 
			"JOIN Event e ON t.eventTypeId.url=e.eventType.eventTypeId.url AND t.eventTypeId.user.username=e.eventType.eventTypeId.user.username " + 
			"WHERE (e.dateFrom >= :dateFrom AND e.dateFrom <= :dateTo) " + 
			"AND t.eventTypeId.user.username = :username ")
	List<AvailableHoursDTO> findHoursReserved(@Param(value = "username") String username, @Param(value = "dateFrom") Date dateFrom, @Param(value = "dateTo") Date dateTo);
}
