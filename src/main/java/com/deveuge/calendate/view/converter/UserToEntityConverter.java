package com.deveuge.calendate.view.converter;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.deveuge.calendate.model.entity.User;
import com.deveuge.calendate.model.service.UserService;
import com.deveuge.calendate.utils.SecurityUtils;
import com.deveuge.calendate.view.dto.UserDTO;

@Component
public class UserToEntityConverter implements Converter<UserDTO, User> {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User convert(UserDTO source) {
		User user = source.getName() == null
				? new User(source.getUsername(), source.getEmail())
				: userService.findOne(SecurityUtils.getCurrentUserUsername());
		
		user.setName(source.getName());
		user.setEmail(source.getEmail());
		Locale userLocale = source.getLocale() != null 
				? Locale.forLanguageTag(source.getLocale())
				: Locale.ENGLISH;
					
		user.setLocale(userLocale);
		if(StringUtils.hasText(source.getPassword())) {
			user.setPassword(passwordEncoder.encode(source.getPassword()));
		}
		
		return user;
	}

}
