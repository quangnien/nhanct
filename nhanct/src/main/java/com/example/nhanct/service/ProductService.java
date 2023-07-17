//package com.example.nhanct.service;
//
//import java.util.List;
//
//import com.example.nhanct.entity.BrandEntity;
//import org.springframework.data.domain.Page;
//
//import com.example.nhanct.entity.CategoryEntity;
//import com.example.nhanct.entity.ProductEntity;
//
//
//public interface ProductService {
//
//	List<ProductEntity> findAll();
//	Page<ProductEntity> findAll(int pageNumber);
//	ProductEntity getById(int id);
//	void add(ProductEntity product);
//	void update(ProductEntity product);
//	boolean delete(int id);
//
//	List<ProductEntity> findAllByKeyword(String keyword);
//
//	List<ProductEntity> finByCategoryId(int id);
//
//	//paging by category
//	Page<ProductEntity> getAllProductByCategory(int id, int pageNumber);
//
//	List<ProductEntity> findTop3byCategory(CategoryEntity category);
//	List<ProductEntity> findTop3ByBrand(BrandEntity brand);
//	List<ProductEntity> findTop4byCategory(CategoryEntity category);
//	Page<ProductEntity> findTop4ByCategoryOrderBySoluongDesc(int idCategory, int pageNumber);
//	Page<ProductEntity> findTop4ByBrandOrderBySoluongDesc(int idBrand, int pageNumber);
//
////	List<ProductEntity> findByListMaDinhDanh(List<String> maDinhDanhList);
//}
