package com.deveuge.calendate.model.service;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.deveuge.calendate.enums.EmailType;
import com.deveuge.calendate.model.entity.Event;
import com.deveuge.calendate.utils.EmailUtils;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
    private MessageSource messageSource;
	
	private static final String LIST_ITEM_TEMPLATE = "<li><b>%s</b>: %s</li>"; 
	
	@Value("${calendate.url}")
	private String calendateURL;
	
	@Async
	public void sendEmail(String email, EmailType type, Event event, Locale locale) {
		try {
			MimeMessage message = buildEmail(email, type, event, locale);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private MimeMessage buildEmail(String email, EmailType type, Event event, Locale locale) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(InternetAddress.parse(email));
		helper.setSubject("[Calendate] " + getMessage("email.subject." + type.toString(), locale));
		helper.setText(buildEmailBody(type, event, locale), true);
		return message;
	}

	private String getMessage(String key, Locale locale) {
		return messageSource.getMessage(key, null, locale);
	}

	private String buildEmailBody(EmailType type, Event event, Locale locale) {
		String body = EmailUtils.readBodyFile("EmailTemplate.html");
		body = body
			.replace("${Title}", getMessage("email.subject." + type.toString(), locale))
			.replace("${Body}", getMessage("email.body." + type.toString(), locale))
			.replace("${List}", buildEmailDetails(type, event, locale))
			.replace("${Legal}", getMessage("email.legal.disclaimer", locale))
			.replace("${Notice}", getMessage("email.notice." + type.toString(), locale))
			.replace("${CalendateURL}", calendateURL);
		return body;
	}
	
	private String buildEmailDetails(EmailType type, Event event, Locale locale) {
		switch(type) {
		case CONFIRMATION:
		case REMINDER:
			return buildEventDetails(event, locale);
		default:
			return buildAttendantDetails(event, locale);
		}
	}
	
	private String buildEventDetails(Event event, Locale locale) {
		StringBuilder list = new StringBuilder();
		list
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.event-name", locale), event.getEventType().getName()))
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.organizer", locale), event.getEventType().getEventTypeId().getUser().getName()))
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.date-from", locale), event.getDateFrom()))
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.duration", locale), event.getEventType().getDuration() + " min"))
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.location", locale), getMessage("form.label.location." + event.getEventType().getLocation(), locale)))
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.location-detail", locale), event.getEventType().getLocationDetail()))
						;
		return list.toString();
	}
	
	private String buildAttendantDetails(Event event, Locale locale) {
		StringBuilder list = new StringBuilder();
		list
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.event-name", locale), event.getName()))
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.date-from", locale), event.getDateFrom()))
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.duration", locale), event.getEventType().getDuration() + " min"))
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.location", locale), getMessage("form.label.location." + event.getEventType().getLocation(), locale)))
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.location-detail", locale), event.getEventType().getLocationDetail()))
			.append(String.format("</ul><h4>%s</h4><ul>", getMessage("book.title.contact", locale)))
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.name", locale), event.getName()))
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.email", locale), event.getEmail()))
			.append(String.format(LIST_ITEM_TEMPLATE, getMessage("form.label.phone", locale), event.getPhone()))
			;
		return list.toString();
	}
}