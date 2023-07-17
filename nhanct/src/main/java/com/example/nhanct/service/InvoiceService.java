//package com.example.nhanct.service;
//
//import java.util.List;
//
//import org.springframework.data.domain.Page;
//
//import com.example.nhanct.entity.InvoiceEntity;
//import com.example.nhanct.entity.CustomerEntity;
//
//
//
//
//public interface InvoiceService {
//
//	List<InvoiceEntity> findAll();
//	InvoiceEntity getById(int id);
//	void add(InvoiceEntity invoice);
//	void update(InvoiceEntity invoice);
//	void delete(int id);
//
//	public InvoiceEntity creat(InvoiceEntity invoice);
//
//
//	//List<InvoiceEntity> findTop5bycustomer(CustomerEntity customer);
//	List<InvoiceEntity> findTop5byKhachhang(CustomerEntity customer);
//
//
//	//paging
//	Page<InvoiceEntity> findAll(int pageNumber);
//
//	//seaching
//	List<InvoiceEntity> findAll(String keyword);
//}
