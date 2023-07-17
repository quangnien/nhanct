//package com.example.nhanct.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.example.nhanct.entity.BrandEntity;
//
//@Repository
//public interface BrandRepository extends JpaRepository<BrandEntity, Integer>{
//
//	@Query("SELECT s FROM BrandEntity s WHERE s.brandName LIKE %?1%" +
//			" OR s.address LIKE %?1%" +
//			" OR s.email LIKE %?1%" +
//			" OR s.phone LIKE %?1%")
//	List<BrandEntity> findAll(String keyword);
//
//	BrandEntity findByEmail(String email);
//	BrandEntity findByBrandName(String brandName);
//
//}
