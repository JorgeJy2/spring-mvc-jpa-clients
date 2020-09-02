package com.jorgejy.springboot.app.model.dao;

import com.jorgejy.springboot.app.model.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientDao extends CrudRepository<Client, Long> {
	
}
