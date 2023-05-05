package com.brainstation23.erp.controller.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping
public class HomeController {

	@GetMapping
	public String home() {
		return "home";
	}

//	@GetMapping("/dashboard")
//	public String dashboard() {
//		return "index";
//	}



	@GetMapping("/dashboard")
	public String afterLoginAdminPanel() {
		return "index";
	}
//
//	@GetMapping("/dashboard")
//	public ModelAndView dashboard(Principal principal) {
//		return new ModelAndView("index");
//	}

	@GetMapping("/login")
	@Secured("!isAuthenticated()")
	public String viewLoginPage() {
		return "login";
	}

}