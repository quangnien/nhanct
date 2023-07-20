package com.example.nhanct.repository;

import com.example.nhanct.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer>{

	@Query("SELECT s FROM InvoiceEntity s " +
			" LEFT JOIN BusinessEntity b ON b.id = s.businessId" +
			" LEFT JOIN CustomerEntity c ON c.id = s.customerId" +
			" LEFT JOIN WarehouseEntity wi ON wi.id = s.inputWarehouseId" +
			" LEFT JOIN WarehouseEntity wo ON wo.id = s.outputWarehouseId" +
			" WHERE s.reasonForCancellation LIKE %?1%" +
			" OR b.businessName LIKE %?1%"+
			" OR c.customerName LIKE %?1%"+
			" OR wi.warehouseName LIKE %?1%"+
			" OR wo.warehouseName LIKE %?1%"+
			" OR s.status LIKE %?1%")
    List<InvoiceEntity> findAllByKeyword(String keyword);

//    @Query("SELECT s ,ip FROM InvoiceEntity s " +
//            " JOIN InvoiceTypeEntity ip ON ip.id = s.invoiceTypeId")
	@Query("SELECT s FROM InvoiceEntity s ")
    List<InvoiceEntity> findAll();

//	List<InvoiceEntity> findByCategoryId(int id);

	//paging by category
//	@Query("SELECT s FROM InvoiceEntity s WHERE s.categoryId = ?1")
//	public List<InvoiceEntity> findAllInvoiceByCategory(int id, Pageable pageable);

}