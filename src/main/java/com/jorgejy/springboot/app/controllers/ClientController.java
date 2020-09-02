package com.jorgejy.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.jorgejy.springboot.app.model.entity.Client;
import com.jorgejy.springboot.app.model.service.ClientService;

@Controller
@SessionAttributes("client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("title", "get all clients.");
		model.addAttribute("clients", clientService.findAll());
		return "list";
	}
	
	@GetMapping("/form")
	public String create(Map<String, Object> model) {
		Client client = new Client();
		
		model.put("client", client);
		model.put("title", "Form client");
		
		return "form";
	}
	
	@GetMapping("/form/{id}")
	public String edit(@PathVariable(value="id") Long id, Map<String, Object> model) {
		Client client = null;
		
		if(id > 0) {
			client = clientService.findOne(id);
		} else {
			return "redirect:/list";
		}
		
		model.put("client", client);
		model.put("title", "Edit client");
		
		return "form";
	}
	
	// forever join @Valid Client client, BindingResult bindingResult MODEL and binding
	// if want  change name use @ModelAtribut in class model.
	@PostMapping(value="/form") 
	public String save(@Valid Client client, BindingResult bindingResult, Model model, SessionStatus sessionStatus) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("title", "Form client");
			return "form";
		}
		
		clientService.save(client);
		sessionStatus.setComplete();
		return "redirect:list";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id) {
		if(id>0) {
			clientService.delete(id);
		}
		return "redirect:/list";
	}
}
