package com.jorgejy.springboot.app.model.service;

import com.jorgejy.springboot.app.model.entity.Bill;

public interface BillService {

	public void save(Bill bill);
	public Bill findBillById(Long id);
	public void delete(Long id);
	public Bill fetchByIdWithClientWithItemBillWithProduct(Long id);
}
