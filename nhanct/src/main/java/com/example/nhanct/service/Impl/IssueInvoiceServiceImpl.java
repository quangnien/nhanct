//package com.example.nhanct.service.Impl;
//
//import com.example.nhanct.entity.IssueInvoiceEntity;
//import com.example.nhanct.repository.IssueInvoiceRepository;
//import com.example.nhanct.service.IssueInvoiceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class IssueInvoiceServiceImpl implements IssueInvoiceService{
//
//	@Autowired
//	private IssueInvoiceRepository productRepository ;
//
////	@Autowired
////	private SaleRepository saleRepositorye ;
//
//	@Override
//	public Page<IssueInvoiceEntity> findAll(int pageNumber) {
//
//		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
//		return productRepository.findAll(pageable);
//	}
//
//	@Override
//	public IssueInvoiceEntity getById(int id) {
//		return productRepository.findById(id).get();
//	}
//
//	@Override
//	public void add(IssueInvoiceEntity product) {
////		SaleEntity km = saleRepositorye.findById(product.getSaleId()).get();
////		if (LocalDate.now().isAfter(km.getStartDate())
////				&& LocalDate.now().isBefore(km.getEndDate())) {
////			product.setPriceActually(product.getPrice() - (km.getGiamGia()*product.getPrice())/100);
////		}
////		else product.setPriceActually(product.getPrice());
//		product.setPriceActually(product.getPrice());
//
//		product.setLastUpdate(LocalDateTime.now());
//		productRepository.save(product);
//	}
//
//	@Override
//	public void update(IssueInvoiceEntity product) {
//		IssueInvoiceEntity entity = productRepository.findById(product.getId()).get();
//		if(entity != null) {
////			SaleEntity km = saleRepositorye.findById(product.getSaleId()).get();
//			entity.setIssueInvoiceName(product.getIssueInvoiceName());
//			entity.setPrice(product.getPrice());
//			entity.setImage(product.getImage());
//			entity.setQuantity(product.getQuantity());
//			entity.setCategoryId(product.getCategoryId());
//			entity.setBrandId(product.getBrandId());
//			entity.setStatus(product.getStatus());
//			entity.setLastUpdate(LocalDateTime.now());
//			entity.setDes(product.getDes());
//
////			if (LocalDate.now().isAfter(km.getStartDate())
////					&& LocalDate.now().isBefore(km.getEndDate())) {
////				entity.setPriceActually(entity.getPrice() - (km.getGiamGia()*product.getPrice())/100);
////			}
////			else entity.setPriceActually(entity.getPrice());
//			entity.setPriceActually(entity.getPrice());
//
//			productRepository.save(entity);
//		}
//	}
//
//	@Override
//	public boolean delete(int id) {
//		productRepository.deleteById(id);
//		return true;
//	}
//
//	@Override
//	public List<IssueInvoiceEntity> findAllByKeyword(String keyword) {
//		return productRepository.findAllByKeyword(keyword);
//	}
//
//	@Override
//	public List<IssueInvoiceEntity> findAll() {
//		return productRepository.findAll();
//	}
//
////	@Override
////	public List<IssueInvoiceEntity> finByCategoryId(int id) {
////		return productRepository.findByCategoryId(id);
////	}
//
//}
