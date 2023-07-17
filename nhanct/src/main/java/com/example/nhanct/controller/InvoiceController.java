//package com.example.nhanct.controller;
//
//import com.example.nhanct.dto.StatusOfInvoiceDto;
//import com.example.nhanct.entity.InvoiceEntity;
//import com.example.nhanct.service.InvoiceService;
//import com.example.nhanct.utils.SecurityUtils;
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@RequestMapping("admin/invoice")
//public class InvoiceController extends FunctionCommon {
//
//	@Autowired
//	private InvoiceService invoiceService;
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
//		return listPage(model,1);
//	}
//
//	@GetMapping("/page/{pageNumber}")
//	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		Page<InvoiceEntity> page = invoiceService.findAll(currentPage);
//		long totalItems = page.getTotalElements();
//		int totalPages = page.getTotalPages();
//
//		List<InvoiceEntity> listinvoice = page.getContent();
//
//		model.addAttribute("currentPage", currentPage);
//		model.addAttribute("totalItems", totalItems);
//		model.addAttribute("totalPages", totalPages);
//
//		model.addAttribute("invoices", listinvoice);
//		return "invoice/index";
//	}
//
//	@RequestMapping(value = "edit", method = RequestMethod.GET)
//	public String edit(@RequestParam("id") int id , ModelMap model) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		InvoiceEntity invoice =  invoiceService.getById(id);
//
//		/* trạng thái đơn hàng ENUM */
//		model.addAttribute("invoice", invoice);
//		List<StatusOfInvoiceDto> statuss = new ArrayList<>();
//		List<String> statusStrings = new ArrayList<>();
//		statusStrings.add("Chờ xác nhận");
//		statusStrings.add("Đang giao");
//		statusStrings.add("Chờ lấy hàng");
//		statusStrings.add("Đã giao hàng");
//		statusStrings.add("Customers request to cancel orders");
//		statusStrings.add("Cancel đơn hàng");
//
//		/* NOTE: ko nên get thông qua object khóa ngoại, vì khi update sẽ bị thiếu thông tin của object khóa ngoại đó */
//		String customerName = invoice.getCustomer().getCustomerName();
//		model.addAttribute("customerName", customerName);
//
//		for (String statusString : statusStrings) {
//			StatusOfInvoiceDto item = new StatusOfInvoiceDto();
//			item.setStatus(statusString);
//			statuss.add(item);
//		}
//		model.addAttribute("statuss", statuss);
//		return "invoice/edit";
//	}
//
//	@RequestMapping(value = "edit", method = RequestMethod.POST)
//	public String edit(@Valid @ModelAttribute("invoice") InvoiceEntity invoice, BindingResult errors
//			, ModelMap model, @RequestParam("id") int id) {
//		if(errors.hasErrors()) {
//			menuListRole(model);
//			return "invoice/edit";
//		}
//
//		/* if (tinhtranginvoice == 'huydonhang' -> read only */
//		if(invoice.getStatus().equals("Cancel đơn hàng")){
//			invoice.setFlagCancel(1);
//		}
//		invoiceService.update(invoice);
//		return "redirect:/admin/invoice";
//	}
//
//	@GetMapping("search") // trạng thái
//	public String search(ModelMap model, @RequestParam("keyword") String keyword) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		List<InvoiceEntity> listinvoice = invoiceService.findAll(keyword);
//		model.addAttribute("listinvoice", listinvoice);
//		model.addAttribute("keyword", keyword);
//		return "invoice/search";
//	}
//
//	/*---------------- begin AUTHOR ------------*/
//	private boolean hasRoleAuthor () {
//		Object[] tg = SecurityUtils.getPrincipal().getAuthorities().toArray();
//		for (Object object : tg) {
//			if (object.toString().equals("ROLE_HOADON")) {
//				return true;
//			}
//		}
//		return false;
//	}
//	/*---------------- end AUTHOR ------------*/
//
//}
