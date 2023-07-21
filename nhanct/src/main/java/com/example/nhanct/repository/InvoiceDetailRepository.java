package com.example.nhanct.repository;

import com.example.nhanct.dto.InvoiceDetailDto;
import com.example.nhanct.entity.InvoiceDetailEntity;
import com.example.nhanct.entity.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetailEntity, Integer>{

	@Query("SELECT s, ip, k FROM InvoiceDetailEntity s " +
			" JOIN InvoiceEntity ip ON ip.id = s.invoiceId" +
			" JOIN KindOfTaxEntity k ON k.id = s.kindOfTaxId" +
			" WHERE s.itemName LIKE %?1%" +
			" OR k.codeOfTax LIKE %?1%")
    List<InvoiceDetailEntity> findAllByKeyword(String keyword);

//    @Query("SELECT s ,ip FROM InvoiceDetailEntity s " +
//            " JOIN InvoiceTypeEntity ip ON ip.id = s.invoiceTypeId")
//    List<InvoiceDetailEntity> findAll();

	@Query("SELECT s FROM InvoiceDetailEntity s " +
			" ORDER BY s.id DESC")
	List<InvoiceDetailEntity> findAllOrderByIdDesc();

	Page<InvoiceDetailEntity> findAllByInvoiceId(int invoiceId, Pageable pageable);
	List<InvoiceDetailEntity> findAllByInvoiceId(int invoiceId);
	List<InvoiceDetailEntity> findAllByKindOfTaxId(int invoiceId);

	@Modifying
	@Query("DELETE FROM InvoiceEntity WHERE id = ?1")
	void deleteByIdCustom(int id);

//	@Query("SELECT s.id, s.invoiceId, s.itemName" +
//			", s.kindOfTaxId, sum(s.priceAfterTax), sum(s.priceBeforeTax)" +
//			", sum(s.quantity), sum(s.sumMoneyAfterTax), s.dvt, sum(s.price), sum(s.priceOfTax)" +
//			"  FROM InvoiceDetailEntity s WHERE s.invoiceId = ?1 " +
//			"  GROUP BY s.kindOfTaxId")
	@Query("SELECT s.kindOfTaxId as kindOfTaxId, STR(sum(s.priceAfterTax)) as priceAfterTax,  " +
			"STR(sum(s.priceBeforeTax)) as priceBeforeTax, STR(sum(s.sumMoneyAfterTax)) as sumMoneyAfterTax" +
			"  FROM InvoiceDetailEntity s WHERE s.invoiceId = ?1 " +
			"  GROUP BY s.kindOfTaxId")
    List<InvoiceDetailDto> findAllByInvoiceIdGroupByKindOfTaxId(int id);


//	List<InvoiceDetailEntity> findByCategoryId(int id);

	//paging by category
//	@Query("SELECT s FROM InvoiceDetailEntity s WHERE s.categoryId = ?1")
//	public List<InvoiceDetailEntity> findAllInvoiceDetailByCategory(int id, Pageable pageable);

}