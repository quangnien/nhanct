package com.example.nhanct.controller;

import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.entity.WarehouseEntity;
import com.example.nhanct.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/warehouse")
public class WarehouseController extends FunctionCommon {

	@Autowired
	private WarehouseService invoiceTypeService;

	@GetMapping("")
	public String index(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.WAREHOUSE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		return listPage(model,1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
		if(hasRoleAuthor(MenuConstant.WAREHOUSE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		Page<WarehouseEntity> page = invoiceTypeService.findAll(currentPage);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<WarehouseEntity> listWarehouse = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("listWarehouse", listWarehouse);
		return "warehouse/index";
	}

}