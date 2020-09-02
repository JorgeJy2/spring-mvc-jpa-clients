package com.jorgejy.springboot.app.model.dao;

import java.util.List;

import com.jorgejy.springboot.app.model.entity.Client;

public interface ClientDaoManual {

	public List<Client> findAll();
	public void save(Client client);
	public Client findOne(Long id);
	public void delete(Long id);
	
}
