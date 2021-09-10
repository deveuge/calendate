package com.deveuge.calendate.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deveuge.calendate.enums.EmailType;
import com.deveuge.calendate.model.entity.Event;
import com.deveuge.calendate.model.entity.EventType;
import com.deveuge.calendate.model.entity.User;
import com.deveuge.calendate.model.service.EmailService;
import com.deveuge.calendate.model.service.EventService;
import com.deveuge.calendate.model.service.EventTypeService;
import com.deveuge.calendate.utils.ViewUtils;
import com.deveuge.calendate.view.dto.EventDTO;

@Controller
public class UserController {
	
	@Autowired
	private EventTypeService eventTypeService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/{username}/{url}")
	public String viewEventPage(@PathVariable String username, @PathVariable String url, 
			Model model, RedirectAttributes redir) {
		EventType eventType = eventTypeService.findOne(username, url);
		if(eventType == null) {
			return ViewUtils.redirect404(redir);
		}
		model.addAttribute("username", username);
		model.addAttribute("url", url);
		model.addAttribute("eventType", eventTypeService.convert(eventType));
		if (!model.containsAttribute("event")) {
			model.addAttribute("event", new EventDTO());
		}
		
		return "events/view";
	}
	
	@PostMapping("/{username}/{url}")
	public String bookEventPage(
			@Valid @ModelAttribute("event") EventDTO eventDTO,
			@PathVariable String username, @PathVariable String url, 
			BindingResult result, RedirectAttributes redir, Model model, Locale locale) {
		if (result.hasErrors()) {
			redir.addFlashAttribute("org.springframework.validation.BindingResult.event", result);
			redir.addFlashAttribute("event", eventDTO);
			return "redirect:/" + username + "/" + url;
		}
		
		Event event = eventService.convert(eventDTO);
		eventService.save(event);
		
		// Send notification emails
		User organizer = event.getEventType().getEventTypeId().getUser();
		emailService.sendEmail(organizer.getEmail(), EmailType.NEW, event, organizer.getLocale());
		if(event.getEventType().getConfirmationEmail()) {
			emailService.sendEmail(event.getEmail(), EmailType.CONFIRMATION, event, locale);
		}
		
		model.addAttribute("date", event.getDateFrom());
		model.addAttribute("color", event.getEventType().getColor());
		model.addAttribute("confirmation", event.getEventType().getConfirmationEmail());
		model.addAttribute("reminder", event.getEventType().getReminderEmail());
		return "events/confirmed";
	}
}
