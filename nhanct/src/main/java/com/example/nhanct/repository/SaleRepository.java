//package com.example.nhanct.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.example.nhanct.entity.SaleEntity;
//
//@Repository
//public interface SaleRepository extends JpaRepository<SaleEntity, Integer>{
//
//	@Query("SELECT s FROM SaleEntity s WHERE s.eventName LIKE %?1%")
//	public List<SaleEntity> findAll(String keyword);
//
//}
