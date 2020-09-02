package com.jorgejy.springboot.app.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.jorgejy.springboot.app.model.entity.Client;

@Repository
public class ClientDaoImplementManual implements ClientDaoManual {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Client findOne(Long id) {
		return entityManager.find(Client.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Client> findAll() {
		return entityManager.createQuery("from Client").getResultList();
	}

	@Override
	public void save(Client client) {
		if(client.getId()  != null && client.getId()  > 0) {
			entityManager.merge(client);
		}else {			
			entityManager.persist(client);
		}
	}

	@Override
	public void delete(Long id) {
		Client client = findOne(id);
		entityManager.remove(client);
	}

}
