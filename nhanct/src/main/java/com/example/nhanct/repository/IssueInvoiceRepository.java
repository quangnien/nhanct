package com.example.nhanct.repository;

import com.example.nhanct.entity.IssueInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueInvoiceRepository extends JpaRepository<IssueInvoiceEntity, Integer>{

//	@Query("SELECT s, ip FROM IssueInvoiceEntity s " +
//			" JOIN InvoiceTypeEntity ip ON ip.id = s.invoiceTypeId" +
//			" WHERE STR(s.currentInvoiceNumber) = ?1" +
//			" OR s.mst LIKE %?1%" +
//			" OR s.symbol LIKE %?1%" +
//			" OR STR(s.quantity) = ?1" +
//			" OR ip.codeOfInvoiceType LIKE %?1%")
	@Query("SELECT s FROM IssueInvoiceEntity s " +
		" WHERE s.symbol LIKE %?1%")
    List<IssueInvoiceEntity> findAllByKeyword(String keyword);

    @Query("SELECT s ,ip FROM IssueInvoiceEntity s " +
            " JOIN InvoiceTypeEntity ip ON ip.id = s.invoiceTypeId")
    List<IssueInvoiceEntity> findAll();

	@Query("SELECT s FROM IssueInvoiceEntity s " +
			" ORDER BY s.id DESC")
	List<IssueInvoiceEntity> findAllOrderByIdDesc();

	@Query("SELECT s FROM IssueInvoiceEntity s " +
			" JOIN InvoiceTypeEntity i ON s.invoiceTypeId = i.id" +
			" WHERE i.codeOfInvoiceType LIKE ?1" +
			" ORDER BY s.id DESC")
	List<IssueInvoiceEntity> findAllByInvoiceTypeOrderByIdDesc(String codeInvoiceType);

	@Query("SELECT s FROM IssueInvoiceEntity s " +
			" ORDER BY s.dateOfRegistration")
	List<IssueInvoiceEntity> findAllOrderByDate();

//	List<IssueInvoiceEntity> findByCategoryId(int id);

	//paging by category
//	@Query("SELECT s FROM IssueInvoiceEntity s WHERE s.categoryId = ?1")
//	public List<IssueInvoiceEntity> findAllIssueInvoiceByCategory(int id, Pageable pageable);

}