//package com.example.nhanct.controller;
//
//import com.example.nhanct.entity.CustomerEntity;
//import com.example.nhanct.service.CustomerService;
//import com.example.nhanct.utils.SecurityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin/customer")
//public class CustomerController extends FunctionCommon {
//
//	@Value("${adress.404}")
//	private String adress404;
//
//	@Autowired
//	private CustomerService customerService;
//
//	@Autowired
//	SecurityUtils myUser;
//
//	@GetMapping("")
//	public String index(ModelMap model) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//
//		return listPage(model,1);
//	}
//
//	@GetMapping("/page/{pageNumber}")
//	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		Page<CustomerEntity> page = customerService.findAll(currentPage);
//		long totalItems =  page.getTotalElements();
//		int totalPages = page.getTotalPages();
//
//		List<CustomerEntity> listKhachHangs = page.getContent();
//
//		model.addAttribute("currentPage", currentPage);
//		model.addAttribute("totalItems", totalItems);
//		model.addAttribute("totalPages", totalPages);
//
//		model.addAttribute("customers", listKhachHangs);
//		return "customer/index";
//	}
//
//	@GetMapping("confirm-delete")
//	public String delete(@RequestParam("id") int id, ModelMap model,
//			@RequestParam(value = "message", required = false) String message) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		try {
//			CustomerEntity customer = customerService.getById(id);
//			model.addAttribute("customer", customer);
//			model.addAttribute("message", message);
//			return "customer/confirm-delete";
//		} catch (Exception e) {
//			return "redirect:"+adress404;
//		}
//	}
//
//	@GetMapping("confirm-delete-post")
//	public String delete1(@RequestParam("id") int id, ModelMap model) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		String message = "";
//		try {
//			customerService.delete(id);
//			return "redirect:/admin/customer";
//		} catch (DataIntegrityViolationException e) {
//			message = "F";
//			return "redirect:/admin/customer/confirm-delete?id=" + id + "&&message="+message;
//		} catch(Exception e) {
//			message = "F";
//			return "redirect:/admin/customer/confirm-delete?id=" + id + "&&message="+message;
//		}
//	}
//
//	@GetMapping("search")
//	public String index(ModelMap model, @RequestParam("keyword") String keyword) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		List<CustomerEntity> listcustomer = customerService.findAll(keyword);
//		model.addAttribute("listcustomer", listcustomer);
//		model.addAttribute("keyword", keyword);
//		return "customer/search";
//	}
//
//	/*---------------- begin AUTHOR ------------*/
//	private boolean hasRoleAuthor () {
//		Object[] tg = myUser.getPrincipal().getAuthorities().toArray();
//		for (Object object : tg) {
//			if (object.toString().equals("ROLE_TAIKHOANKHACHHANG")) {
//				return true;
//			}
//		}
//		return false;
//	}
//	/*---------------- end AUTHOR ------------*/
//
//}
