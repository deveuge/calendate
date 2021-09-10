package com.deveuge.calendate.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deveuge.calendate.model.dao.IUserDAO;
import com.deveuge.calendate.model.entity.User;
import com.deveuge.calendate.view.dto.UserDTO;
 
@Service
public class UserService {

	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private Converter<User, UserDTO> userConverter;
	@Autowired
	private Converter<UserDTO, User> userDTOConverter;
	
	@Transactional(readOnly=true)
	public List<User> findAll() {
		return (List<User>) userDAO.findAll();
	}

	@Transactional
	public void save(User user) {
		userDAO.save(user);
	}

	@Transactional(readOnly=true)
	public User findOne(String id) {
		return userDAO.findById(id).orElse(null);
	}
	
	@Transactional(readOnly=true)
	public User findByEmail(String email) {
		return userDAO.findByEmail(email);
	}
	
	@Transactional(readOnly = true)
	public User findByUsernameOrEmail(String username, String email) {
		return userDAO.findByUsernameOrEmail(username, email);
	}

	@Transactional
	public void delete(String id) {
		userDAO.deleteById(id);
	}
	
	public User convert(UserDTO source) {
		return userDTOConverter.convert(source);
	}
	
	public UserDTO convert(User source) {
		return userConverter.convert(source);
	}
	
}
