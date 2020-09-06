package com.jorgejy.springboot.app.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jorgejy.springboot.app.model.entity.Product;

public interface ProductDao extends CrudRepository<Product, Long> {

	@Query("select p from Product p where p.name like %?1%")
	public List<Product> findByName(String name);
	
	public List<Product> findByNameLikeIgnoreCase(String term);
	
	
}
