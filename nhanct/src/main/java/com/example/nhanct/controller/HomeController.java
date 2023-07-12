package com.example.nhanct.controller;

import com.example.nhanct.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class HomeController extends FunctionCommon {

	@Autowired
	SecurityUtils myUser;
	
	@GetMapping("")
	public String home(ModelMap model) { 
		menuListRole(model);
		return "home/index";
	}
	
	@GetMapping("profile")
	public String index(ModelMap model) { 
		menuListRole(model);
		model.addAttribute("mess_diachi",myUser.getPrincipal().getAddress());
		model.addAttribute("mess_email",myUser.getPrincipal().getEmail() );
		model.addAttribute("mess_gioitinh",myUser.getPrincipal().getSex() );
		model.addAttribute("mess_hinhanh",myUser.getPrincipal().getImage() );
		model.addAttribute("mess_ngaysinh",myUser.getPrincipal().getDob() );
		model.addAttribute("mess_sodienthoai",myUser.getPrincipal().getPhone() );
		model.addAttribute("mess_tenuser",myUser.getPrincipal().getUserName() );
		model.addAttribute("mess_username",myUser.getPrincipal().getUsername() );
		model.addAttribute("mess_password",myUser.getPrincipal().getPassword() );
		model.addAttribute("mess_id",myUser.getPrincipal().getId());
		
		return "home/profile";
	}

}
