package com.jorgejy.springboot.app.model.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jorgejy.springboot.app.model.dao.BillDao;
import com.jorgejy.springboot.app.model.entity.Bill;

@Service
public class BillServiceImplements implements BillService {

	@Autowired
	private BillDao billDao;
	
	@Override
	@Transactional
	public void save(Bill bill) {
		billDao.save(bill);
	}

	@Override
	@Transactional(readOnly = true)
	public Bill findBillById(Long id) {
		return billDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		billDao.deleteById(id);
	}

	@Override
	public Bill fetchByIdWithClientWithItemBillWithProduct(Long id) {
		return billDao.fetchByIdWithClientWithItemBillWithProduct(id);
	}
	
}
