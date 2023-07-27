package com.example.nhanct.controller;

import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.entity.InvoiceDetailEntity;
import com.example.nhanct.entity.InvoiceTypeEntity;
import com.example.nhanct.entity.KindOfTaxEntity;
import com.example.nhanct.service.InvoiceDetailService;
import com.example.nhanct.service.InvoiceTypeService;
import com.example.nhanct.service.KindOfTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin/invoice/detail2")
public class InvoiceDetail2Controller extends FunctionCommon {

	@Autowired
	private InvoiceDetailService invoiceDetailService;
    @Autowired
	private InvoiceTypeService invoiceTypeService;
	@Autowired
	private KindOfTaxService kindOfTaxService;
	@Value("${adress.404}")
	private String adress404;

    @GetMapping("")
	public String index(ModelMap model, @RequestParam("invoiceId") int invoiceId) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		return listPage(model, 1, invoiceId);
	}

	@GetMapping("/page/{pageNumber}")
	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage, @RequestParam("invoiceId") int invoiceId) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		Page<InvoiceDetailEntity> page = invoiceDetailService.findAll(currentPage, invoiceId);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<InvoiceDetailEntity> listInvoiceDetail = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("invoiceId", invoiceId);

		model.addAttribute("listInvoiceDetail", listInvoiceDetail);
		return "invoice/detail2/index";
	}

	@GetMapping("search")
	public String index(ModelMap model, @Param("keyword") String keyword, @RequestParam("invoiceId") int invoiceId) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		List<InvoiceDetailEntity> listInvoiceDetail = invoiceDetailService.findAllByKeyword(keyword);
		model.addAttribute("listInvoiceDetail", listInvoiceDetail);
		model.addAttribute("keyword", keyword);
		model.addAttribute("invoiceId", invoiceId);
		return "invoice/detail2/search";
	}
}