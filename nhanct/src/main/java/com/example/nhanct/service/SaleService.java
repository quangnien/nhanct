//package com.example.nhanct.service;
//
//import java.util.List;
//
//import org.springframework.data.domain.Page;
//
//import com.example.nhanct.entity.SaleEntity;
//
//public interface SaleService {
//
//	List<SaleEntity> findAll();
//	SaleEntity getById(int id);
//	boolean add(SaleEntity sale);
//	boolean update(SaleEntity sale);
//	void delete(int id);
//
//	// bắt lỗi ngày thágn
//	public boolean isException(SaleEntity sale);
//
//	//paging
//	Page<SaleEntity> findAll(int pageNumber);
//
//	//seaching
//	List<SaleEntity> findAll(String keyword);
//
////	BrandEntity findByEmail(String email);
//}
