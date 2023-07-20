package com.example.nhanct.controller;

import com.example.nhanct.config.PDFExporter;
import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.entity.*;
import com.example.nhanct.service.*;
import com.example.nhanct.utils.SecurityUtils;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
	public String add(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
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
		return "invoice/add";
	}

	@PostMapping("add")
	public String add(@Valid @ModelAttribute("invoice") InvoiceEntity invoice, BindingResult errors, ModelMap model) {

//		if(invoice.getFromNumber() > invoice.getToNumber()) {
//			errors.rejectValue("fromNumber", "invoice", "ToNumber must be larger than FromNumber");
//		}

		if(errors.hasErrors()) {
			model.addAttribute("listInvoiceType", invoiceTypeService.findAll());
			menuListRole(model);
			return "invoice/add";
		}

		try {
			invoiceService.add(invoice);
		} catch (Exception e) {
			throw new RuntimeException(e);
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

	/*______________________________________*/
	@GetMapping("approve")
	public String approve(ModelMap model, @RequestParam("id") int id) {
		if(hasRoleAuthor(MenuConstant.INVOICE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		String status = "approve";
		try {
			invoiceService.changeStatusInvoice(id, status, "");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "redirect:/admin/invoice";
	}

	/* ____________________________ EXPORT PDF ____________________________*/
	@GetMapping("pdf-customer")
	public void exportPdf(@RequestParam("id") int id, ModelMap model, HttpSession session, HttpServletResponse response) throws IOException, DocumentException {

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		InvoiceEntity invoiceEntity = invoiceService.getById(id);
		List<InvoiceDetailEntity> invoiceDetailEntityList = invoiceDetailService.findAllByInvoiceId(id);

		List<String> listHeader = new ArrayList<>();
		listHeader.add("MST");
		listHeader.add("Address");
		listHeader.add("Phone");
		listHeader.add("Date");
		listHeader.add("FullName Of Carrier");
		listHeader.add("Vehicle For Ship");
		listHeader.add("Warehouse Input");
		listHeader.add("Warehouse Output");

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
		}

		PDFExporter exporter = PDFExporter.builder().titleHeader("HOA ĐON XUAT KHO KIEM VAN CHUYEN NOI BO").listHeader(listHeader)
				.listHeaderContent(listHeaderContent).colNum(7).listDataTable(listDataTable).build();

		exporter.export(response);
//		return "redirect:/detail/invoice/edit?id="+id;
	}



	/* ____________________________ EXPORT PDF OPTION 2 - BUT NO SUCCESS with list data____________________________*/

	//	@GetMapping("pdf-customer")
	public void invoiceForCustomer(ModelMap model, @RequestParam("id") int id, HttpSession session, HttpServletResponse response) throws IOException, DocumentException {
		menuListRole(model);

		String fileName = "customer_invoice_" + id + ".pdf";
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

		try (OutputStream outputStream = response.getOutputStream()) {
			String templatePath = "/templates/invoice/file/invoice_customer.html";
			String htmlContent = parseThymeleafTemplate(templatePath, "to", "Some Address");
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(htmlContent);
			renderer.layout();
			renderer.createPDF(outputStream);
		}

	}

	private String parseThymeleafTemplate(String templatePath, String name, String address) throws IOException {
		// Read the template file from the specified location
		String template = readTemplateFromFile(templatePath);

		// Replace placeholders with actual values
		String htmlContent = template.replace("${to}", name);
		return htmlContent;
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
}