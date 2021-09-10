package com.deveuge.calendate.security;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

@Component
public class AuthenticationSuccessBean implements ApplicationListener<AuthenticationSuccessEvent> {
	
	@Autowired
	LocaleResolver localeResolver;
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
    private HttpServletResponse response;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		Locale userLocale = ((UserPrincipalImpl) event.getAuthentication().getPrincipal()).getUser().getLocale();
		localeResolver.setLocale(request, response, userLocale);
	}
	
}
