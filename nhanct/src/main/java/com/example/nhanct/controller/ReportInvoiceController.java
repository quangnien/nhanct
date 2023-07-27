package com.example.nhanct.controller;

import com.example.nhanct.config.PDFExporter;
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
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("admin/report")
public class ReportInvoiceController extends FunctionCommon {

	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private IssueInvoiceService issueInvoiceService;
	@Autowired
	SecurityUtils myMenu;

	private static ReportDto reportDtoCommon = new ReportDto();
	private static List<InvoiceEntity> reportResultInvoiceForExport = new ArrayList<>();
	private static List<IssueInvoiceEntity> reportResultIssueInvoiceForExport = new ArrayList<>();

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

		reportResultInvoiceForExport = new ArrayList<>();
		reportDtoCommon = new ReportDto();

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
			List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2VAT();
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
		List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2VAT();
		List<Select2Dto> kindOfTaxList = StatusOfKindOfTaxEnum.getSelect2ComboList();

		model.addAttribute("statusList", statusList);
		model.addAttribute("invoiceTypeList", invoiceTypeList);
		model.addAttribute("kindOfTaxList", kindOfTaxList);
		model.addAttribute("report", report);
		model.addAttribute("reportResult", reportResult);

		reportResultInvoiceForExport = reportResult;
		reportDtoCommon = report;

		return "report/index-invoice";
	}
	@GetMapping("invoice/export")
	public void exportPdfForInvoice(HttpServletResponse response) throws IOException, DocumentException {

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";

		String headerValue = "attachment; filenamhay e=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

//		List<IssueInvoiceEntity> issueInvoiceEntityList = issueInvoiceService.findAllOrderByDate();

		List<String> listHeader = new ArrayList<>();
		listHeader.add("Nguoi lap phieu: ");
		listHeader.add("                           ");
		listHeader.add("Loai hoa don: ");
		listHeader.add("Trang thai: ");
		listHeader.add("Thoi gian: ");
		listHeader.add("Loai thue: ");

		List<String> listHeaderContent = new ArrayList<>();
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add(reportDtoCommon.getKindOfTax() != null ? reportDtoCommon.getKindOfTax() : "");
		listHeaderContent.add(reportDtoCommon.getStatus() != null ? reportDtoCommon.getStatus() : "");
		listHeaderContent.add((reportDtoCommon.getFromDate() != null && reportDtoCommon.getFromDate() != null) ? reportDtoCommon.getFromDate().toString() + " - " + reportDtoCommon.getToDate().toString() : "");
		listHeaderContent.add(reportDtoCommon.getInvoiceType() != null ? StatusOfInvoiceTypeEnum.getTextByCode(reportDtoCommon.getInvoiceType()) : "");

		List<String> listTableHeader1 = new ArrayList<>();
		listTableHeader1.add("STT");
		listTableHeader1.add("Ky hieu");
		listTableHeader1.add("Tu so");
		listTableHeader1.add("Toi so");
		listTableHeader1.add("Ngay");
		listTableHeader1.add("Tong so luong");
		listTableHeader1.add("Tien thue");
		listTableHeader1.add("Tien truoc thue");
		listTableHeader1.add("Tien sau thue");

		List<List<String>> listDataTable1 = new ArrayList<>();
//		for(int i = 0; i < issueInvoiceEntityList.size(); i++){
		for(int i = 0; i < reportResultInvoiceForExport.size(); i++){
			List<String> listDataTableItem = new ArrayList<>();
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getSymbol());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getFromNumber().toString());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getToNumber().toString());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getDatePresent().toString());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getQuantity().toString());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getPriceOfTax());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getPriceBeforeTax());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getPriceAfterTax());
			listDataTable1.add(listDataTableItem);
		}

		List<String> listSign = new ArrayList<>();
		listSign.add("Nguoi lap phieu");
		listSign.add("Nguoi dai dien");

		PDFExporter exporter = PDFExporter.builder().titleHeader("BAO CAO DOANH THU").listHeader(listHeader)
				.listHeaderContent(listHeaderContent).colNum1(9).listDataTable1(listDataTable1).listTableHeader1(listTableHeader1).listSign(listSign)
				.colNum2(0).listTableHeader2(null).listDataTable2(null).build();

		exporter.export(response);
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

		reportResultIssueInvoiceForExport = new ArrayList<>();
		reportDtoCommon = new ReportDto();

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

		reportResultIssueInvoiceForExport = reportResult;
		reportDtoCommon = report;

		return "report/index-invoice-issue";
	}

	@GetMapping("invoice-issue/export")
	public void exportPdfForInvoiceIssue(HttpServletResponse response) throws IOException, DocumentException {

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";

		String headerValue = "attachment; filenamhay e=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

//		List<IssueInvoiceEntity> issueInvoiceEntityList = issueInvoiceService.findAllOrderByDate();

		List<String> listHeader = new ArrayList<>();
		listHeader.add("CONG TY CO PHAN NOI THAT HOA PHAT");
		listHeader.add("Ma so thue:  0311942282");
		listHeader.add("Dia chi: 76 duong so 2, khu pho 5, Phuong Binh Hung Hoa B, Quan Binh Tan, TP Ho Chi Minh, Viet Nam");
		listHeader.add("So dien thoai: 0938024027");
		listHeader.add("                           ");
		listHeader.add("Loai hoa don: ");
		listHeader.add("Thoi gian: ");

		List<String> listHeaderContent = new ArrayList<>();
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add(reportDtoCommon.getKindOfTax() != null ? reportDtoCommon.getKindOfTax() : "");
		listHeaderContent.add((reportDtoCommon.getFromDate() != null && reportDtoCommon.getFromDate() != null) ? reportDtoCommon.getFromDate().toString() + " - " + reportDtoCommon.getToDate().toString() : "");

		List<String> listTableHeader1 = new ArrayList<>();
		listTableHeader1.add("STT");
		listTableHeader1.add("Ten loai hoa don");
		listTableHeader1.add("Ky hieu");
		listTableHeader1.add("So luong");
		listTableHeader1.add("Tu so");
		listTableHeader1.add("Toi so");
		listTableHeader1.add("So luong da su dung");
		listTableHeader1.add("So luong con hieu luc");

		List<List<String>> listDataTable1 = new ArrayList<>();
//		for(int i = 0; i < issueInvoiceEntityList.size(); i++){
		for(int i = 0; i < reportResultIssueInvoiceForExport.size(); i++){
			List<String> listDataTableItem = new ArrayList<>();
			listDataTableItem.add(reportResultIssueInvoiceForExport.get(i).getInvoiceType().getNameOfInvoiceType());
			listDataTableItem.add(reportResultIssueInvoiceForExport.get(i).getSymbol());
			listDataTableItem.add(String.valueOf(reportResultIssueInvoiceForExport.get(i).getQuantity()));

			listDataTableItem.add(String.valueOf(reportResultIssueInvoiceForExport.get(i).getFromNumber()));
			listDataTableItem.add(String.valueOf(reportResultIssueInvoiceForExport.get(i).getToNumber()));

			int temp = reportResultIssueInvoiceForExport.get(i).getToNumber()
					- reportResultIssueInvoiceForExport.get(i).getCurrentInvoiceNumber();

			listDataTableItem.add(String.valueOf(reportResultIssueInvoiceForExport.get(i).getQuantity() - temp));
			listDataTableItem.add(String.valueOf(temp));

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

		reportResultInvoiceForExport = new ArrayList<>();
		reportDtoCommon = new ReportDto();

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
			List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2WC();
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
		List<Select2Dto> invoiceTypeList = StatusOfInvoiceTypeEnum.getSelect2WC();
		List<Select2Dto> kindOfTaxList = StatusOfKindOfTaxEnum.getSelect2ComboList();

		model.addAttribute("statusList", statusList);
		model.addAttribute("invoiceTypeList", invoiceTypeList);
		model.addAttribute("kindOfTaxList", kindOfTaxList);
		model.addAttribute("report", report);
		model.addAttribute("reportResult", reportResult);

		reportResultInvoiceForExport = reportResult;
		reportDtoCommon = report;

		return "report/index-invoice-warehouse";
	}

	@GetMapping("invoice-warehouse/export")
	public void exportPdfForInvoiceWareHose(HttpServletResponse response) throws IOException, DocumentException {

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";

		String headerValue = "attachment; filenamhay e=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

//		List<IssueInvoiceEntity> issueInvoiceEntityList = issueInvoiceService.findAllOrderByDate();

		List<String> listHeader = new ArrayList<>();
		listHeader.add("Nguoi lap phieu: ");
		listHeader.add("                           ");
		listHeader.add("Loai hoa don: ");
		listHeader.add("Trang thai: ");
		listHeader.add("Thoi gian: ");
		listHeader.add("Loai thue: ");

		List<String> listHeaderContent = new ArrayList<>();
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add(reportDtoCommon.getKindOfTax() != null ? reportDtoCommon.getKindOfTax() : "");
		listHeaderContent.add(reportDtoCommon.getStatus() != null ? reportDtoCommon.getStatus() : "");
		listHeaderContent.add((reportDtoCommon.getFromDate() != null && reportDtoCommon.getFromDate() != null) ? reportDtoCommon.getFromDate().toString() + " - " + reportDtoCommon.getToDate().toString() : "");
		listHeaderContent.add(reportDtoCommon.getInvoiceType() != null ? StatusOfInvoiceTypeEnum.getTextByCode(reportDtoCommon.getInvoiceType()) : "");

		List<String> listTableHeader1 = new ArrayList<>();
		listTableHeader1.add("STT");
		listTableHeader1.add("Ky hieu");
		listTableHeader1.add("Tu so");
		listTableHeader1.add("Toi so");
		listTableHeader1.add("Ngay");
		listTableHeader1.add("Tong so luong");
		listTableHeader1.add("Tien thue");
		listTableHeader1.add("Tien truoc thue");
		listTableHeader1.add("Tien sau thue");

		List<List<String>> listDataTable1 = new ArrayList<>();
//		for(int i = 0; i < issueInvoiceEntityList.size(); i++){
		for(int i = 0; i < reportResultInvoiceForExport.size(); i++){
			List<String> listDataTableItem = new ArrayList<>();
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getSymbol());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getFromNumber().toString());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getToNumber().toString());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getDatePresent().toString());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getQuantity().toString());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getPriceOfTax());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getPriceBeforeTax());
			listDataTableItem.add(reportResultInvoiceForExport.get(i).getPriceAfterTax());
			listDataTable1.add(listDataTableItem);
		}

		List<String> listSign = new ArrayList<>();
		listSign.add("Nguoi lap phieu");
		listSign.add("Nguoi dai dien");

		PDFExporter exporter = PDFExporter.builder().titleHeader("BAO CAO DOANH THU").listHeader(listHeader)
				.listHeaderContent(listHeaderContent).colNum1(9).listDataTable1(listDataTable1).listTableHeader1(listTableHeader1).listSign(listSign)
				.colNum2(0).listTableHeader2(null).listDataTable2(null).build();

		exporter.export(response);
	}

}
