package com.jorgejy.springboot.app.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jorgejy.springboot.app.model.dao.ClientDao;
import com.jorgejy.springboot.app.model.entity.Client;

@Service
public class ClientServiceImplements implements ClientService {

	@Autowired
	private ClientDao clientDao;

	@Override
	@Transactional(readOnly=true)
	public Client findOne(Long id) {
		return clientDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		return (List<Client>) clientDao.findAll();
	}

	@Override
	@Transactional
	public void save(Client client) {
		clientDao.save(client);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clientDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Client> findAll(Pageable pageable) {
		return clientDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Client fetchByIdWithBills(Long id) {
		return clientDao.fetchByIdWithBills(id);
	}
	
}
