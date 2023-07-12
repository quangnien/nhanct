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
//import com.example.nhanct.entity.SaleEntity;
//import com.example.nhanct.repository.SaleRepository;
//import com.example.nhanct.service.SaleService;
//
//@Service
//public class SaleServiceImpl implements SaleService {
//
//	@Autowired
//	private SaleRepository saleRepository;
//
//	@Override
//	public List<SaleEntity> findAll() {
//		return saleRepository.findAll();
//	}
//
//	@Override
//	public SaleEntity getById(int id) {
//		return saleRepository.findById(id).get();
//	}
//
//	@Override
//	public boolean add(SaleEntity sale) {
//
//		if (sale.getStartDate().isAfter(sale.getEndDate())
//				|| sale.getStartDate().isEqual(sale.getEndDate())) {
//			System.out.println("ERROR");
//			return false;
//		}
//		else {
//			saleRepository.save(sale);
//			return true;
//		}
//	}
//
//	@Override
//	public boolean update(SaleEntity sale) {
//		SaleEntity entity = saleRepository.findById(sale.getId()).get();
//
//		if (entity.getStartDate().isAfter(entity.getEndDate())
//				|| entity.getStartDate().isEqual(entity.getEndDate())) {
//			System.out.println("ERROR");
//			return false;
//		}
//		else if (entity != null) {
//			entity.setEventName(sale.getEventName());
//			entity.setGiamGia(sale.getGiamGia());
//			entity.setStartDate(sale.getStartDate());
//			entity.setEndDate(sale.getEndDate());
//
//			saleRepository.save(entity);
//		}
//		return true;
//	}
//
//	@Override
//	public void delete(int id) {
//		saleRepository.deleteById(id);
//	}
//
//	@Override
//	public Page<SaleEntity> findAll(int pageNumber) {
//		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
//		return saleRepository.findAll(pageable);
//	}
//
//	@Override
//	public boolean isException(SaleEntity sale) {
//		boolean ex = false;
//		// check time input
//		if ( sale.getStartDate()==null
//			|| sale.getEndDate()==null
//			|| sale.getStartDate().isAfter(sale.getEndDate())
//			|| sale.getStartDate().isEqual(sale.getEndDate()))
//			ex = true;
//		return ex;
//	}
//
//	@Override
//	public List<SaleEntity> findAll(String keyword) {
//		return saleRepository.findAll(keyword);
//	}
//
////	@Override
////	public List<SaleEntity> findAll(String keyword) {
////		if(keyword != null) {
//////			return saleRepository.findAll(keyword);
////			return null;
////		}
////		return saleRepository.findAll();
////	}
//
//}
