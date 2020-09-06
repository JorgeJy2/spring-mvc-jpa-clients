package com.jorgejy.springboot.app.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jorgejy.springboot.app.model.entity.Bill;
import com.jorgejy.springboot.app.model.entity.Client;
import com.jorgejy.springboot.app.model.entity.ItemBill;
import com.jorgejy.springboot.app.model.entity.Product;
import com.jorgejy.springboot.app.model.service.BillService;
import com.jorgejy.springboot.app.model.service.ClientService;
import com.jorgejy.springboot.app.model.service.ProductService;

@Controller
@RequestMapping("/bill")
@SessionAttributes("bill")
public class BillController {
	
	private final Logger loger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/form/{clientId}")
	public String create(@PathVariable(value = "clientId") Long clientId, Model model, RedirectAttributes flash) {
		Client client  = clientService.findOne(clientId);
		if(client == null) {
			flash.addFlashAttribute("danger", "client no exist.");
			return "redirect:/list";
		}
		
		Bill bill = new Bill();
		bill.setClient(client);
		
		model.addAttribute("bill", bill);
		model.addAttribute("title","Create bill to client "+client.getName() + " " +client.getFirstName());
		
		return "bill/form";
	}
	
	@PostMapping("/form")
	public String save(@Valid Bill bill,
			BindingResult result,
			Model model,
			@RequestParam(name="product_id[]", required = false) Long[] productsId,
			@RequestParam(name="quantity[]", required = false) Integer[] quantity, 
			RedirectAttributes flash,
			SessionStatus sessionStatus) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Create bill to client "+ bill.getClient().getName() + " "+ bill.getClient().getFirstName());
			return "bill/form";
		}
		
		if(productsId == null || productsId.length == 0) {
			model.addAttribute("title", "Create bill to client "+ bill.getClient().getName() + " "+ bill.getClient().getFirstName());
			model.addAttribute("danger", "Error products is required.");
			return "bill/form";
		}
		
		for(int i = 0 ;  i < productsId.length; i++ ) {
			Product product = productService.findProductById(productsId[i]);
			
			ItemBill itemBill = new ItemBill();
			itemBill.setQuantity(quantity[i]);
			itemBill.setProduct(product);
			
			bill.addItem(itemBill);
			
			loger.info("ID: "+ productsId[i] + ", quantity: "+ quantity[i]);
		}
		
		billService.save(bill);
		
		sessionStatus.setComplete();
		flash.addFlashAttribute("success", "bill create.");
		return "redirect:/show/" +bill.getClient().getId();
	}
	
	@GetMapping("/show/{id}")
	public String showBill(@PathVariable Long id,
			Model model, RedirectAttributes flash) {
		
		Bill bill = billService.findBillById(id);
		
		if(bill == null) {
			flash.addFlashAttribute("danger", "The bill no exist.");
			return "redirect:/list";
		}
		
		model.addAttribute("bill", bill);
		model.addAttribute("title","Bill : ".concat(bill.getDescription()));
		
		
		return "bill/show";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable  Long id, RedirectAttributes flash) {
		
		Bill bill = billService.findBillById(id);
		
		if(bill != null) {
			billService.delete(id);
			flash.addFlashAttribute("success", "Bill delete.");	
			return "redirect:/show/"+ bill.getClient().getId();
		}
		
		flash.addFlashAttribute("danger", "Bill no exist");
		
		return "redirect:/list";
		
	}
}
