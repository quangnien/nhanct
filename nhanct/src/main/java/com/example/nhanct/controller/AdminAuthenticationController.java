package com.example.nhanct.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AdminAuthenticationController {
	
	@RequestMapping("/login/admin")
	public String login(@RequestParam(required = false) String message, final Model model) {
		 if (message != null && !message.isEmpty()) {
	        if (message.equals("logout")) {
	          model.addAttribute("message", "Logout!");
	        }
	        if (message.equals("error")) {
	          model.addAttribute("message", "Login Failed!");
	        }
	      }
		 
		 return "login/index";
	}
	
	@RequestMapping("/403")
	public String eror() {
		return "admin/403";
	}
	
	@GetMapping("/logout/admin")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null) {
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login/admin";
	}
	
	@RequestMapping("/admin/deny")
	public String deny() {
		return "deny/deny";
	}

}
	