package com.jorgejy.springboot.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocaleController {
	
	@GetMapping("/locale")
	public String locale(HttpServletRequest httpServletRequest) {
		String lastUrl = httpServletRequest.getHeader("referer");
		return "redirect:".concat(lastUrl);
	}
}
