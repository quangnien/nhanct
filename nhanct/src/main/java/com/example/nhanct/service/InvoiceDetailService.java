package com.example.nhanct.service;

import com.example.nhanct.dto.InvoiceDetailDto;
import com.example.nhanct.entity.InvoiceDetailEntity;
import org.springframework.data.domain.Page;

import java.util.List;


public interface InvoiceDetailService {

	List<InvoiceDetailEntity> findAll();
	Page<InvoiceDetailEntity> findAll(int pageNumber, int invoiceId);
	InvoiceDetailEntity getById(int id);
	void add(InvoiceDetailEntity product);
	void update(InvoiceDetailEntity product);
	boolean delete(int id);
	boolean deleteById(int id);

	List<InvoiceDetailEntity> findAllByKeyword(String keyword);
	List<InvoiceDetailEntity> findAllByInvoiceId(int invoiceId);
	List<InvoiceDetailEntity> findAllByKindOfTaxId(int invoiceId);
	List<InvoiceDetailDto> findAllByInvoiceIdGroupByKindOfTaxId(int id);

}