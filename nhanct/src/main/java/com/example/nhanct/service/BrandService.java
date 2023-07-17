//package com.example.nhanct.service;
//
//import com.example.nhanct.entity.BrandEntity;
//import org.springframework.data.domain.Page;
//
//import java.util.List;
//
//public interface BrandService {
//	List<BrandEntity> findAll();
//	BrandEntity getById(int id);
//	void add(BrandEntity brand);
//	void update(BrandEntity brand);
//	void delete(int id);
//
//	//paging
//	Page<BrandEntity> findAll(int pageNumber);
//
//	//seaching
//	List<BrandEntity> findAll(String keyword);
//
//	BrandEntity findByEmail(String email);
//	BrandEntity findByBrandName(String brandName);
//
//	public boolean isExceptionEmail(BrandEntity brand);
//
//
//}
