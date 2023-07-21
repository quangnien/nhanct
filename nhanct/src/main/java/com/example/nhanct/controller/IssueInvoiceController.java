package com.example.nhanct.controller;

import com.example.nhanct.config.PDFExporter;
import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.entity.InvoiceDetailEntity;
import com.example.nhanct.entity.InvoiceEntity;
import com.example.nhanct.entity.InvoiceTypeEntity;
import com.example.nhanct.entity.IssueInvoiceEntity;
import com.example.nhanct.service.InvoiceTypeService;
import com.example.nhanct.service.IssueInvoiceService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	@GetMapping("report")
	public void exportPdfForIssueInvoice(HttpServletResponse response) throws IOException, DocumentException {

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";

		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		List<IssueInvoiceEntity> issueInvoiceEntityList = issueInvoiceService.findAllOrderByDate();

		List<String> listHeader = new ArrayList<>();
		listHeader.add("CONG TY CO PHAN NOI THAT HOA PHAT");
		listHeader.add("Ma so thue:  0311942282");
		listHeader.add("Dia chi: 76 duong so 2, khu pho 5, Phuong Binh Hung Hoa B, Quan Binh Tan, TP Ho Chi Minh, Viet Nam");
		listHeader.add("So dien thoai: 0938024027");
		listHeader.add("                           ");
		listHeader.add("Loai hoa don: Tat ca");
		listHeader.add("Thoi gian: ");

		List<String> listHeaderContent = new ArrayList<>();
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add(issueInvoiceEntityList.get(0).getDateOfRegistration() + " - " + issueInvoiceEntityList.get(issueInvoiceEntityList.size()-1).getDateOfRegistration());

		List<String> listTableHeader1 = new ArrayList<>();
		listTableHeader1.add("STT");
		listTableHeader1.add("Ten loai hoa don");
		listTableHeader1.add("Ky hieu");
		listTableHeader1.add("Tong so");
		listTableHeader1.add("Tu so");
		listTableHeader1.add("Toi so");
		listTableHeader1.add("Da su dung");
		listTableHeader1.add("Con lai");

		List<List<String>> listDataTable1 = new ArrayList<>();
		for(int i = 0; i < issueInvoiceEntityList.size(); i++){
			List<String> listDataTableItem = new ArrayList<>();
			listDataTableItem.add(issueInvoiceEntityList.get(i).getInvoiceType().getNameOfInvoiceType());
			listDataTableItem.add(issueInvoiceEntityList.get(i).getSymbol());
			listDataTableItem.add(String.valueOf(issueInvoiceEntityList.get(i).getQuantity()));
			listDataTableItem.add(String.valueOf(issueInvoiceEntityList.get(i).getFromNumber()));
			listDataTableItem.add(String.valueOf(issueInvoiceEntityList.get(i).getToNumber()));
			listDataTableItem.add(String.valueOf(issueInvoiceEntityList.get(i).getCurrentInvoiceNumber()));
			listDataTableItem.add(String.valueOf(issueInvoiceEntityList.get(i).getQuantity() - issueInvoiceEntityList.get(i).getCurrentInvoiceNumber()));

			listDataTable1.add(listDataTableItem);
		}

		List<String> listSign = new ArrayList<>();
		listSign.add("Nguoi lap phieu");
		listSign.add("Nguoi dai dien");

		PDFExporter exporter = PDFExporter.builder().titleHeader("BAO CAO TINH HINH SU DUNG HOA DON").listHeader(listHeader)
				.listHeaderContent(listHeaderContent).colNum1(8).listDataTable1(listDataTable1).listTableHeader1(listTableHeader1).listSign(listSign)
				.colNum2(0).listTableHeader2(null).listDataTable2(null).build();

		exporter.export(response);
	}

}