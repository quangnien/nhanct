//package com.example.nhanct.controller;
//
//import com.example.nhanct.entity.SaleEntity;
//import com.example.nhanct.service.SaleService;
//import com.example.nhanct.utils.SecurityUtils;
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.data.domain.Page;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("admin/sale")
//public class SaleController extends FunctionCommon {
//
//	@Autowired
//	private SaleService saleService;
//
//	@Value("${adress.406}")
//	private String page406;
//
//	@Value("${adress.404}")
//	private String adress404;
//
//	@Autowired
//	SecurityUtils myUser;
//
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	public String index(ModelMap model) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		return listPage(model,1);
//	}
//
//	@GetMapping("/page/{pageNumber}")
//	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		Page<SaleEntity> page = saleService.findAll(currentPage);
//		long totalItems = page.getTotalElements();
//		int totalPages = page.getTotalPages();
//
//		List<SaleEntity> listsale = page.getContent();
//
//		model.addAttribute("currentPage", currentPage);
//		model.addAttribute("totalItems", totalItems);
//		model.addAttribute("totalPages", totalPages);
//
//		model.addAttribute("listSale", listsale);
//
//		return "sale/index";
//	}
//
//	@RequestMapping(value = "search", method = RequestMethod.GET)
//	public String index(ModelMap model, @Param("keyword") String keyword) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		List<SaleEntity> listsale = saleService.findAll(keyword);
//		model.addAttribute("listsale", listsale);
//		model.addAttribute("keyword", keyword);
//		return "sale/search";
//	}
//
//	@RequestMapping(value = "add", method = RequestMethod.GET)
//	public String add(ModelMap model, @RequestParam(value = "message", required = false) String message) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		model.addAttribute("sale", new SaleEntity());
//		model.addAttribute("message", message);
//		return "sale/add";
//	}
//
//	@RequestMapping(value = "add", method = RequestMethod.POST)
//	public String add(@Valid @ModelAttribute("sale") SaleEntity sale, BindingResult errors,
//					  ModelMap model) {
//		if(errors.hasErrors()) {
//			menuListRole(model);
//			return "sale/add";
//		}
//		String message = "";
//		if (saleService.isException(sale)) {
//			message = "F";
//			return "redirect:/admin/sale/add?message="+message;
//		} else {
//			if (saleService.add(sale)) {
//				message = "T";
//			} else {
//				message = "F";
//				return "redirect:/admin/sale/add?message="+message;
//			}
//		}
////		saleService.add(sale);
//		return "redirect:/admin/sale";
////		return "redirect:/admin/sale/add?message="+message;
////		return "redirect:/admin/editFlight?id=" + flight.getId() + "&&message=" + message;
//	}
//
//	@RequestMapping(value = "edit", method = RequestMethod.GET)
//	public String edit(@RequestParam("id") int id , ModelMap model,
//			@RequestParam(value = "message", required = false) String message) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		try {
//			SaleEntity sale = saleService.getById(id);
//			model.addAttribute("sale", sale);
//			model.addAttribute("message", message);
//			return "sale/edit";
//		} catch (Exception e) {
//			return "redirect:"+adress404;
//		}
//	}
//
//	@RequestMapping(value = "edit", method = RequestMethod.POST)
//	public String edit(@Valid @ModelAttribute("sale") SaleEntity sale, BindingResult errors, ModelMap model) {
//		if(errors.hasErrors()) {
//			menuListRole(model);
//			return "sale/edit";
//		}
//		String message = "";
//		if (saleService.isException(sale)) {
//			message = "F";
//			return "redirect:/admin/sale/edit?id=" + sale.getId() + "&&message="+message;
//		} else {
//			if (saleService.update(sale)) {
//				message = "T";
//			} else {
//				message = "F";
//				return "redirect:/admin/sale/edit?id=" + sale.getId() + "&&message="+message;
//			}
//		}
//		//return "redirect:/admin/sale/edit?id=" + sale.getId() + "&&message="+message;
////		saleService.update(sale);
//		return "redirect:/admin/sale";
//	}
//
////	@RequestMapping(value="delete" , method = RequestMethod.GET)
////	public String delete(@RequestParam("id") int id, ModelMap model) {
////		try {
////			saleService.delete(id);
////			return "redirect:/admin/sale";
////		} catch (DataIntegrityViolationException e) {
////			model.addAttribute("message", "F");
////			index(model);
////			return listPage(model, 1);
////		} catch(Exception e) {
////			model.addAttribute("message", "F");
////			index(model);
////			return listPage(model, 1);
////		}
////	}
//
//	@GetMapping("confirm-delete")
//	public String delete(@RequestParam("id") int id, ModelMap model,
//			@RequestParam(value = "message", required = false) String message) {
//		menuListRole(model);
//		try {
//			SaleEntity sale = saleService.getById(id);
//			model.addAttribute("sale", sale);
//			model.addAttribute("message", message);
//			return "sale/confirm-delete";
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
//			saleService.delete(id);
//			return "redirect:/admin/sale";
//		} catch (DataIntegrityViolationException e) {
//			message = "F";
//			System.out.println("/admin/sale/confirm-delete?id=" + id + " &&message= "+message);
//			return "redirect:/admin/sale/confirm-delete?id=" + id + "&&message="+message;
//		} catch(Exception e) {
//			message = "F";
//			System.out.println("/admin/sale/confirm-delete?id=" + id + " &&message= "+message);
//			return "redirect:/admin/sale/confirm-delete?id=" + id + "&&message="+message;
//		}
//	}
//
//	/*---------------- begin AUTHOR ------------*/
//	private boolean hasRoleAuthor () {
//		Object[] tg = myUser.getPrincipal().getAuthorities().toArray();
//		for (Object object : tg) {
//			if (object.toString().equals("ROLE_KHUYENMAI")) {
//				return true;
//			}
//		}
//		return false;
//	}
//	/*---------------- end AUTHOR ------------*/
//}
