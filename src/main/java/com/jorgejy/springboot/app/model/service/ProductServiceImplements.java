package com.jorgejy.springboot.app.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jorgejy.springboot.app.model.dao.ProductDao;
import com.jorgejy.springboot.app.model.entity.Product;

@Service
public class ProductServiceImplements implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Override
	@Transactional(readOnly = true)	
	public List<Product> findByName(String term) {
		// return productDao.findByName(term);
		return productDao.findByNameLikeIgnoreCase("%"+term+ "%"); 
	}

	@Override
	@Transactional(readOnly = true)
	public Product findProductById(Long id) {
		return productDao.findById(id).orElse(null);
	}

}
