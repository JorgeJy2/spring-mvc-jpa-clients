package com.jorgejy.springboot.app.model.dao;

import com.jorgejy.springboot.app.model.entity.Client;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientDao extends PagingAndSortingRepository <Client, Long> {
	
}
