package com.example.nhanct.controller;

import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.entity.ContactEntity;
import com.example.nhanct.service.ContactService;
import com.example.nhanct.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/contact")
public class ContactController extends FunctionCommon {

	@Autowired
	private ContactService contactService;
	@Autowired
	SecurityUtils myUser;
	@Value("${adress.404}")
	private String adress404;

	@GetMapping("")
	public String index(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.CONTACT) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		
		List<ContactEntity> listContact = contactService.findAll();
		model.addAttribute("listContact", listContact);
		return "contact/index";
	}

	@GetMapping("confirm-delete")
	public String delete(@RequestParam("id") int id, ModelMap model,
			@RequestParam(value = "message", required = false) String message) {
		if(hasRoleAuthor(MenuConstant.CONTACT) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		try {
			ContactEntity contact = contactService.getById(id);
			model.addAttribute("contact", contact);
			model.addAttribute("message", message);
			return "contact/confirm-delete";
		} catch (Exception e) {
			return "redirect:"+adress404;
		}
	}
	
	@GetMapping("confirm-delete-post")
	public String delete1(@RequestParam("id") int id, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.CONTACT) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		String message = "";
		try {
			contactService.delete(id);
			return "redirect:/admin/contact";
		} catch (DataIntegrityViolationException e) {
			message = "F";
			System.out.println("/admin/contact/confirm-delete?id=" + id + " &&message= "+message);
			return "redirect:/admin/contact/confirm-delete?id=" + id + "&&message="+message;
		} catch(Exception e) {
			message = "F";
			System.out.println("/admin/contact/confirm-delete?id=" + id + " &&message= "+message);
			return "redirect:/admin/contact/confirm-delete?id=" + id + "&&message="+message;
		}
	}

}
