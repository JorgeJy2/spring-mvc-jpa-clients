package com.jorgejy.springboot.app.model.service;

import java.util.List;

import com.jorgejy.springboot.app.model.entity.Product;

public interface ProductService {
	
	public List<Product> findByName(String term);
	
	public Product findProductById(Long id);
}
