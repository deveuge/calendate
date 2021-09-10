package com.deveuge.calendate.controller;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deveuge.calendate.enums.Alert;
import com.deveuge.calendate.enums.Weekday;
import com.deveuge.calendate.model.entity.EventType;
import com.deveuge.calendate.model.service.EventTypeService;
import com.deveuge.calendate.utils.SecurityUtils;
import com.deveuge.calendate.utils.ViewUtils;
import com.deveuge.calendate.view.dto.EventTypeDTO;

@Controller
@RequestMapping("/events")
public class EventTypeController {
	
	@Autowired
	private EventTypeService eventTypeService;

	@GetMapping("")
	public String viewEventTypes(Model model) {
		List<EventType> eventTypes = eventTypeService.findAllByUsername(SecurityUtils.getCurrentUserUsername());
		model.addAttribute("eventTypes", eventTypes);
		return "events/list";
	}
	
	@GetMapping("/new")
	public String viewCreateEventType(Model model) {
		if (!model.containsAttribute("eventType")) {
			model.addAttribute("eventType", new EventTypeDTO());
		}
		return "events/edit";
	}
	
	@PostMapping("/new")
	public String createEventType(@Valid @ModelAttribute("eventType") EventTypeDTO eventTypeDTO, BindingResult result, RedirectAttributes redir) {
		if(eventTypeService.findOne(eventTypeDTO.getUrl()) != null) {
			result.reject("Repeated.eventType.url");
		}
		result = validateSpecificConditions(eventTypeDTO, result);
		if (result.hasErrors()) {
			redir.addFlashAttribute("org.springframework.validation.BindingResult.eventType", result);
			redir.addFlashAttribute("eventType", eventTypeDTO);
			return "redirect:/events/new";
		}
		
		eventTypeService.save(eventTypeService.convert(eventTypeDTO));
		ViewUtils.createAlert(redir, Alert.SUCCESS, "event.success.create");
		return "redirect:/events";
	}
	
	@GetMapping("/{url}")
	public String viewEditEventType(Model model, @PathVariable String url, RedirectAttributes redir) {
		EventType eventType = eventTypeService.findOne(url);
		if(eventType == null) {
			return ViewUtils.redirect404(redir);
		}
		if (!model.containsAttribute("eventType")) {
			model.addAttribute("eventType", eventTypeService.convert(eventType));
		}
		model.addAttribute("isEdit", true);
		return "events/edit";
	}
	
	@PostMapping("/edit")
	public String editEventType(@Valid @ModelAttribute("eventType") EventTypeDTO eventTypeDTO, BindingResult result, RedirectAttributes redir) {
		result = validateSpecificConditions(eventTypeDTO, result);
		if (result.hasErrors()) {
			redir.addFlashAttribute("org.springframework.validation.BindingResult.eventType", result);
			redir.addFlashAttribute("eventType", eventTypeDTO);
			return "redirect:/events/" + eventTypeDTO.getUrl();
		}
		
		eventTypeService.save(eventTypeService.convert(eventTypeDTO));
		ViewUtils.createAlert(redir, Alert.SUCCESS, "event.success.update");
		return "redirect:/events";
	}
	
	
	@PostMapping("/{url}/delete")
	public String deleteEventType(@PathVariable String url, RedirectAttributes redir) {
		eventTypeService.delete(url);
		ViewUtils.createAlert(redir, Alert.SUCCESS, "event.success.delete");
		return "redirect:/events";
	}
	
	private BindingResult validateSpecificConditions(EventTypeDTO eventTypeDTO, BindingResult result) {
		if(eventTypeDTO.getDateFrom().after(eventTypeDTO.getDateTo())) {
			result.reject("Invalid.eventType.date");
		}
		for(Entry<Weekday, Date[]> entry : eventTypeDTO.getAvailability().entrySet()) {
			if(entry.getValue()[0] != null && entry.getValue()[1] != null &&
					entry.getValue()[0].after(entry.getValue()[1])) {
				result.reject("Invalid.eventType.hour");
				break;
			}
		}
		
		return result;
	}
}
