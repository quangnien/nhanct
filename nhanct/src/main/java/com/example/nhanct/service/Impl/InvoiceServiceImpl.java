package com.example.nhanct.service.Impl;

import com.example.nhanct.config.PDFDataSource;
import com.example.nhanct.config.PDFExporter;
import com.example.nhanct.dto.ReportDto;
import com.example.nhanct.entity.*;
import com.example.nhanct.enumdef.StatusOfInvoiceEnum;
import com.example.nhanct.repository.CustomerRepository;
import com.example.nhanct.repository.InvoiceRepository;
import com.example.nhanct.repository.IssueInvoiceRepository;
import com.example.nhanct.repository.WarehouseRepository;
import com.example.nhanct.service.InvoiceDetailService;
import com.example.nhanct.service.InvoiceService;
import com.example.nhanct.utils.SecurityUtils;
import com.lowagie.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class InvoiceServiceImpl implements InvoiceService{

	@Autowired
	private InvoiceRepository invoiceRepository ;
	@Autowired
	private IssueInvoiceRepository issueInvoiceRepository ;
	@Autowired
	private CustomerRepository customerRepository ;
	@Autowired
	private WarehouseRepository warehouseRepository ;
	@Autowired
	private InvoiceDetailService invoiceDetailService;
	@Autowired
	SecurityUtils myContext;
	@Autowired
	public JavaMailSender emailSender;
	private Configuration configuration;

	@Override
	public Page<InvoiceEntity> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return invoiceRepository.findAll(pageable);
	}

	@Override
	public InvoiceEntity getById(int id) {
		InvoiceEntity invoiceEntity = invoiceRepository.findById(id).get();

		Optional<CustomerEntity> customer = customerRepository.findById(invoiceEntity.getCustomerId());
		Optional<WarehouseEntity> warehouseInput = warehouseRepository.findById(invoiceEntity.getInputWarehouseId());
		Optional<WarehouseEntity> warehouseOutput = warehouseRepository.findById(invoiceEntity.getInputWarehouseId());

		CustomerEntity customerEntity = new CustomerEntity();
		WarehouseEntity warehouseInputEntity = new WarehouseEntity();
		WarehouseEntity warehouseOutputEntity = new WarehouseEntity();
		if(invoiceEntity.getFlagInvoiceType().equals("VAT")){
			customerEntity = customer.get();
			invoiceEntity.setCustomerName(customerEntity.getCustomerName());
			invoiceEntity.setPhone(customerEntity.getPhone());
			invoiceEntity.setAddress(customerEntity.getAddress());
			invoiceEntity.setEmail(customerEntity.getEmail());
			invoiceEntity.setMst(customerEntity.getMst());
			invoiceEntity.setInvoiceType("VAT");
		}
		else if(invoiceEntity.getFlagInvoiceType().equals("WC")){
			warehouseInputEntity = warehouseInput.get();
			invoiceEntity.setInputWarehouseId(warehouseInputEntity.getId());

			warehouseOutputEntity = warehouseInput.get();
			invoiceEntity.setOutputWarehouseId(warehouseOutputEntity.getId());

			invoiceEntity.setInvoiceType("WC");

		}

		return invoiceEntity;
	}

	@Override
	public void add(InvoiceEntity invoice) throws Exception {
		invoice.setIssueDate(new Date());
		invoice.setIssuerId(myContext.getPrincipal().getId());
		invoice.setReleaserId(myContext.getPrincipal().getId());
		invoice.setStatus(StatusOfInvoiceEnum.DU_THAO.getText());
		invoice.setStatusPresent(StatusOfInvoiceEnum.DU_THAO.getText());
		invoice.setDatePresent(new Date());

		IssueInvoiceEntity issueInvoice = new IssueInvoiceEntity();
		if(invoice.getInvoiceType().equals("VAT")){
			issueInvoice = issueInvoiceRepository.findAllByInvoiceTypeOrderByIdDesc("VAT").get(0);
		}
		else if(invoice.getInvoiceType().equals("WC")){
			issueInvoice = issueInvoiceRepository.findAllByInvoiceTypeOrderByIdDesc("WC").get(0);
		};

		invoice.setIssueInvoiceId(issueInvoice.getId());

		Optional<IssueInvoiceEntity> issueInvoiceEntityOptional = issueInvoiceRepository.findById(invoice.getIssueInvoiceId());
		if(issueInvoiceEntityOptional.isPresent()){
			IssueInvoiceEntity issueInvoiceEntity = issueInvoiceEntityOptional.get();
			if(issueInvoiceEntity.getCurrentInvoiceNumber() + 1 > issueInvoiceEntity.getToNumber()){
				throw new Exception("Quantity of Invoice > quantity was registered with Issue-Invoice.");
			}
			else {
//				issueInvoiceEntity.setCurrentInvoiceNumber(issueInvoiceEntity.getCurrentInvoiceNumber() + 1);
				//not set currentInvoiceNumber when event add new
				issueInvoiceRepository.save(issueInvoiceEntity);
			}
		}

		/* IF INVOICE_TYPE = VAT */
		if(invoice.getInvoiceType().equals("VAT")){
			CustomerEntity customerEntityFromDB = customerRepository.findByMst(invoice.getMst());
			if(null != customerEntityFromDB){
				customerEntityFromDB.setCustomerName(invoice.getCustomerName());
				customerEntityFromDB.setPhone(invoice.getPhone());
				customerEntityFromDB.setAddress(invoice.getAddress());
				customerEntityFromDB.setEmail(invoice.getEmail());
				customerRepository.save(customerEntityFromDB);

				invoice.setCustomerId(customerEntityFromDB.getId());
			}
			else {
				CustomerEntity customerEntity = new CustomerEntity();
				customerEntity.setCustomerName(invoice.getCustomerName());
				customerEntity.setMst(invoice.getMst());
				customerEntity.setPhone(invoice.getPhone());
				customerEntity.setAddress(invoice.getAddress());
				customerEntity.setEmail(invoice.getEmail());
				customerRepository.save(customerEntity);

				invoice.setCustomerId(customerEntity.getId());
			}
		}
		else {
			CustomerEntity customerTemp = customerRepository.findAll().get(0);
			invoice.setCustomerId(customerTemp.getId());
		}

		if(invoice.getInvoiceType().equals("VAT")){
			invoice.setFlagInvoiceType("VAT");
		}
		else if(invoice.getInvoiceType().equals("WC")){
			invoice.setFlagInvoiceType("WC");
		}
		invoiceRepository.save(invoice);
	}

	@Override
	public void update(InvoiceEntity invoiceEntity) {
		Optional<InvoiceEntity> entityOptional = invoiceRepository.findById(invoiceEntity.getId());
		if(entityOptional.isPresent()){
			InvoiceEntity entity = entityOptional.get();
			entity.setBusinessId(invoiceEntity.getBusinessId());
			entity.setCustomerId(invoiceEntity.getCustomerId());
			entity.setInputWarehouseId(invoiceEntity.getInputWarehouseId());
			entity.setOutputWarehouseId(invoiceEntity.getOutputWarehouseId());

			/* IF INVOICE_TYPE = VAT */
			if(invoiceEntity.getInvoiceType().equals("VAT")){
				CustomerEntity customerEntityFromDB = customerRepository.findByMst(invoiceEntity.getMst());
				if(null != customerEntityFromDB){
//					customerEntityFromDB.setCustomerName(invoiceEntity.getCustomerName());
//					customerEntityFromDB.setPhone(invoiceEntity.getPhone());
//					customerEntityFromDB.setAddress(invoiceEntity.getAddress());
//					customerRepository.save(customerEntityFromDB);

					entity.setCustomerId(customerEntityFromDB.getId());
				}
				else {
					CustomerEntity customerEntity = new CustomerEntity();
					customerEntity.setCustomerName(invoiceEntity.getCustomerName());
					customerEntity.setMst(invoiceEntity.getMst());
					customerEntity.setPhone(invoiceEntity.getPhone());
					customerEntity.setAddress(invoiceEntity.getAddress());
					customerEntity.setEmail(invoiceEntity.getEmail());
					customerRepository.save(customerEntity);

					entity.setCustomerId(customerEntity.getId());
				}
			}
			else if(invoiceEntity.getInvoiceType().equals("WC")){
				entity.setInputWarehouseId(invoiceEntity.getInputWarehouseId());
				entity.setOutputWarehouseId(invoiceEntity.getOutputWarehouseId());

				/* MUST BE SET, BECAUSE -> ERROR (RELASIONSHIP OF JPA) */
				CustomerEntity customerEntityFromDB = customerRepository.findByMst(invoiceEntity.getMst());
				entity.setCustomerId(customerEntityFromDB.getId());
			}


			if(invoiceEntity.getInvoiceType().equals("VAT")){
				entity.setFlagInvoiceType("VAT");
			}
			else if(invoiceEntity.getInvoiceType().equals("WC")){
				entity.setFlagInvoiceType("WC");
			}

			invoiceRepository.save(entity);
		}
	}

	@Override
	public boolean delete(int id) {
		invoiceRepository.deleteById(id);
		return true;
	}

	@Override
	public List<InvoiceEntity> findAllByKeyword(String keyword) {
		return invoiceRepository.findAllByKeyword(keyword);
	}

	@Override
	public void changeStatusInvoice(int id, String status, String reason) throws Exception {
		if(null != status){
			Optional<InvoiceEntity> invoiceEntityOptional = invoiceRepository.findById(id);
			if(invoiceEntityOptional.isPresent()){
				InvoiceEntity invoice = invoiceEntityOptional.get();
				if(status.equals("approve")){
					invoice.setReleaseDate(new Date());
					invoice.setReleaserId(myContext.getPrincipal().getId());
					invoice.setStatus(StatusOfInvoiceEnum.DA_DUYET.getText());
					invoice.setStatusPresent(StatusOfInvoiceEnum.DA_DUYET.getText());
					invoice.setDatePresent(new Date());

//					Optional<IssueInvoiceEntity> issueInvoiceEntityOptional = issueInvoiceRepository.findById(invoice.getIssueInvoiceId());
//					if(issueInvoiceEntityOptional.isPresent()){
//						IssueInvoiceEntity issueInvoiceEntity = issueInvoiceEntityOptional.get();
//						int curentInvoiceNumber = issueInvoiceEntity.getCurrentInvoiceNumber();
//						if(curentInvoiceNumber + 1 > issueInvoiceEntity.getToNumber()){
//							throw new Exception("Quantity of Invoice > quantity was registered with Issue-Invoice.");
//						}
//						else {
//							issueInvoiceEntity.setCurrentInvoiceNumber(curentInvoiceNumber + 1);
//							invoice.setNumberOfInvoice(curentInvoiceNumber + 1);
//							issueInvoiceRepository.save(issueInvoiceEntity);
//						}
//					}

					invoiceRepository.save(invoice);
				}
				else if(status.equals("cancel")){
					invoice.setReasonForCancellation(reason);
					invoice.setStatus(StatusOfInvoiceEnum.DA_HUY.getText());
					invoice.setCancelDate(new Date());
					invoice.setStatusPresent(StatusOfInvoiceEnum.DA_HUY.getText());
					invoice.setDatePresent(new Date());
					invoiceRepository.save(invoice);
				}
				else if(status.equals("request")){
					invoice.setStatus(StatusOfInvoiceEnum.CHO_DUYET.getText());
					invoice.setStatusPresent(StatusOfInvoiceEnum.CHO_DUYET.getText());
					invoice.setDatePresent(new Date());

					Optional<IssueInvoiceEntity> issueInvoiceEntityOptional = issueInvoiceRepository.findById(invoice.getIssueInvoiceId());
					if(issueInvoiceEntityOptional.isPresent()){
						IssueInvoiceEntity issueInvoiceEntity = issueInvoiceEntityOptional.get();
						int curentInvoiceNumber = issueInvoiceEntity.getCurrentInvoiceNumber();
						if(curentInvoiceNumber + 1 > issueInvoiceEntity.getToNumber()){
							throw new Exception("Quantity of Invoice > quantity was registered with Issue-Invoice.");
						}
						else {
							issueInvoiceEntity.setCurrentInvoiceNumber(curentInvoiceNumber + 1);
							invoice.setNumberOfInvoice(curentInvoiceNumber + 1);
							issueInvoiceRepository.save(issueInvoiceEntity);
						}
					}


					invoiceRepository.save(invoice);
				}
			}

		}
	}

	@Override
	public void pdfForCustomer(int id) throws Exception {

	}

	@Override
	public List<InvoiceEntity> findAllReport(ReportDto report) {
		if(report.getInvoiceType().equals(StatusOfInvoiceEnum.ALL.getText())){
			report.setInvoiceType(null);
		}
		if(report.getStatus().equals(StatusOfInvoiceEnum.ALL.getText())){
			report.setStatus(null);
		}
		if(report.getKindOfTax().equals(StatusOfInvoiceEnum.ALL.getText())){
			report.setKindOfTax(null);
		}

		String fromDate = dateFormat(report.getFromDate());
		Date toDatePlusOne = setToDatePlusOneDay(report.getToDate());
		String toDate = dateFormat(toDatePlusOne);


		if(report.getInvoiceType() != null
			&& report.getKindOfTax() == null
			&& report.getStatus() == null){
				return invoiceRepository.findAllReportByInvoiceType(
						report.getInvoiceType(),
						fromDate, toDate);
		}
		else if(report.getInvoiceType() != null
				&& report.getKindOfTax() != null
				&& report.getStatus() == null){
			return invoiceRepository.findAllReportByInvoiceTypeAndKindOfTax(
					report.getInvoiceType(), report.getKindOfTax(),
					fromDate, toDate);
		}
		else if(report.getInvoiceType() != null
				&& report.getKindOfTax() == null
				&& report.getStatus() != null){
			return invoiceRepository.findAllReportByInvoiceTypeAndStatus(
					report.getInvoiceType(), report.getStatus(),
					fromDate, toDate);
		}
		else if(report.getInvoiceType() != null
				&& report.getKindOfTax() != null
				&& report.getStatus() != null){
			return invoiceRepository.findAllReportByInvoiceTypeAndStatusAndKindOfTax(
					report.getInvoiceType(), report.getStatus(), report.getKindOfTax(),
					fromDate, toDate);
		}
		else if(report.getInvoiceType() == null
				&& report.getKindOfTax() != null
				&& report.getStatus() == null){
			return invoiceRepository.findAllReportByKindOfTax(
					report.getKindOfTax(),
					fromDate, toDate);
		}
		else if(report.getInvoiceType() == null
				&& report.getKindOfTax() != null
				&& report.getStatus() != null){
			return invoiceRepository.findAllReportByStatusAndKindOfTax(
					report.getStatus(), report.getKindOfTax(),
					fromDate, toDate);
		}
		else if(report.getInvoiceType() == null
				&& report.getKindOfTax() == null
				&& report.getStatus() != null){
			return invoiceRepository.findAllReportByStatus(
					report.getStatus(),
					fromDate, toDate);
		}
		else if(report.getInvoiceType() == null
				&& report.getKindOfTax() == null
				&& report.getStatus() == null){
			return invoiceRepository.findAllReportByDate(
					fromDate, toDate);
		}
		return null;
	}

	@Override
	public void sendMailToCustomer(HttpServletResponse response, InvoiceEntity invoiceEntity, int id) throws MessagingException, DocumentException, IOException {
		javax.mail.internet.MimeMessage message = this.emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(invoiceEntity.getCustomer().getEmail());
		helper.setSubject("Here is invoice pdf for you!");
		StringBuffer stringBuffer = new StringBuffer();
		int count = 0;
		stringBuffer.append( "<h1>CÔNG TY CỔ PHẦN NỘI THẤT HÒA PHÁT</h1>");
		stringBuffer.append( "<p>                                        </p>");
		stringBuffer.append( "<h3>Mã số thuế:  0311942282</h3>");
		stringBuffer.append( "<h3>Địa chỉ:  Số 76 Đường số 2, Khu phố 5, Phường Bình Hưng Hòa B, Quận Bình Tân, Thành phố Hồ Chí Minh, Việt Nam</h3>");
		stringBuffer.append( "<h3>Điện thoại:  0938024027</h3>");
		stringBuffer.append( "<p>____________________________________</p>");
		stringBuffer.append( "<p>                                        </p>");
		stringBuffer.append( "<p>Họ và tên người mua hàng: " + invoiceEntity.getCustomer().getCustomerName() + "</p>");
		stringBuffer.append( "<p>Mã số thuế: " + invoiceEntity.getCustomer().getMst()+ "</p>");
		stringBuffer.append( "<p>Địa chỉ: " + invoiceEntity.getCustomer().getAddress()+ "</p>");
		stringBuffer.append( "<p>Điện thoại: " + invoiceEntity.getCustomer().getPhone()+ "</p>");
		stringBuffer.append( "<p>Email: " + invoiceEntity.getCustomer().getEmail()+ "</p>");
		stringBuffer.append( "<p>                                        </p>");

		stringBuffer.append( "<table style=\"width:100%; border: 1px solid black; border-collapse: collapse;\">\n" +
				"  <tr>\n" +
				"    <th style=\"text-align: left; border: 1px solid black; border-collapse: collapse;\">STT</th>\n" +
				"    <th style=\"text-align: left; border: 1px solid black; border-collapse: collapse;\">Tên hàng hóa, dịch vụ</th>\n" +
				"    <th style=\"text-align: left; border: 1px solid black; border-collapse: collapse;\">Đơn vị tính</th>\n" +
				"    <th style=\"text-align: left; border: 1px solid black; border-collapse: collapse;\">Số lượng</th>\n" +
				"    <th style=\"text-align: left; border: 1px solid black; border-collapse: collapse;\">Đơn giá</th>\n" +
				"    <th style=\"text-align: left; border: 1px solid black; border-collapse: collapse;\">Thành tiền</th>\n" +
				"    <th style=\"text-align: left; border: 1px solid black; border-collapse: collapse;\">Thuế xuất GTGT</th>\n" +
				"    <th style=\"text-align: left; border: 1px solid black; border-collapse: collapse;\">Tiền xuất GTGT</th>\n" +
				"  </tr>\n");
		List<InvoiceDetailEntity> invoiceDetailEntityList = invoiceDetailService.findAllByInvoiceId(id);
		for(int i = 0; i < invoiceDetailEntityList.size(); i++){
			stringBuffer.append("  <tr>\n");
			stringBuffer.append("<td style=\"border: 1px solid black; border-collapse: collapse;\">" + i + "</td>\n");
			stringBuffer.append("<td style=\"border: 1px solid black; border-collapse: collapse;\">" + invoiceDetailEntityList.get(i).getItemName() + "</td>\n");
			stringBuffer.append("<td style=\"border: 1px solid black; border-collapse: collapse;\">" + invoiceDetailEntityList.get(i).getDvt() + "</td>\n");
			stringBuffer.append("<td style=\"border: 1px solid black; border-collapse: collapse;\">" + String.valueOf(invoiceDetailEntityList.get(i).getQuantity()) + "</td>\n");
			stringBuffer.append("<td style=\"border: 1px solid black; border-collapse: collapse;\">" + String.valueOf(invoiceDetailEntityList.get(i).getPrice()) + "</td>\n");
			stringBuffer.append("<td style=\"border: 1px solid black; border-collapse: collapse;\">" + String.valueOf(invoiceDetailEntityList.get(i).getPriceBeforeTax()) + "</td>\n");
			stringBuffer.append("<td style=\"border: 1px solid black; border-collapse: collapse;\">" + String.valueOf(invoiceDetailEntityList.get(i).getKindOfTax().getRatio()) + " %" + "</td>\n");
			stringBuffer.append("<td style=\"border: 1px solid black; border-collapse: collapse;\">" + String.valueOf(invoiceDetailEntityList.get(i).getPriceOfTax()) + "</td>\n");
			stringBuffer.append("  </tr>\n");
		}
		stringBuffer.append("</table>");
		helper.setText("invoice", String.valueOf(stringBuffer));

		if(invoiceEntity != null){
			/*__________________*/
			response.setContentType("application/pdf");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
			response.setHeader(headerKey, headerValue);

			invoiceDetailEntityList = invoiceDetailService.findAllByInvoiceId(id);

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
//				listTableHeader2.add("STT");
//				listTableHeader2.add("Tong hop");
//				listTableHeader2.add("Thanh tien truoc thue");
//				listTableHeader2.add("Tien thue");
//				listTableHeader2.add("Tong tien thanh toan");

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

			List<String> listSign = new ArrayList<>();
			listSign.add("Nguoi mua hang");
			listSign.add("Nguoi ban hang");

			PDFExporter exporter = PDFExporter.builder().titleHeader("HOA ĐON GIA TRI GIA TANG").listHeader(listHeader)
					.listHeaderContent(listHeaderContent)
					.colNum1(8).listTableHeader1(listTableHeader1).listDataTable1(listDataTable1)
					.colNum2(0).listTableHeader2(listTableHeader2).listDataTable2(listDataTable2)
					.listSign(listSign).build();

			byte[] pdfData = exporter.getPdfData(response);
			String contentType = "application/pdf";
			PDFDataSource pdfDataSource = new PDFDataSource(pdfData, contentType);
			helper.addAttachment(exporter.getTitleHeader(), pdfDataSource);
		}

		this.emailSender.send(message);
	}

	@Override
	public List<InvoiceEntity> findAll() {
		return invoiceRepository.findAll();
	}

	public String getPdfContent(Map<String, String> inParams, String template)
			throws IOException, TemplateException {
		Template t = configuration.getTemplate(template);
		return FreeMarkerTemplateUtils.processTemplateIntoString(t, inParams);
	}

	private String dateFormat(Date inputDate){

		Instant instant = inputDate.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

		String formattedDate = localDateTime.format(formatter);
		return formattedDate;
	}

	private Date setToDatePlusOneDay(Date toDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate);

		// Add 1 day
		calendar.add(Calendar.DAY_OF_MONTH, 1);

		return calendar.getTime();
	}

//	@Override
//	public List<InvoiceEntity> finByCategoryId(int id) {
//		return invoiceRepository.findByCategoryId(id);
//	}

}
