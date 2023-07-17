//package com.example.nhanct.controller;
//
//import com.example.nhanct.consts.MenuConstant;
//import com.example.nhanct.entity.BrandEntity;
//import com.example.nhanct.service.BrandService;
//import com.example.nhanct.utils.SecurityUtils;
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("admin/brand")
//public class BrandController extends FunctionCommon {
//
//	@Autowired
//	private BrandService brandService;
//	@Value("${adress.404}")
//	private String adress404;
//
//	@GetMapping("")
//	public String index(ModelMap model) {
//		if(hasRoleAuthor(MenuConstant.BRAND) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		return listPage(model,1);
//	}
//
//	@GetMapping("/page/{pageNumber}")
//	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
//		if(hasRoleAuthor(MenuConstant.BRAND) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		Page<BrandEntity> page = brandService.findAll(currentPage);
//		long totalItems = page.getTotalElements();
//		int totalPages = page.getTotalPages();
//
//		List<BrandEntity> listBrand = page.getContent();
//
//		model.addAttribute("currentPage", currentPage);
//		model.addAttribute("totalItems", totalItems);
//		model.addAttribute("totalPages", totalPages);
//
//		model.addAttribute("listBrand", listBrand);
//		return "brand/index";
//	}
//
//	@GetMapping("search")
//	public String index(ModelMap model, @RequestParam("keyword") String keyword) {
//		if(hasRoleAuthor(MenuConstant.BRAND) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		List<BrandEntity> listBrand = brandService.findAll(keyword);
//		model.addAttribute("listBrand", listBrand);
//		model.addAttribute("keyword", keyword);
//		return "brand/search";
//	}
//
//	@GetMapping("add")
//	public String add(ModelMap model) {
//		if(hasRoleAuthor(MenuConstant.BRAND) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//
//		model.addAttribute("brand", new BrandEntity());
//		return "brand/add";
//	}
//
//	@RequestMapping(value = "add", method = RequestMethod.POST)
//	public String add(@Valid @ModelAttribute("brand") BrandEntity brand, BindingResult errors,
//					  ModelMap model) {
//		if(errors.hasErrors() || duplicateEmail(brand, errors)
//		|| duplicateBrandName(brand, errors)) {
//			menuListRole(model);
//			return "brand/add";
//		}
//		if(brandService.isExceptionEmail(brand)) {
//			errors.rejectValue("email", "brand", "Enter the correct format email!");
//			menuListRole(model);
//			return "brand/add";
//		}
//
//		brandService.add(brand);
//		return "redirect:/admin/brand";
//
//	}
//
//	@RequestMapping(value = "edit", method = RequestMethod.GET)
//	public String edit(@RequestParam("id") int id , ModelMap model) {
//		if(hasRoleAuthor(MenuConstant.BRAND) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		try {
//			BrandEntity brand = brandService.getById(id);
//			model.addAttribute("brand", brand);
//			return "brand/edit";
//		} catch (Exception e) {
//			return "redirect:"+adress404;
//		}
//	}
//
//	@RequestMapping(value = "edit", method = RequestMethod.POST)
//	public String edit(@Valid @ModelAttribute("brand") BrandEntity brand, BindingResult errors, ModelMap model) {
//		if(errors.hasErrors() || duplicateEmail(brand, errors)
//				|| duplicateBrandName(brand, errors)) {
//			menuListRole(model);
//			return "brand/edit";
//		}
//		brandService.update(brand);
//		if(brandService.isExceptionEmail(brand)) {
//			errors.rejectValue("email", "brand", "Enter the correct format email");
//			return "brand/edit";
//		}
//		return "redirect:/admin/brand";
//	}
//
//	@GetMapping("confirm-delete")
//	public String delete(@RequestParam("id") int id, ModelMap model,
//			@RequestParam(value = "message", required = false) String message) {
//		if(hasRoleAuthor(MenuConstant.BRAND) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		try {
//			BrandEntity brand = brandService.getById(id);
//			model.addAttribute("brand", brand);
//			model.addAttribute("message", message);
//			return "brand/confirm-delete";
//		} catch (Exception e) {
//			return "redirect:"+adress404;
//		}
//	}
//
//	@GetMapping("confirm-delete-post")
//	public String delete1(@RequestParam("id") int id, ModelMap model) {
//		if(hasRoleAuthor(MenuConstant.BRAND) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		String message = "";
//		try {
//			brandService.delete(id);
//			return "redirect:/admin/brand";
//
//
//		} catch (DataIntegrityViolationException e) { // lỗi ràng buộc
//			message = "F";
//			System.out.println("/admin/brand/confirm-delete?id=" + id + " &&message= "+message);
//			return "redirect:/admin/brand/confirm-delete?id=" + id + "&&message="+message;
//		} catch(Exception e) { // lỗi chung
//			message = "F";
//			System.out.println("/admin/brand/confirm-delete?id=" + id + " &&message= "+message);
//			return "redirect:/admin/brand/confirm-delete?id=" + id + "&&message="+message;
//		}
//	}
//
//	/*------------------------------PRIVATE METHOD------------------------------------------*/
//	public boolean duplicateEmail(BrandEntity brand, BindingResult errors) {
//		BrandEntity entity = brandService.findByEmail(brand.getEmail().trim());
//		if(entity == null || entity.getId() == brand.getId()) return false;
//		else {
//			errors.rejectValue("email", "brand", "This email was exists!");
//			return true;
//		}
//	}
//
//	public boolean duplicateBrandName(BrandEntity brand, BindingResult errors) {
//		BrandEntity entity = brandService.findByBrandName(brand.getBrandName().trim());
//		if(entity == null || entity.getId() == brand.getId()) return false;
//		else {
//			errors.rejectValue("brandName", "brand", "This brandName was exists!");
//			return true;
//		}
//	}
//
//}
