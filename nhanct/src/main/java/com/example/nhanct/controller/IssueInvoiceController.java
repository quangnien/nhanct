package com.example.nhanct.controller;

import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.entity.InvoiceTypeEntity;
import com.example.nhanct.entity.IssueInvoiceEntity;
import com.example.nhanct.service.InvoiceTypeService;
import com.example.nhanct.service.IssueInvoiceService;
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
@RequestMapping("admin/issue-invoice")
public class IssueInvoiceController extends FunctionCommon {

	@Autowired
	private IssueInvoiceService issueInvoiceService;
    @Autowired
	private InvoiceTypeService invoiceTypeService;
	@Value("${adress.406}")
	private String page406;
	@Value("${adress.404}")
	private String adress404;

    @GetMapping("")
	public String index(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.ISSUE_INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		return listPage(model, 1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
		if(hasRoleAuthor(MenuConstant.ISSUE_INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		Page<IssueInvoiceEntity> page = issueInvoiceService.findAll(currentPage);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<IssueInvoiceEntity> listIssueInvoice = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("listIssueInvoice", listIssueInvoice);
		return "issue-invoice/index";
	}

	@GetMapping("search")
	public String index(ModelMap model, @Param("keyword") String keyword) {
		if(hasRoleAuthor(MenuConstant.ISSUE_INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		List<IssueInvoiceEntity> listIssueInvoice = issueInvoiceService.findAllByKeyword(keyword);
		model.addAttribute("listIssueInvoice", listIssueInvoice);
		model.addAttribute("keyword", keyword);
		return "issue-invoice/search";
	}

	@GetMapping("add")
	public String add(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.ISSUE_INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		List<InvoiceTypeEntity> listInvoiceType = invoiceTypeService.findAll();
		if(listInvoiceType.isEmpty()) return "redirect:"+page406 + "?id=invoiceType";

		model.addAttribute("listInvoiceType", listInvoiceType);
		model.addAttribute("issueInvoice", new IssueInvoiceEntity());
		return "issue-invoice/add";
	}

	@PostMapping("add")
	public String add(@Valid @ModelAttribute("issueInvoice") IssueInvoiceEntity issueInvoice, BindingResult errors, ModelMap model) {

		if(issueInvoice.getFromNumber() > issueInvoice.getToNumber()) {
			errors.rejectValue("fromNumber", "issueInvoice", "ToNumber must be larger than FromNumber");
		}

		if(errors.hasErrors()) {
			model.addAttribute("listInvoiceType", invoiceTypeService.findAll());
			menuListRole(model);
			return "issue-invoice/add";
		}

		issueInvoiceService.add(issueInvoice);
		return "redirect:/admin/issue-invoice";
	}

	@GetMapping("edit")
	public String edit(@RequestParam("id") int id, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.ISSUE_INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		try {
			model.addAttribute("listInvoiceType", invoiceTypeService.findAll());
			model.addAttribute("issueInvoice", issueInvoiceService.getById(id));
			return "issue-invoice/edit";
		} catch (Exception e) {
			return "redirect:"+adress404;
		}

	}

	@PostMapping("edit")
	public String edit(@Valid @ModelAttribute("issueInvoice") IssueInvoiceEntity issueInvoice, BindingResult errors, ModelMap model) {

		if(errors.hasErrors()) {
			menuListRole(model);
			model.addAttribute("listInvoiceType", invoiceTypeService.findAll());
			return "issue-invoice/edit";
		}
		issueInvoiceService.update(issueInvoice);
		return "redirect:/admin/issue-invoice";
	}

	@GetMapping("confirm-delete")
	public String delete(@RequestParam("id") int id, ModelMap model,
			@RequestParam(value = "message", required = false) String message) {
		if(hasRoleAuthor(MenuConstant.ISSUE_INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		try {
			IssueInvoiceEntity issueInvoice = issueInvoiceService.getById(id);
			model.addAttribute("issueInvoice", issueInvoice);
			model.addAttribute("message", message);
			return "issue-invoice/confirm-delete";
		} catch (Exception e) {
			return "redirect:"+adress404;
		}
	}

	@GetMapping("confirm-delete-post")
	public String confirmDeletePost(@RequestParam("id") int id, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.ISSUE_INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		String message = "";
		try {
			issueInvoiceService.delete(id);
			return "redirect:/admin/issue-invoice";

		} catch (DataIntegrityViolationException e) {
			message = "F";
			return "redirect:/admin/issue-invoice/confirm-delete?id=" + id + "&&message="+message;
		} catch(Exception e) {
			message = "F";
			return "redirect:/admin/issue-invoice/confirm-delete?id=" + id + "&&message="+message;
		}
	}
}