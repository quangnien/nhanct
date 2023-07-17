//package com.example.nhanct.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.example.nhanct.entity.InvoiceEntity;
//import com.example.nhanct.entity.CustomerEntity;
//
//@Repository
//public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer>{
//
//	List<InvoiceEntity> findTop5ByCustomerOrderByIdDesc(CustomerEntity customer);
//
//	//seaching
//	@Query("SELECT s FROM InvoiceEntity s WHERE s.status LIKE %?1%")
//	public List<InvoiceEntity> findAll(String keyword);
//}
