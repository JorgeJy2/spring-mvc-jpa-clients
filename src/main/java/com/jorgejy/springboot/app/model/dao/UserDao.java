package com.jorgejy.springboot.app.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.jorgejy.springboot.app.model.entity.User;

public interface UserDao extends CrudRepository<User, Long>{
	
	public User findByUsername(String username);
	
}
