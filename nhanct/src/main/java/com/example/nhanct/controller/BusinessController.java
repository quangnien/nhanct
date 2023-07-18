package com.example.nhanct.controller;

import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.entity.BusinessEntity;
import com.example.nhanct.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/business")
public class BusinessController extends FunctionCommon {

	@Autowired
	private BusinessService invoiceTypeService;

	@GetMapping("")
	public String index(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.BUSINESS) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		return listPage(model,1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
		if(hasRoleAuthor(MenuConstant.BUSINESS) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		Page<BusinessEntity> page = invoiceTypeService.findAll(currentPage);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<BusinessEntity> listBusiness = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("listBusiness", listBusiness);
		return "business/index";
	}

}