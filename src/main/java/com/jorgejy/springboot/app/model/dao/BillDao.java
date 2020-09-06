package com.jorgejy.springboot.app.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.jorgejy.springboot.app.model.entity.Bill;

public interface BillDao extends CrudRepository<Bill, Long> {

}
