package com.example.nhanct.controller;

import com.example.nhanct.config.PDFExporter;
import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.dto.InvoiceJson;
import com.example.nhanct.entity.*;
import com.example.nhanct.repository.KindOfTaxRepository;
import com.example.nhanct.service.*;
import com.example.nhanct.utils.SecurityUtils;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("admin/invoice")
public class InvoiceController extends FunctionCommon {

	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private InvoiceDetailService invoiceDetailService;
    @Autowired
	private InvoiceTypeService invoiceTypeService;
	@Autowired
	private BusinessService businessService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private IssueInvoiceService issueInvoiceService;
	@Autowired
	private KindOfTaxRepository kindOfTaxRepository;
	@Autowired
	private SecurityUtils myUser;
	@Value("${adress.404}")
	private String adress404;

    @GetMapping("")
	public String index(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		return listPage(model, 1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		Page<InvoiceEntity> page = invoiceService.findAll(currentPage);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<InvoiceEntity> listInvoice = page.getContent();

		String isAdd = hasRoleByParam("ROLE_NHAN_VIEN_PHONG_KE_TOAN");
		String isChecker = hasRoleByParam("ROLE_TRUONG_PHONG_KE_TOAN");

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listInvoice", listInvoice);
		model.addAttribute("isAdd", isAdd);
		model.addAttribute("isChecker", isChecker);

		return "invoice/index";
	}

	@GetMapping("search")
	public String index(ModelMap model, @Param("keyword") String keyword) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		String isAdd = hasRoleByParam("ROLE_NHAN_VIEN_PHONG_KE_TOAN");
		String isChecker = hasRoleByParam("ROLE_TRUONG_PHONG_KE_TOAN");

		List<InvoiceEntity> listInvoice = invoiceService.findAllByKeyword(keyword);
		model.addAttribute("listInvoice", listInvoice);
		model.addAttribute("keyword", keyword);
		model.addAttribute("isAdd", isAdd);
		model.addAttribute("isChecker", isChecker);
		return "invoice/search";
	}

	@GetMapping("add")
	public String add(ModelMap model, @RequestParam(value = "message", required = false) String message) {

		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		List<BusinessEntity> listBusiness = businessService.findAll();
		List<CustomerEntity> listCustomer = customerService.findAll();
		List<WarehouseEntity> listWarehouse = warehouseService.findAll();
		List<IssueInvoiceEntity> listIssueInvoice = issueInvoiceService.findAll();
//		if(listInvoiceType.isEmpty()) return "redirect:"+page406 + "?id=invoiceType";

		model.addAttribute("message", message);

		model.addAttribute("listBusiness", listBusiness);
		model.addAttribute("listCustomer", listCustomer);
		model.addAttribute("listWarehouse", listWarehouse);
		model.addAttribute("listIssueInvoice", listIssueInvoice);
		model.addAttribute("invoice", new InvoiceEntity());
		return "invoice/add";
	}

	@PostMapping("add")
	public String add(@Valid @ModelAttribute("invoice") InvoiceEntity invoice, BindingResult errors, ModelMap model) {

//		if(invoice.getFromNumber() > invoice.getToNumber()) {
//			errors.rejectValue("fromNumber", "invoice", "ToNumber must be larger than FromNumber");
//		}

		String message = "";

		if(errors.hasErrors()) {
			model.addAttribute("listInvoiceType", invoiceTypeService.findAll());
			menuListRole(model);
			return "invoice/add";
		}

		try {
			invoiceService.add(invoice);
		} catch (Exception e) {
			message = "F";
			return "redirect:/admin/invoice/add?message=" + message;
//			throw new RuntimeException(e);
		}
		return "redirect:/admin/invoice";
	}

	@GetMapping("edit")
	public String edit(@RequestParam("id") int id, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		try {
			List<BusinessEntity> listBusiness = businessService.findAll();
			List<CustomerEntity> listCustomer = customerService.findAll();
			List<WarehouseEntity> listWarehouse = warehouseService.findAll();
			List<IssueInvoiceEntity> listIssueInvoice = issueInvoiceService.findAll();

			model.addAttribute("listBusiness", listBusiness);
			model.addAttribute("listCustomer", listCustomer);
			model.addAttribute("listWarehouse", listWarehouse);
			model.addAttribute("listIssueInvoice", listIssueInvoice);
			model.addAttribute("invoice", invoiceService.getById(id));
			return "invoice/edit";
		} catch (Exception e) {
			return "redirect:"+adress404;
		}

	}

	@PostMapping("edit")
	public String edit(@Valid @ModelAttribute("invoice") InvoiceEntity invoice, BindingResult errors, ModelMap model) {

		if(errors.hasErrors()) {
			menuListRole(model);
			model.addAttribute("listInvoiceType", invoiceTypeService.findAll());
			return "invoice/edit";
		}
		List<BusinessEntity> listBusiness = businessService.findAll();
		List<CustomerEntity> listCustomer = customerService.findAll();
		List<WarehouseEntity> listWarehouse = warehouseService.findAll();
		List<IssueInvoiceEntity> listIssueInvoice = issueInvoiceService.findAll();
//		if(listInvoiceType.isEmpty()) return "redirect:"+page406 + "?id=invoiceType";

		model.addAttribute("listBusiness", listBusiness);
		model.addAttribute("listCustomer", listCustomer);
		model.addAttribute("listWarehouse", listWarehouse);
		model.addAttribute("listIssueInvoice", listIssueInvoice);
		model.addAttribute("invoice", new InvoiceEntity());

		invoiceService.update(invoice);
		return "redirect:/admin/invoice";
	}

	@GetMapping("confirm-delete")
	public String delete(@RequestParam("id") int id, ModelMap model,
			@RequestParam(value = "message", required = false) String message) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		try {
			InvoiceEntity invoice = invoiceService.getById(id);
			model.addAttribute("invoice", invoice);
			model.addAttribute("message", message);
			model.addAttribute("reasonForCancel", "");
			return "invoice/confirm-delete";
		} catch (Exception e) {
			return "redirect:"+adress404;
		}
	}

	@GetMapping("confirm-delete-post")
	public String confirmDeletePost(@RequestParam("id") int id,@RequestParam("reason") String reason, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		String message = "";
		try {
//			invoiceService.delete(id);
			String status = "cancel";
			try {
				invoiceService.changeStatusInvoice(id, status, reason);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return "redirect:/admin/invoice";

		} catch (DataIntegrityViolationException e) {
			message = "F";
			return "redirect:/admin/invoice/confirm-delete?id=" + id + "&&message="+message;
		} catch(Exception e) {
			message = "F";
			return "redirect:/admin/invoice/confirm-delete?id=" + id + "&&message="+message;
		}
	}

	@GetMapping("request-to-approve")
	public String requestToAppove(@RequestParam("id") int id, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		String message = "";
		try {
			String status = "request";
			try {
				invoiceService.changeStatusInvoice(id, status, "");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return "redirect:/admin/invoice";

		} catch (DataIntegrityViolationException e) {
			message = "F";
			return "redirect:/admin/invoice"+ id + "&&message="+message;
		} catch(Exception e) {
			message = "F";
			return "redirect:/admin/invoice"+ id + "&&message="+message;
		}
	}

	/*______________________________________*/
	@GetMapping("approve")
	public String approve(ModelMap model, @RequestParam("id") int id, HttpServletResponse response) {
		if (hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		String status = "approve";
		try {
			invoiceService.changeStatusInvoice(id, status, "");

			/* ___________ AUTO SEND MAIL ____________*/
//			InvoiceEntity invoiceEntity = invoiceService.getById(id);

//			SimpleMailMessage message = new SimpleMailMessage();
//
//			helper.setTo(invoiceEntity.getCustomer().getEmail());
//			helper.setSubject("Invoice Information");
//			message.setText("Here is invoice for you!");
//
//			if(invoiceEntity != null){
//
//			}
//				/*__________________*/
//				response.setContentType("application/pdf");
//				DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//				String currentDateTime = dateFormatter.format(new Date());
//
//				String headerKey = "Content-Disposition";
//				String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
//				response.setHeader(headerKey, headerValue);
//
//				List<InvoiceDetailEntity> invoiceDetailEntityList = invoiceDetailService.findAllByInvoiceId(id);
//
//				List<String> listHeader = new ArrayList<>();
//				listHeader.add("CONG TY CO PHAN NOI THAT HOA PHAT");
//				listHeader.add("Ma so thue:  0311942282");
//				listHeader.add("Dia chi: 76 duong so 2, khu pho 5, Phuong Binh Hung Hoa B, Quan Binh Tan, TP Ho Chi Minh, Viet Nam");
//				listHeader.add("So dien thoai: 0938024027");
//				listHeader.add("_______________________");
//				listHeader.add("Ho va ten nguoi mua hang: ");
//				listHeader.add("Ma so thue: ");
//				listHeader.add("Dia chi: ");
//				listHeader.add("Dien thoai: ");
//				listHeader.add("Email: ");
//
//				List<String> listHeaderContent = new ArrayList<>();
//				listHeaderContent.add("");
//				listHeaderContent.add("");
//				listHeaderContent.add("");
//				listHeaderContent.add("");
//				listHeaderContent.add("");
//				listHeaderContent.add(invoiceEntity.getCustomer().getCustomerName());
//				listHeaderContent.add(invoiceEntity.getCustomer().getMst());
//				listHeaderContent.add(invoiceEntity.getCustomer().getAddress());
//				listHeaderContent.add(invoiceEntity.getCustomer().getPhone());
//				listHeaderContent.add(invoiceEntity.getCustomer().getEmail());
//
//				List<String> listTableHeader1 = new ArrayList<>();
//				listTableHeader1.add("STT");
//				listTableHeader1.add("Ten hang hoa, dich vu");
//				listTableHeader1.add("Don vi tinh");
//				listTableHeader1.add("So luong");
//				listTableHeader1.add("Don gia");
//				listTableHeader1.add("Thanh tien");
//				listTableHeader1.add("Thue xuat GTGT");
//				listTableHeader1.add("Tien xuat GTGT");
//
//				List<String> listTableHeader2 = new ArrayList<>();
//				listTableHeader2.add("STT");
//				listTableHeader2.add("Tong hop");
//				listTableHeader2.add("Thanh tien truoc thue");
//				listTableHeader2.add("Tien thue");
//				listTableHeader2.add("Tong tien thanh toan");
//
//				List<List<String>> listDataTable1 = new ArrayList<>();
//				for(int i = 0; i < invoiceDetailEntityList.size(); i++){
//					List<String> listDataTableItem = new ArrayList<>();
//					listDataTableItem.add(invoiceDetailEntityList.get(i).getItemName());
//					listDataTableItem.add(invoiceDetailEntityList.get(i).getDvt());
//					listDataTableItem.add(String.valueOf(invoiceDetailEntityList.get(i).getQuantity()));
//					listDataTableItem.add(String.valueOf(invoiceDetailEntityList.get(i).getPrice()));
//					listDataTableItem.add(String.valueOf(invoiceDetailEntityList.get(i).getPriceBeforeTax()));
//					listDataTableItem.add(String.valueOf(invoiceDetailEntityList.get(i).getKindOfTax().getRatio()) + " %");
//					listDataTableItem.add(String.valueOf(invoiceDetailEntityList.get(i).getPriceOfTax()));
//
//					listDataTable1.add(listDataTableItem);
//				}
//
//				List<List<String>> listDataTable2 = new ArrayList<>();
//				List<KindOfTaxEntity> listKindOfTaxEntityList = kindOfTaxRepository.findAll();
//				for(KindOfTaxEntity kindOfTaxEntity : listKindOfTaxEntityList){
//					BigDecimal priceBeforeTax = BigDecimal.valueOf(0);
//					BigDecimal priceOfTax = BigDecimal.valueOf(0);
//					BigDecimal priceAfterTax = BigDecimal.valueOf(0);
//					List<InvoiceDetailEntity> invoiceDetailList = invoiceDetailService.findAllByKindOfTaxId(kindOfTaxEntity.getId());
//					for(InvoiceDetailEntity invoiceDetail: invoiceDetailList){
//						priceBeforeTax = priceBeforeTax. add(invoiceDetail.getPriceBeforeTax());
//						priceOfTax = priceOfTax.add(invoiceDetail.getPriceOfTax());
//						priceAfterTax = priceAfterTax.add(invoiceDetail.getPriceAfterTax());
//					}
//
//					List<String> listDataItem = new ArrayList<>();
//					listDataItem.add(kindOfTaxEntity.getNameOfTax());
//					listDataItem.add(priceBeforeTax.toString());
//					listDataItem.add(priceOfTax.toString());
//					listDataItem.add(priceAfterTax.toString());
//
//					listDataTable2.add(listDataItem);
//				}
//
//				List<String> listSign = new ArrayList<>();
//				listSign.add("Nguoi mua hang");
//				listSign.add("Nguoi ban hang");
//
//				PDFExporter exporter = PDFExporter.builder().titleHeader("HOA ĐON GIA TRI GIA TANG").listHeader(listHeader)
//						.listHeaderContent(listHeaderContent)
//						.colNum1(8).listTableHeader1(listTableHeader1).listDataTable1(listDataTable1)
//						.colNum2(5).listTableHeader2(listTableHeader2).listDataTable2(listDataTable2)
//						.listSign(listSign).build();
//
//				// Attach the PDF file to the email
//				helper.addAttachment(exporter.getTitleHeader(), (DataSource) exporter);
//				/*__________________*/
//			}
//
//			this.emailSender.send(message);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}

			return "redirect:/admin/invoice";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

		/* ____________________________ EXPORT PDF ____________________________*/
	@GetMapping("report/business")
	public void exportPdfForBusiness(@RequestParam("id") int id, HttpServletResponse response) throws IOException, DocumentException {

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		InvoiceEntity invoiceEntity = invoiceService.getById(id);
		List<InvoiceDetailEntity> invoiceDetailEntityList = invoiceDetailService.findAllByInvoiceId(id);

		List<String> listHeader = new ArrayList<>();
		listHeader.add("MST: ");
		listHeader.add("Address: ");
		listHeader.add("Phone: ");
		listHeader.add("Date: ");
		listHeader.add("FullName Of Carrier: ");
		listHeader.add("Vehicle For Ship: ");
		listHeader.add("Warehouse Input: ");
		listHeader.add("Warehouse Output: ");

		List<String> listHeaderContent = new ArrayList<>();
		listHeaderContent.add(invoiceEntity.getBusiness().getMst());
		listHeaderContent.add("HCM");
		listHeaderContent.add(invoiceEntity.getBusiness().getPhone());
		listHeaderContent.add(new Date().toString());
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add(invoiceEntity.getInputWarehouse().getWarehouseName());
		listHeaderContent.add(invoiceEntity.getOutputWarehouse().getWarehouseName());

		List<List<String>> listDataTable = new ArrayList<>();
		for(int i = 0; i < invoiceDetailEntityList.size(); i++){
			List<String> listDataTableItem = new ArrayList<>();
			listDataTableItem.add(invoiceDetailEntityList.get(i).getItemName());
			listDataTableItem.add("");
			listDataTableItem.add(invoiceDetailEntityList.get(i).getDvt());
			listDataTableItem.add(String.valueOf(invoiceDetailEntityList.get(i).getQuantity()));
			listDataTableItem.add("");
			listDataTableItem.add("");
			listDataTableItem.add("");

			listDataTable.add(listDataTableItem);
		}

		List<String> listTableHeader = new ArrayList<>();
		listTableHeader.add("STT");
		listTableHeader.add("Item Name");
		listTableHeader.add("Number");
		listTableHeader.add("DVT");
		listTableHeader.add("Quantity Input");
		listTableHeader.add("Quantity Output");
		listTableHeader.add("Price");
		listTableHeader.add("To Price");


		List<String> listSign = new ArrayList<>();
		listSign.add("Nguoi lap phieu");
		listSign.add("Thu kho xuat");
		listSign.add("Nguoi van chuyen");
		listSign.add("Thu kho nhap");

		PDFExporter exporter = PDFExporter.builder().titleHeader("HOA ĐON XUAT KHO KIEM VAN CHUYEN NOI BO").listHeader(listHeader)
				.listHeaderContent(listHeaderContent).colNum1(8).listDataTable1(listDataTable).listTableHeader1(listTableHeader).listSign(listSign)
				.colNum2(0).listTableHeader2(null).listDataTable2(null).build();

		exporter.export(response);
	}

	@GetMapping("report/customer")
	public void exportPdfForCustomer(@RequestParam("id") int id, HttpServletResponse response) throws IOException, DocumentException {

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		InvoiceEntity invoiceEntity = invoiceService.getById(id);
		List<InvoiceDetailEntity> invoiceDetailEntityList = invoiceDetailService.findAllByInvoiceId(id);

		List<String> listHeader = new ArrayList<>();
		listHeader.add("CONG TY CO PHAN NOI THAT HOA PHAT");
		listHeader.add("Ma so thue:  0311942282");
		listHeader.add("Dia chi: 76 duong so 2, khu pho 5, Phuong Binh Hung Hoa B, Quan Binh Tan, TP Ho Chi Minh, Viet Nam");
		listHeader.add("So dien thoai: 0938024027");
		listHeader.add("_______________________");
		listHeader.add("Ho va ten nguoi mua hang: ");
		listHeader.add("Ma so thue: ");
		listHeader.add("Dia chi: ");
		listHeader.add("Dien thoai: ");
		listHeader.add("Email: ");

		List<String> listHeaderContent = new ArrayList<>();
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add("");
		listHeaderContent.add(invoiceEntity.getCustomer().getCustomerName());
		listHeaderContent.add(invoiceEntity.getCustomer().getMst());
		listHeaderContent.add(invoiceEntity.getCustomer().getAddress());
		listHeaderContent.add(invoiceEntity.getCustomer().getPhone());
		listHeaderContent.add(invoiceEntity.getCustomer().getEmail());

		List<String> listTableHeader1 = new ArrayList<>();
		listTableHeader1.add("STT");
		listTableHeader1.add("Ten hang hoa, dich vu");
		listTableHeader1.add("Don vi tinh");
		listTableHeader1.add("So luong");
		listTableHeader1.add("Don gia");
		listTableHeader1.add("Thanh tien");
		listTableHeader1.add("Thue xuat GTGT");
		listTableHeader1.add("Tien xuat GTGT");

		List<String> listTableHeader2 = new ArrayList<>();
		listTableHeader2.add("STT");
		listTableHeader2.add("Tong hop");
		listTableHeader2.add("Thanh tien truoc thue");
		listTableHeader2.add("Tien thue");
		listTableHeader2.add("Tong tien thanh toan");

		List<List<String>> listDataTable1 = new ArrayList<>();
		for(int i = 0; i < invoiceDetailEntityList.size(); i++){
			List<String> listDataTableItem = new ArrayList<>();
			listDataTableItem.add(invoiceDetailEntityList.get(i).getItemName());
			listDataTableItem.add(invoiceDetailEntityList.get(i).getDvt());
			listDataTableItem.add(String.valueOf(invoiceDetailEntityList.get(i).getQuantity()));
			listDataTableItem.add(String.valueOf(invoiceDetailEntityList.get(i).getPrice()));
			listDataTableItem.add(String.valueOf(invoiceDetailEntityList.get(i).getPriceBeforeTax()));
			listDataTableItem.add(String.valueOf(invoiceDetailEntityList.get(i).getKindOfTax().getRatio()) + " %");
			listDataTableItem.add(String.valueOf(invoiceDetailEntityList.get(i).getPriceOfTax()));

			listDataTable1.add(listDataTableItem);
		}

		List<List<String>> listDataTable2 = new ArrayList<>();
		List<KindOfTaxEntity> listKindOfTaxEntityList = kindOfTaxRepository.findAll();
		for(KindOfTaxEntity kindOfTaxEntity : listKindOfTaxEntityList){
			BigDecimal priceBeforeTax = BigDecimal.valueOf(0);
			BigDecimal priceOfTax = BigDecimal.valueOf(0);
			BigDecimal priceAfterTax = BigDecimal.valueOf(0);
			List<InvoiceDetailEntity> invoiceDetailList = invoiceDetailService.findAllByKindOfTaxId(kindOfTaxEntity.getId());
			for(InvoiceDetailEntity invoiceDetail: invoiceDetailList){
				priceBeforeTax = priceBeforeTax. add(invoiceDetail.getPriceBeforeTax());
				priceOfTax = priceOfTax.add(invoiceDetail.getPriceOfTax());
				priceAfterTax = priceAfterTax.add(invoiceDetail.getPriceAfterTax());
			}

			List<String> listDataItem = new ArrayList<>();
			listDataItem.add(kindOfTaxEntity.getNameOfTax());
			listDataItem.add(priceBeforeTax.toString());
			listDataItem.add(priceOfTax.toString());
			listDataItem.add(priceAfterTax.toString());

			listDataTable2.add(listDataItem);
		}

		List<String> listSign = new ArrayList<>();
		listSign.add("Nguoi mua hang");
		listSign.add("Nguoi ban hang");

		PDFExporter exporter = PDFExporter.builder().titleHeader("HOA ĐON GIA TRI GIA TANG").listHeader(listHeader)
				.listHeaderContent(listHeaderContent)
				.colNum1(8).listTableHeader1(listTableHeader1).listDataTable1(listDataTable1)
				.colNum2(5).listTableHeader2(listTableHeader2).listDataTable2(listDataTable2)
				.listSign(listSign).build();

		exporter.export(response);
	}

	@GetMapping("cancel-invoice")
	public void exportPdfForCalcelInvoice(@RequestParam("id") int id, HttpServletResponse response) throws IOException, DocumentException {

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		InvoiceEntity invoiceEntity = invoiceService.getById(id);
		List<InvoiceDetailEntity> invoiceDetailEntityList = invoiceDetailService.findAllByInvoiceId(id);

		List<String> listHeader = new ArrayList<>();
		listHeader.add("Tien hanh lap bien ban ve viec huy hoa don sau");
		listHeader.add("Ngay: ");
		listHeader.add("Loai hoa don: ");
		listHeader.add("Ky hieu hoa don: ");
		listHeader.add("So hoa don: ");
		listHeader.add("Phat hanh ngay: ");
		listHeader.add("Nguoi phat hanh: ");
		listHeader.add("Ly do huy: ");
		listHeader.add("Vay chung toi lap bien ban nay de lam co so huy hoa don viet sai tren va cam ket" +
				" khong su dung hoa don tren de ke khai thue GTGT");

		List<String> listHeaderContent = new ArrayList<>();
		listHeaderContent.add("");
		listHeaderContent.add(new Date().toString());
		listHeaderContent.add(invoiceEntity.getIssueInvoice().getInvoiceType().getNameOfInvoiceType());
		listHeaderContent.add(invoiceEntity.getSymbol());
		listHeaderContent.add(String.valueOf(invoiceEntity.getNumberOfInvoice()));
		listHeaderContent.add(String.valueOf(invoiceEntity.getIssueDate()));
		listHeaderContent.add(String.valueOf(invoiceEntity.getIssuerUser().getUserName()));
		listHeaderContent.add(String.valueOf(invoiceEntity.getReasonForCancellation()));
		listHeaderContent.add("");

		List<String> listSign = new ArrayList<>();
		listSign.add("Nguoi lap bien ban");
		listSign.add("Nguoi dai dien");

		PDFExporter exporter = PDFExporter.builder().titleHeader("BIEN BAN HUY HOA DON").listHeader(listHeader)
				.listHeaderContent(listHeaderContent).colNum1(0).listDataTable1(null).listTableHeader1(null).listSign(listSign)
				.colNum2(0).listTableHeader2(null).listDataTable2(null).build();

		exporter.export(response);
	}

	/* ____________________________ EXPORT PDF OPTION 2 - BUT NO SUCCESS with list data____________________________*/

//		@GetMapping("pdf-customer")
	public void invoiceForCustomer(ModelMap model, @RequestParam("id") int id, HttpSession session, HttpServletResponse response) throws IOException, DocumentException {
		menuListRole(model);

		String fileName = "customer_invoice_" + id + ".pdf";
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

		try (OutputStream outputStream = response.getOutputStream()) {
			String templatePath = "/templates/invoice/file/invoice_customer.ftlh";
			String htmlContent = parseThymeleafTemplate(templatePath, "", "");
			ITextRenderer renderer = new ITextRenderer();
			String fontPath = "./static/fonts/Baloo-Regular.ttf";
			renderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.setDocumentFromString(htmlContent);
			renderer.layout();
			renderer.createPDF(outputStream);
		}

	}

	private String parseThymeleafTemplate(String templatePath, String name, String address) throws IOException {
		// Read the template file from the specified location
		String template = readTemplateFromFile(templatePath);

		// Replace placeholders with actual values
//		String htmlContent = template.replace("${to}", name);
//		return htmlContent;
		return template;
	}

	private String readTemplateFromFile(String templatePath) throws IOException {
		InputStream inputStream = getClass().getResourceAsStream(templatePath);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			return stringBuilder.toString();
		}
	}

//	private String parseThymeleafTemplate() {
//		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//		templateResolver.setSuffix(".html");
//		templateResolver.setTemplateMode(TemplateMode.HTML);
//
//		TemplateEngine templateEngine = new TemplateEngine();
//		templateEngine.setTemplateResolver(templateResolver);
//
//		Context context = new Context();
//		context.setVariable("to", "Baeldung");
//
//		return templateEngine.process("invoice_customer", context);
//	}

	/*---------------- begin AUTHOR ------------*/
	private String hasRoleByParam(String roleCode) {
		Object[] tg = myUser.getPrincipal().getAuthorities().toArray();
		for (Object object : tg) {
			if (object.toString().equals(roleCode)) {
				return "True";
			}
		}
		return "False";
	}

	/* __________ PROCESS FOR AJAX _____________ */
	@PostMapping("/get-customer")
	@ResponseBody
	public InvoiceJson getCustomerName(@RequestParam(name = "mst") String mst) {
		InvoiceJson invoiceJson = new InvoiceJson();
		CustomerEntity customerEntity = new CustomerEntity();
		try {
			if(mst != null || mst.equals("")){
				customerEntity = customerService.findByMst(mst);
				if(null == customerEntity){
					return null;
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}

		invoiceJson.setCustomerName(customerEntity.getCustomerName());
		invoiceJson.setPhone(customerEntity.getPhone());
		invoiceJson.setAddress(customerEntity.getAddress());
		return invoiceJson;
	}

}