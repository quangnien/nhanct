package com.example.nhanct.repository;

import com.example.nhanct.entity.IssueInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	/*______________________ BEGIN REPORT FOR ISSUE ______________________*/
	/*______________________ BEGIN REPORT FOR ISSUE ______________________*/
	/*______________________ BEGIN REPORT FOR ISSUE ______________________*/
	/*______________________ BEGIN REPORT FOR ISSUE ______________________*/
	/*______________________ BEGIN REPORT FOR ISSUE ______________________*/
	@Query(value = "SELECT DISTINCT  ii.*" +
			" FROM issue_invoice ii" +
			" JOIN invoice i ON ii.id = i.issue_invoice_id" +
			" JOIN  invoice_type it ON it.id = ii.invoice_type_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" ORDER BY ii.date_of_registration ASC",
			nativeQuery = true)
	List<IssueInvoiceEntity> findAllReportIssueByInvoiceType(@Param("invoiceType") String invoiceType,
															 @Param("fromDate") String fromDate,
															 @Param("toDate") String toDate);

	@Query(value = "SELECT DISTINCT  ii.*" +
			" FROM issue_invoice ii" +
			" JOIN invoice i ON ii.id = i.issue_invoice_id" +
			" JOIN  invoice_type it ON it.id = ii.invoice_type_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" ORDER BY ii.date_of_registration ASC",
			nativeQuery = true)
	List<IssueInvoiceEntity> findAllReporIssuetByDate(@Param("fromDate") String fromDate,
													  @Param("toDate") String toDate);


}