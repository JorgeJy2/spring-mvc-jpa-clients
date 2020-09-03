package com.jorgejy.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jorgejy.springboot.app.model.entity.Client;
import com.jorgejy.springboot.app.model.service.ClientService;
import com.jorgejy.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {	
		
		Pageable pageable = PageRequest.of(page , 5);
		Page<Client> clients = clientService.findAll(pageable);		
		PageRender<Client> pageRender = new PageRender<>("/list", clients);
		
		model.addAttribute("title", "get all clients.");
		model.addAttribute("clients", clients);
		model.addAttribute("page", pageRender);
		
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
	public String edit(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Client client = null;
		
		if(id > 0) {
			client = clientService.findOne(id);
			if(client == null) {
				flash.addFlashAttribute("danger", "Client no exist.");
				return "redirect:/list";
			}
		} else {
			flash.addFlashAttribute("danger", "ID not bee 0.");
			return "redirect:/list";
		}
		
		model.put("client", client);
		model.put("title", "Edit client");
		
		return "form";
	}
	
	// forever join @Valid Client client, BindingResult bindingResult MODEL and binding
	// if want  change name use @ModelAtribut in class model.
	@PostMapping(value="/form") 
	public String save(@Valid Client client, BindingResult bindingResult, Model model,RedirectAttributes flash, SessionStatus sessionStatus) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("title", "Form client");
			return "form";
		}
		
		String messageFlash = (client.getId() != null) ? "Client updated!" : "Client created!";
		
		clientService.save(client);
		sessionStatus.setComplete();
		
		flash.addFlashAttribute("success", messageFlash);
		return "redirect:list";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if(id>0) {
			clientService.delete(id);
		}
		flash.addFlashAttribute("success", "Client deleted!");
		return "redirect:/list";
	}
}
