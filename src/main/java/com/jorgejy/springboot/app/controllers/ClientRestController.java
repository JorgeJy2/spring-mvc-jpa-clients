package com.jorgejy.springboot.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jorgejy.springboot.app.model.entity.Client;
import com.jorgejy.springboot.app.model.service.ClientService;

@RestController
@RequestMapping("/api/client")
public class ClientRestController {
	
	@Autowired
	private ClientService clientService;

	
	@GetMapping("/list")
	public List<Client> list() {
		return clientService.findAll();
	 }
}
