package com.example.nhanct.controller;

import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.entity.KindOfTaxEntity;
import com.example.nhanct.service.KindOfTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/kind-of-tax")
public class KindOfTaxController extends FunctionCommon {

	@Autowired
	private KindOfTaxService invoiceTypeService;

	@GetMapping("")
	public String index(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.KIND_OF_TAX) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		return listPage(model,1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
		if(hasRoleAuthor(MenuConstant.KIND_OF_TAX) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		Page<KindOfTaxEntity> page = invoiceTypeService.findAll(currentPage);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<KindOfTaxEntity> listKindOfTax = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("listKindOfTax", listKindOfTax);
		return "kind-of-tax/index";
	}

}