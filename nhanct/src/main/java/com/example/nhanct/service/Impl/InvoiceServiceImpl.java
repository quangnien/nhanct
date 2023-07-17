//package com.example.nhanct.service.Impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import com.example.nhanct.entity.InvoiceEntity;
//import com.example.nhanct.entity.CustomerEntity;
//import com.example.nhanct.repository.InvoiceRepository;
//import com.example.nhanct.service.InvoiceService;
//
//
//@Service
//public class InvoiceServiceImpl implements InvoiceService{
//
//	@Autowired
//	private InvoiceRepository invoiceRepository;
//
//	@Override
//	public List<InvoiceEntity> findAll() {
//		return invoiceRepository.findAll();
//	}
//
//	@Override
//	public InvoiceEntity getById(int id) {
//		return invoiceRepository.findById(id).get();
//	}
//
//	@Override
//	public void add(InvoiceEntity invoice) {
//		invoiceRepository.save(invoice);
//
//	}
//
//	@Override
//	public void update(InvoiceEntity invoice) {
//		InvoiceEntity entity = invoiceRepository.findById(invoice.getId()).get();
//		if(entity != null) {
//
//			entity.setCustomer(invoice.getCustomer());
//			entity.setStatus(invoice.getStatus());
//			entity.setCustomerId(invoice.getCustomerId());
//			entity.setFlagCancel(invoice.getFlagCancel());
//			//entity.setThoiGianInvoice(invoice.getThoiGianInvoice());
//			invoiceRepository.save(entity);
//		}
//
//	}
//
//	@Override
//	public void delete(int id) {
//		invoiceRepository.deleteById(id);
//
//	}
//
//	@Override
//	public InvoiceEntity creat(InvoiceEntity invoice) {
//		return invoiceRepository.save(invoice);
//	}
//
//	@Override
//	public List<InvoiceEntity> findTop5byKhachhang(CustomerEntity customer) {
//		return invoiceRepository.findTop5ByCustomerOrderByIdDesc(customer);
//	}
//
//	@Override
//	public Page<InvoiceEntity> findAll(int pageNumber) {
//		Pageable pageable = PageRequest.of(pageNumber -1, 5);
//		return invoiceRepository.findAll(pageable);
//	}
//
//	@Override
//	public List<InvoiceEntity> findAll(String keyword) {
//		return invoiceRepository.findAll(keyword);
//	}
//
//
//
//}
