package com.deveuge.calendate.view.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.deveuge.calendate.model.entity.User;
import com.deveuge.calendate.view.dto.UserDTO;

@Component
public class UserToDTOConverter implements Converter<User, UserDTO>{

	@Override
	public UserDTO convert(User source) {
		UserDTO user = new UserDTO(
				source.getUsername(), 
				source.getName(), 
				source.getEmail(),
				source.getLocale().toString().replaceAll("_", "-"));
		return user;
	}

}
