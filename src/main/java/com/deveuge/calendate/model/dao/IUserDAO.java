package com.deveuge.calendate.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.deveuge.calendate.model.entity.User;

public interface IUserDAO extends CrudRepository<User, String> {
	
	User findByEmail(String email);
	User findByUsernameOrEmail(String username, String email);

}
