package com.jorgejy.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.jorgejy.springboot.app.model.entity.Client;
import com.jorgejy.springboot.app.model.service.ClientService;
import com.jorgejy.springboot.app.model.service.UploadFileService;
import com.jorgejy.springboot.app.util.paginator.PageRender;


@Controller
@SessionAttributes("client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private MessageSource messageSource;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Secured("ROLE_USER")
	@GetMapping(value = "/upload/{filename:.+}")
	public ResponseEntity<Resource> showPicture(@PathVariable String filename) {
		Resource resource = null;
		try {
			resource = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	// one role hasRole('ROLE_ADMIN')
	// hasAnyRole('ROLE_ADMIN', 'ROLE_USER')
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/show/{id}")
	public String showClient(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Client client;
		if (id > 0) {
			// client = clientService.findOne(id);
			client = clientService.fetchByIdWithBills(id);
			if (client == null) {
				flash.addFlashAttribute("danger", "Client no exist.");
				return "redirect:/list";
			}
		} else {
			flash.addFlashAttribute("danger", "ID not bee 0.");
			return "redirect:/list";
		}

		model.put("client", client);
		model.put("title", "Details client: " + client.getName());

		return "show";
	}


	
	@RequestMapping(value = {"/list", "/"}, method = RequestMethod.GET)
	public String list(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, 
			HttpServletRequest httpServletRequest,
			Locale locale) {
		

		if(authentication != null) {
			logger.info("USER SIGN IN , USER NAME: ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null) {
			logger.info("STATIC  SecurityContextHolder.getContext().getAuthentication(): USER NAME: ".concat(auth.getName()));
		}
		
		if(hasRole("ROLE_ADMIN")) {
			logger.info("USER NAME ".concat(auth.getName()).concat(" YOU HAVE ACCESS!"));
		} else {
			logger.info("USER NAME ".concat(auth.getName()).concat(" ACCES DENIED"));
		}
		
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(httpServletRequest, "");
		
		if(securityContext.isUserInRole("ROLE_ADMIN")) {
			logger.info("USE SecurityContextHolderAwareRequestWrapper: USER NAME: ".concat(auth.getName()).concat(" YOU HAVE ACCESS"));
		} else {
			logger.info("USE SecurityContextHolderAwareRequestWrapper: USER NAME: ".concat(auth.getName()).concat(" ACCESS DENIED"));
		}

		if(httpServletRequest.isUserInRole("ROLE_ADMIN")) {
			logger.info("USE HttpServletRequest: USER NAME ".concat(auth.getName()).concat(" YOU HAVE ACCESS"));
		} else {
			logger.info("USE HttpServletRequest: USER NAME ".concat(auth.getName()).concat(" ACCESS DENIED"));
		}	
		
		
		
		Pageable pageable = PageRequest.of(page, 5);
		Page<Client> clients = clientService.findAll(pageable);
		PageRender<Client> pageRender = new PageRender<>("/list", clients);

		model.addAttribute("title", messageSource.getMessage("text.client.list.title", null, locale));
		model.addAttribute("clients", clients);
		model.addAttribute("page", pageRender);

		return "list";
	}
	// more roles validate example {"ROLE_ADMIN", "ROLE_USER"}.
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/form")
	public String create(Map<String, Object> model) {
		Client client = new Client();

		model.put("client", client);
		model.put("title", "Form client");

		return "form";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{id}")
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Client client = null;

		if (id > 0) {
			client = clientService.findOne(id);
			if (client == null) {
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

	// forever join @Valid Client client, BindingResult bindingResult MODEL and
	// binding
	// if want change name use @ModelAtribut in class model.
	@PostMapping(value = "/form")
	public String save(@Valid Client client, BindingResult bindingResult, Model model,
			@RequestParam("file") MultipartFile picture, RedirectAttributes flash, SessionStatus sessionStatus) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("title", "Form client");
			return "form";
		}

		if (!picture.isEmpty()) {

			if (client.getId() != null && client.getId() > 0 && client.getPicture() != null
					&& client.getPicture().length() > 0) {

				uploadFileService.delete(client.getPicture());
			}

			String uniqueNamePicture;
			try {
				uniqueNamePicture = uploadFileService.copy(picture);
				flash.addFlashAttribute("info", "Picture upload " + uniqueNamePicture + ".");
				client.setPicture(uniqueNamePicture);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		String messageFlash = (client.getId() != null) ? "Client updated!" : "Client created!";

		clientService.save(client);
		sessionStatus.setComplete();

		flash.addFlashAttribute("success", messageFlash);
		return "redirect:list";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Client client = clientService.findOne(id);

			clientService.delete(id);
			flash.addFlashAttribute("success", "Client deleted!");

			if (uploadFileService.delete(client.getPicture())) {
				flash.addFlashAttribute("info", "Picture: " + client.getPicture() + " delete!");
			}
		}
		return "redirect:/list";
	}
	
	public boolean hasRole(String roleName) {
		
		SecurityContext securityContext = SecurityContextHolder.getContext(); 
		
		if(securityContext == null) {
			return false;
		}
		
		Authentication authentication = securityContext.getAuthentication();
		if(authentication == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		return authorities.contains(new SimpleGrantedAuthority(roleName));
		
		
		
		/*for(GrantedAuthority authority : authorities) {
			if(roleName.equals(authority.getAuthority())) {
				logger.info("User : ".concat(authentication.getName()).concat(" role is:  ").concat(roleName));
				return true;
			}
		}
		
		return false;
		
		*/
		
	}
}
