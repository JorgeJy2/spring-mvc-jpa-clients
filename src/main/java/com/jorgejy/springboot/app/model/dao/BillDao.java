package com.jorgejy.springboot.app.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jorgejy.springboot.app.model.entity.Bill;

public interface BillDao extends CrudRepository<Bill, Long> {
	
	@Query("select b from Bill b join fetch b.client c join fetch b.items l join fetch l.product where b.id=?1")
	public Bill fetchByIdWithClientWithItemBillWithProduct(Long id);
}
