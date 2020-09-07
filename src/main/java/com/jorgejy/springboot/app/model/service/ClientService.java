package com.jorgejy.springboot.app.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jorgejy.springboot.app.model.entity.Client;

public interface ClientService {

	public Client findOne(Long id);
	public List<Client> findAll();
	public Page<Client> findAll(Pageable pageable);
	public void save(Client client);
	public void delete(Long id);
	public Client fetchByIdWithBills(Long id);
}
