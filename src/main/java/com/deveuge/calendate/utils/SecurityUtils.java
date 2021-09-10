package com.deveuge.calendate.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.deveuge.calendate.model.entity.User;
import com.deveuge.calendate.security.UserPrincipalImpl;

public class SecurityUtils {

	public static boolean isAuthenticated() {
		return  SecurityContextHolder.getContext().getAuthentication() != null &&
				 SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
				 !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
	}
	
	public static User getCurrentUser() {
		UserPrincipalImpl principal = (UserPrincipalImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal.getUser();
	}
	
	public static String getCurrentUserUsername() {
		return getCurrentUser().getUsername();
	}
}
