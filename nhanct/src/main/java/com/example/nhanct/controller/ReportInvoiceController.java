package com.example.nhanct.controller;

import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.dto.ReportDto;
import com.example.nhanct.dto.Select2Dto;
import com.example.nhanct.entity.InvoiceEntity;
import com.example.nhanct.entity.IssueInvoiceEntity;
import com.example.nhanct.enumdef.StatusOfInvoiceEnum;
import com.example.nhanct.enumdef.StatusOfInvoiceTypeEnum;
import com.example.nhanct.enumdef.StatusOfKindOfTaxEnum;
import com.example.nhanct.service.InvoiceService;
import com.example.nhanct.service.InvoiceTypeService;
import com.example.nhanct.service.IssueInvoiceService;
import com.example.nhanct.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("admin/report")
public class ReportInvoiceController extends FunctionCommon {

	@Autowired
	private InvoiceTypeService invoiceTypeService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private IssueInvoiceService issueInvoiceService;
	@Autowired
	SecurityUtils myMenu;

	@GetMapping("invoice")
	public String reportInvoiceVAT(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.REPORT_INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		List<Select2Dto> statusList = StatusOfInvoiceEnum.getSelect2ComboList();
//		List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2ComboList();
		List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2VAT();
		List<Select2Dto> kindOfTaxList = StatusOfKindOfTaxEnum.getSelect2ComboList();

		model.addAttribute("statusList", statusList);
		model.addAttribute("invoiceTypeList", invoiceTypeList);
		model.addAttribute("kindOfTaxList", kindOfTaxList);
		model.addAttribute("report", new ReportDto());

		return "report/index-invoice";
	}

	@PostMapping("invoice")
	public String reportInvoiceVATPost(ModelMap model, @Valid @ModelAttribute("report") ReportDto report,
									 BindingResult errors) {
		if(hasRoleAuthor(MenuConstant.REPORT_INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		if(report.getFromDate() == null){
			errors.rejectValue("fromDate", "report", "FromDate is mandatory");
		}
		else if(report.getToDate() == null){
			errors.rejectValue("toDate", "report", "ToDate is mandatory");
		}
		else if(report.getToDate().before(report.getFromDate())){
			errors.rejectValue("fromDate", "report", "FromDate must be before ToDate");
		}

		if(errors.hasErrors()) {
			List<Select2Dto> statusList = StatusOfInvoiceEnum.getSelect2ComboList();
			List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2ComboList();
			List<Select2Dto> kindOfTaxList = StatusOfKindOfTaxEnum.getSelect2ComboList();

			model.addAttribute("statusList", statusList);
			model.addAttribute("invoiceTypeList", invoiceTypeList);
			model.addAttribute("kindOfTaxList", kindOfTaxList);
//			model.addAttribute("report", new ReportDto());
			menuListRole(model);
			return "report/index-invoice";
		}

		List<InvoiceEntity> reportResult = invoiceService.findAllReport(report);
		List<Select2Dto> statusList = StatusOfInvoiceEnum.getSelect2ComboList();
		List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2ComboList();
		List<Select2Dto> kindOfTaxList = StatusOfKindOfTaxEnum.getSelect2ComboList();

		model.addAttribute("statusList", statusList);
		model.addAttribute("invoiceTypeList", invoiceTypeList);
		model.addAttribute("kindOfTaxList", kindOfTaxList);
		model.addAttribute("report", report);
		model.addAttribute("reportResult", reportResult);

		return "report/index-invoice";
	}

	/*______________________________________________________*/

	@GetMapping("invoice-issue")
	public String reportInvoiceIssue(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.REPORT_INVOICE_WAREHOUSE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2ComboList();

		model.addAttribute("invoiceTypeList", invoiceTypeList);
		model.addAttribute("report", new ReportDto());

		return "report/index-invoice-issue";
	}

	@PostMapping("invoice-issue")
	public String reportInvoiceIssuePost(ModelMap model, @Valid @ModelAttribute("report") ReportDto report,
									BindingResult errors) {
		if(hasRoleAuthor(MenuConstant.REPORT_INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		if(report.getFromDate() == null){
			errors.rejectValue("fromDate", "report", "FromDate is mandatory");
		}
		else if(report.getToDate() == null){
			errors.rejectValue("toDate", "report", "ToDate is mandatory");
		}
		else if(report.getToDate().before(report.getFromDate())){
			errors.rejectValue("fromDate", "report", "FromDate must be before ToDate");
		}

		if(errors.hasErrors()) {
			List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2ComboList();

			model.addAttribute("invoiceTypeList", invoiceTypeList);
//			model.addAttribute("report", new ReportDto());
			menuListRole(model);
			return "report/index-invoice-issue";
		}

		List<IssueInvoiceEntity> reportResult = issueInvoiceService.findAllReportIssue(report);
		List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2ComboList();

		model.addAttribute("invoiceTypeList", invoiceTypeList);
		model.addAttribute("report", report);
		model.addAttribute("reportResult", reportResult);

		return "report/index-invoice-issue";
	}

	/*______________________________________________________*/

	@GetMapping("invoice-warehouse")
	public String reportInvoiceWC(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.REPORT_INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		List<Select2Dto> statusList = StatusOfInvoiceEnum.getSelect2ComboList();
//		List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2ComboList();
		List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2WC();
		List<Select2Dto> kindOfTaxList = StatusOfKindOfTaxEnum.getSelect2ComboList();

		model.addAttribute("statusList", statusList);
		model.addAttribute("invoiceTypeList", invoiceTypeList);
		model.addAttribute("kindOfTaxList", kindOfTaxList);
		model.addAttribute("report", new ReportDto());

		return "report/index-invoice-warehouse";
	}

	@PostMapping("invoice-warehouse")
	public String reportInvoiceWCPost(ModelMap model, @Valid @ModelAttribute("report") ReportDto report,
									BindingResult errors) {
		if(hasRoleAuthor(MenuConstant.REPORT_INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		if(report.getFromDate() == null){
			errors.rejectValue("fromDate", "report", "FromDate is mandatory");
		}
		else if(report.getToDate() == null){
			errors.rejectValue("toDate", "report", "ToDate is mandatory");
		}
		else if(report.getToDate().before(report.getFromDate())){
			errors.rejectValue("fromDate", "report", "FromDate must be before ToDate");
		}

		if(errors.hasErrors()) {
			List<Select2Dto> statusList = StatusOfInvoiceEnum.getSelect2ComboList();
			List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2ComboList();
			List<Select2Dto> kindOfTaxList = StatusOfKindOfTaxEnum.getSelect2ComboList();

			model.addAttribute("statusList", statusList);
			model.addAttribute("invoiceTypeList", invoiceTypeList);
			model.addAttribute("kindOfTaxList", kindOfTaxList);
//			model.addAttribute("report", new ReportDto());
			menuListRole(model);
			return "report/index-invoice";
		}

		List<InvoiceEntity> reportResult = invoiceService.findAllReport(report);
		List<Select2Dto> statusList = StatusOfInvoiceEnum.getSelect2ComboList();
		List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2ComboList();
		List<Select2Dto> kindOfTaxList = StatusOfKindOfTaxEnum.getSelect2ComboList();

		model.addAttribute("statusList", statusList);
		model.addAttribute("invoiceTypeList", invoiceTypeList);
		model.addAttribute("kindOfTaxList", kindOfTaxList);
		model.addAttribute("report", report);
		model.addAttribute("reportResult", reportResult);

		return "report/index-invoice-warehouse";
	}


}
