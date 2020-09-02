package com.jorgejy.springboot.app.model.service;

import java.util.List;

import com.jorgejy.springboot.app.model.entity.Client;

public interface ClientService {

	public List<Client> findAll();
	public void save(Client client);
	public Client findOne(Long id);
	public void delete(Long id);

}
