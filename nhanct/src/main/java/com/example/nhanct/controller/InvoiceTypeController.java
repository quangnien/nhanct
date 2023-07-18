package com.example.nhanct.controller;

import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.entity.InvoiceTypeEntity;
import com.example.nhanct.service.InvoiceTypeService;
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
@RequestMapping("admin/invoice-type")
public class InvoiceTypeController extends FunctionCommon {

	@Autowired
	private InvoiceTypeService invoiceTypeService;

	@Value("${adress.404}")
	private String adress404;

	@GetMapping("")
	public String index(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.INVOICE_TYPE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		return listPage(model,1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
		if(hasRoleAuthor(MenuConstant.INVOICE_TYPE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		Page<InvoiceTypeEntity> page = invoiceTypeService.findAll(currentPage);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<InvoiceTypeEntity> listInvoiceType = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("listInvoiceType", listInvoiceType);
		return "invoice-type/index";
	}

}