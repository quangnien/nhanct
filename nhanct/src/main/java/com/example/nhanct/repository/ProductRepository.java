//package com.example.nhanct.repository;
//
//import java.util.List;
//
//import com.example.nhanct.entity.BrandEntity;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.example.nhanct.entity.CategoryEntity;
//import com.example.nhanct.entity.ProductEntity;
//
//@Repository
//public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
//
//	@Query("SELECT s FROM ProductEntity s " +
//			" JOIN CategoryEntity c ON c.id = s.categoryId" +
//			" JOIN BrandEntity  b ON b.id = s.brandId" +
//			" WHERE s.productName LIKE %?1%" +
//			" OR c.categoryName LIKE %?1%" +
//			" OR b.brandName LIKE %?1%")
//	public List<ProductEntity> findAllByKeyword(String keyword);
//
//	List<ProductEntity> findByCategoryId(int id);
//
//	//paging by category
//	@Query("SELECT s FROM ProductEntity s WHERE s.categoryId = ?1")
//	public List<ProductEntity> findAllProductByCategory(int id, Pageable pageable);
//
//	List<ProductEntity> findTop3ByCategoryOrderByIdDesc(CategoryEntity category);
//	List<ProductEntity> findTop3ByBrandOrderByIdDesc(BrandEntity brand);
//	List<ProductEntity> findTop4ByCategoryOrderByIdDesc(CategoryEntity category);
//
//	@Query("SELECT s FROM ProductEntity s WHERE s.categoryId = ?1 ORDER BY s.quantity ASC")
//	Page<ProductEntity> findTop4ByCategoryOrderByQuantityDesc(int idCategory, Pageable pageable);
//
//	@Query("SELECT s FROM ProductEntity s WHERE s.brandId = ?1 ORDER BY s.quantity ASC")
//	Page<ProductEntity> findTop4ByBrandOrderByQuantityDesc(int idBrand, Pageable pageable);
//
////	@Query("SELECT s FROM ProductEntity s WHERE s.maDinhDanh IN ?1")
////	public List<ProductEntity> findByListMaDinhDanh(List<String> maDinhDanhList);
//
//}
