//package com.example.nhanct.service.Impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.nhanct.entity.InvoiceEntity;
//import com.example.nhanct.entity.DetailInvoiceEntity;
//import com.example.nhanct.repository.DetailInvoiceRepository;
//import com.example.nhanct.service.DetailInvoiceService;
//
//@Service
//public class DetailInvoiceServiceImpl implements DetailInvoiceService{
//
//	@Autowired
//	private DetailInvoiceRepository detailInvoiceRepository;
//
//	@Override
//	public List<DetailInvoiceEntity> findAll() {
//		return detailInvoiceRepository.findAll();
//	}
//
//	@Override
//	public DetailInvoiceEntity getById(int id) {
//		return detailInvoiceRepository.findById(id).get();
//	}
//
//	@Override
//	public void add(DetailInvoiceEntity detailInvoice) {
//		detailInvoiceRepository.save(detailInvoice);
//
//	}
//
//	@Override
//	public void update(DetailInvoiceEntity detailInvoice) {
//		DetailInvoiceEntity entity = detailInvoiceRepository.findById(detailInvoice.getId()).get();
//		if(entity != null) {
//			entity.setQuantity(detailInvoice.getQuantity());
//			entity.setSumMoney(detailInvoice.getSumMoney());
//			entity.setInvoiceId(detailInvoice.getInvoiceId());
//			entity.setProductId(detailInvoice.getProductId());
//			detailInvoiceRepository.save(entity);
//		}
//
//	}
//
//	@Override
//	public void delete(int id) {
//		detailInvoiceRepository.deleteById(id);
//
//	}
//
//	@Override
//	public DetailInvoiceEntity creat(DetailInvoiceEntity detailInvoice) {
//		return detailInvoiceRepository.save(detailInvoice);
//	}
//
//	@Override
//	public List<DetailInvoiceEntity> findbyinvoice(InvoiceEntity id) {
//		return detailInvoiceRepository.findByInvoice(id);
//	}
//
//}
