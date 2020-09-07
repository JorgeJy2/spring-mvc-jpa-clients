package com.jorgejy.springboot.app.model.dao;

import com.jorgejy.springboot.app.model.entity.Client;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientDao extends PagingAndSortingRepository <Client, Long> {

	@Query("select c from Client c left join fetch c.bills f where c.id=?1")
	public Client fetchByIdWithBills(Long id);
}
