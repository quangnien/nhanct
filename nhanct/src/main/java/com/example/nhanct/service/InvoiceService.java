package com.example.nhanct.service;

import com.example.nhanct.dto.ReportDto;
import com.example.nhanct.entity.InvoiceEntity;
import com.example.nhanct.entity.IssueInvoiceEntity;
import com.lowagie.text.DocumentException;
import org.springframework.data.domain.Page;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface InvoiceService {

	List<InvoiceEntity> findAll();
	Page<InvoiceEntity> findAll(int pageNumber);
	InvoiceEntity getById(int id);
	void add(InvoiceEntity product) throws Exception;
	void update(InvoiceEntity product);
	boolean delete(int id);

	List<InvoiceEntity> findAllByKeyword(String keyword);

	void changeStatusInvoice(int id, String status, String reason) throws Exception;
	void pdfForCustomer(int id) throws Exception;

	/*____InvoiceType_____*/
	List<InvoiceEntity> findAllReport(ReportDto report);

	void sendMailToCustomer(HttpServletResponse response, InvoiceEntity invoiceEntity, int id) throws MessagingException, DocumentException, IOException;

}