package com.jorgejy.springboot.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jorgejy.springboot.app.model.entity.Product;
import com.jorgejy.springboot.app.model.service.ProductService;

@Controller
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping(value="/find/{term}", produces = {"application/json"})
	public @ResponseBody List<Product> find(@PathVariable String term) {
		return productService.findByName(term);
	}
}
