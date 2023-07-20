package com.example.nhanct.controller;

import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.entity.InvoiceTypeEntity;
import com.example.nhanct.entity.InvoiceDetailEntity;
import com.example.nhanct.entity.KindOfTaxEntity;
import com.example.nhanct.service.InvoiceTypeService;
import com.example.nhanct.service.InvoiceDetailService;
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
@RequestMapping("admin/invoice/detail")
public class InvoiceDetailController extends FunctionCommon {

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
		return "invoice/detail/index";
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
		return "invoice/detail/search";
	}

	@GetMapping("add")
	public String add(ModelMap model, @RequestParam("invoiceId") int invoiceId) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		List<InvoiceTypeEntity> listInvoiceType = invoiceTypeService.findAll();
		List<KindOfTaxEntity> listKindOfTax = kindOfTaxService.findAll();
//		if(listInvoiceType.isEmpty()) return "redirect:"+page406 + "?id=invoiceType";

		model.addAttribute("listInvoiceType", listInvoiceType);
		model.addAttribute("listKindOfTax", listKindOfTax);
		model.addAttribute("invoiceDetail", new InvoiceDetailEntity());
		model.addAttribute("invoiceId", invoiceId);
		return "invoice/detail/add";
	}

	@PostMapping("add")
	public String add(@Valid @ModelAttribute("invoiceDetail") InvoiceDetailEntity invoiceDetail, @RequestParam("invoiceId") int invoiceId,
					  BindingResult errors, ModelMap model) {

//		if(invoiceDetail.getFromNumber() > invoiceDetail.getToNumber()) {
//			errors.rejectValue("fromNumber", "invoiceDetail", "ToNumber must be larger than FromNumber");
//		}

		if(errors.hasErrors()) {
			List<InvoiceTypeEntity> listInvoiceType = invoiceTypeService.findAll();
			List<KindOfTaxEntity> listKindOfTax = kindOfTaxService.findAll();
			model.addAttribute("listInvoiceType", listInvoiceType);
			model.addAttribute("listKindOfTax", listKindOfTax);
			model.addAttribute("invoiceId", invoiceId);
			menuListRole(model);
			return "invoice/detail/add";
		}

		invoiceDetailService.add(invoiceDetail);
		return "redirect:/admin/invoice/detail?invoiceId=" + invoiceId;
	}

	@GetMapping("edit")
	public String edit(@RequestParam("id") int id, ModelMap model, @RequestParam("invoiceId") int invoiceId) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		try {
			List<InvoiceTypeEntity> listInvoiceType = invoiceTypeService.findAll();
			List<KindOfTaxEntity> listKindOfTax = kindOfTaxService.findAll();
			model.addAttribute("listInvoiceType", listInvoiceType);
			model.addAttribute("listKindOfTax", listKindOfTax);
			model.addAttribute("invoiceId", invoiceId);

			model.addAttribute("invoiceDetail", invoiceDetailService.getById(id));
			return "invoice/detail/edit";
		} catch (Exception e) {
			return "redirect:"+adress404;
		}

	}

	@PostMapping("edit")
	public String edit(@Valid @ModelAttribute("invoiceDetail") InvoiceDetailEntity invoiceDetail, @RequestParam("invoiceId") int invoiceId,
		BindingResult errors, ModelMap model) {

		if(errors.hasErrors()) {
			menuListRole(model);

			List<InvoiceTypeEntity> listInvoiceType = invoiceTypeService.findAll();
			List<KindOfTaxEntity> listKindOfTax = kindOfTaxService.findAll();
			model.addAttribute("listInvoiceType", listInvoiceType);
			model.addAttribute("listKindOfTax", listKindOfTax);
			model.addAttribute("invoiceId", invoiceId);
			return "invoice/detail/edit";
		}
		invoiceDetailService.update(invoiceDetail);
		return "redirect:/admin/invoice/detail?invoiceId=" + invoiceId;
	}

	@GetMapping("confirm-delete")
	public String delete(@RequestParam("id") int id, @RequestParam("invoiceId") int invoiceId, ModelMap model,
			@RequestParam(value = "message", required = false) String message) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		try {
			InvoiceDetailEntity invoiceDetail = invoiceDetailService.getById(id);
			model.addAttribute("invoiceDetail", invoiceDetail);
			model.addAttribute("message", message);
			model.addAttribute("invoiceId", invoiceId);
			return "invoice/detail/confirm-delete";
		} catch (Exception e) {
			return "redirect:"+adress404;
		}
	}

	@GetMapping("confirm-delete-post")
	public String confirmDeletePost(@RequestParam("id") int id, ModelMap model, @RequestParam("invoiceId") int invoiceId){
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		String message = "";
		try {
			invoiceDetailService.delete(id);
			return "redirect:/admin/invoice/detail?invoiceId=" + invoiceId;
		} catch (DataIntegrityViolationException e) {
			message = "F";
			return "redirect:/admin/invoice/detail/confirm-delete?id=" + id + "&&message="+message;
		} catch(Exception e) {
			message = "F";
			return "redirect:/admin/invoice/detail/confirm-delete?id=" + id + "&&message="+message;
		}
	}
}