package com.example.nhanct.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StatusController {
	
	@Value("${path.error-406}")
	private String path406;

	@Value("${path.error-401}")
	private String path401;

	@Value("${path.error-403}")
	private String path403;

	@Value("${path.error-404}")
	private String path404;
	
	@RequestMapping(value = "${adress.406}", method = RequestMethod.GET)
	public String notAcceptable(@RequestParam String id, Model model) {
		if(id.isEmpty()) {
//			return path403;
			return null;
		}else{
			switch (id) {
			case "role":
				model.addAttribute("message", "Vui lòng thêm dữ liệu Vai Trò trước.");
				model.addAttribute("link", "/admin/role/add");
				break;
			case "category":
				model.addAttribute("message", "Vui lòng thêm dữ liệu ở Category trước.");
				model.addAttribute("link", "/admin/category/add");
				break;
			case "brand":
				model.addAttribute("message", "Vui lòng thêm dữ liệu ở Brand trước.");
				model.addAttribute("link", "/admin/brand/add");
				break;
			default:
				model.addAttribute("message", "Something Wrong.");
				model.addAttribute("link", "/admin");
				break;
			}
			return path406;
		}
	}
	
	@RequestMapping(value = "${adress.401}", method = RequestMethod.GET)
	public String unauthorized() {
		return path401;
	}

	@RequestMapping(value = "${adress.403}", method = RequestMethod.GET)
	public String forbidden() {
		return path403;
	}

}
