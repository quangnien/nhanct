package com.example.nhanct.repository;

import com.example.nhanct.entity.InvoiceEntity;
import com.example.nhanct.entity.IssueInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	@Query(value = "SELECT" +
			" ii.symbol" +
			" , i.id " +
			" , i.number_of_invoice " +
			" , i.issue_date " +
			" , i.release_date " +
			" , i.status " +
			" , i.reason_for_cancellation " +
			" , i.sum_price " +
			" , i.symbol " +
			" , i.flag_invoice_type " +
			" , i.cancel_date " +
			" , i.status_present " +
			" , i.invoice_type " +
			" , i.mst " +
			" , i.customer_name " +
			" , i.phone " +
			" , i.address " +
			" , i.business_id " +
			" , i.issue_invoice_id " +
			" , i.customer_id " +
			" , i.input_warehouse_id " +
			" , i.output_warehouse_id " +
			" , i.issuer_id " +
			" , i.releaser_id " +

//			" , i.issue_quantity " +
//			" , i.issue_from_number " +
//			" , i.issue_to_number " +
//			" , i.issue_number_of_invoice " +
//			" , i.issue_number_empty " +

			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" ORDER BY i.date_present ASC" +
			" LIMIT 1" +
			" ) AS from_number" +
			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" ORDER BY i.date_present DESC" +
			" LIMIT 1" +
			" ) AS to_number," +
			" " +
			" SUM(id.price_of_tax) as price_of_tax, sum(id.price_after_tax) as price_after_tax," +
			" sum(id.price_before_tax) as price_before_tax, sum(id.quantity) as quantity," +
			"  i.date_present" +
			" " +
			" FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" GROUP BY i.date_present" +
			" ORDER BY i.date_present ASC"
			, nativeQuery = true)
	List<InvoiceEntity> findAllReportByInvoiceType(@Param("invoiceType") String invoiceType,
												   @Param("fromDate") String fromDate,
												   @Param("toDate") String toDate);
	@Query(value = "SELECT" +
			" ii.symbol" +
			" , i.id " +
			" , i.number_of_invoice " +
			" , i.issue_date " +
			" , i.release_date " +
			" , i.status " +
			" , i.reason_for_cancellation " +
			" , i.sum_price " +
			" , i.symbol " +
			" , i.flag_invoice_type " +
			" , i.cancel_date " +
			" , i.status_present " +
			" , i.invoice_type " +
			" , i.mst " +
			" , i.customer_name " +
			" , i.phone " +
			" , i.address " +
			" , i.business_id " +
			" , i.issue_invoice_id " +
			" , i.customer_id " +
			" , i.input_warehouse_id " +
			" , i.output_warehouse_id " +
			" , i.issuer_id " +
			" , i.releaser_id " +

//			" , i.issue_quantity " +
//			" , i.issue_from_number " +
//			" , i.issue_to_number " +
//			" , i.issue_number_of_invoice " +
//			" , i.issue_number_empty " +

			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" AND i.status = :status" +
			" ORDER BY i.date_present ASC" +
			" LIMIT 1" +
			" ) AS from_number" +
			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" AND i.status = :status" +
			" ORDER BY i.date_present DESC" +
			" LIMIT 1" +
			" ) AS to_number," +
			" " +
			" SUM(id.price_of_tax) as price_of_tax, sum(id.price_after_tax) as price_after_tax," +
			" sum(id.price_before_tax) as price_before_tax, sum(id.quantity) as quantity," +
			"  i.date_present" +
			" " +
			" FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" AND i.status = :status" +
			" GROUP BY i.date_present" +
			" ORDER BY i.date_present ASC"
			, nativeQuery = true)
	List<InvoiceEntity> findAllReportByInvoiceTypeAndStatus(@Param("invoiceType") String invoiceType,
															@Param("status") String status,
															@Param("fromDate") String fromDate,
															@Param("toDate") String toDate);
	@Query(value = "SELECT" +
			" ii.symbol" +
			" , i.id " +
			" , i.number_of_invoice " +
			" , i.issue_date " +
			" , i.release_date " +
			" , i.status " +
			" , i.reason_for_cancellation " +
			" , i.sum_price " +
			" , i.symbol " +
			" , i.flag_invoice_type " +
			" , i.cancel_date " +
			" , i.status_present " +
			" , i.invoice_type " +
			" , i.mst " +
			" , i.customer_name " +
			" , i.phone " +
			" , i.address " +
			" , i.business_id " +
			" , i.issue_invoice_id " +
			" , i.customer_id " +
			" , i.input_warehouse_id " +
			" , i.output_warehouse_id " +
			" , i.issuer_id " +
			" , i.releaser_id " +

//			" , i.issue_quantity " +
//			" , i.issue_from_number " +
//			" , i.issue_to_number " +
//			" , i.issue_number_of_invoice " +
//			" , i.issue_number_empty " +

			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" AND kot.code_of_tax = :kindOfTax" +
			" ORDER BY i.date_present ASC" +
			" LIMIT 1" +
			" ) AS from_number" +
			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" AND kot.code_of_tax = :kindOfTax" +
			" ORDER BY i.date_present DESC" +
			" LIMIT 1" +
			" ) AS to_number," +
			" " +
			" SUM(id.price_of_tax) as price_of_tax, sum(id.price_after_tax) as price_after_tax," +
			" sum(id.price_before_tax) as price_before_tax, sum(id.quantity) as quantity," +
			"  i.date_present" +
			" " +
			" FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" AND kot.code_of_tax = :kindOfTax" +
			" GROUP BY i.date_present" +
			" ORDER BY i.date_present ASC"
			, nativeQuery = true)
	List<InvoiceEntity> findAllReportByInvoiceTypeAndKindOfTax(@Param("invoiceType") String invoiceType,
															   @Param("kindOfTax") String kindOfTax,
															   @Param("fromDate") String fromDate,
															   @Param("toDate") String toDate);
	@Query(value = "SELECT" +
			" ii.symbol" +
			" , i.id " +
			" , i.number_of_invoice " +
			" , i.issue_date " +
			" , i.release_date " +
			" , i.status " +
			" , i.reason_for_cancellation " +
			" , i.sum_price " +
			" , i.symbol " +
			" , i.flag_invoice_type " +
			" , i.cancel_date " +
			" , i.status_present " +
			" , i.invoice_type " +
			" , i.mst " +
			" , i.customer_name " +
			" , i.phone " +
			" , i.address " +
//			" , i.date_present " +
//			" , i.priceOfTax " +
//			" , i.priceBeforeTax " +
//			" , i.priceAfterTax " +
//			" , i.fromNumber " +
//			" , i.toNumber " +
			" , i.business_id " +
			" , i.issue_invoice_id " +
			" , i.customer_id " +
			" , i.input_warehouse_id " +
			" , i.output_warehouse_id " +
			" , i.issuer_id " +
			" , i.releaser_id " +

//			" , i.issue_quantity " +
//			" , i.issue_from_number " +
//			" , i.issue_to_number " +
//			" , i.issue_number_of_invoice " +
//			" , i.issue_number_empty " +

			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" AND i.status = :status" +
			" AND kot.code_of_tax = :kindOfTax" +
			" ORDER BY i.date_present ASC" +
			" LIMIT 1" +
			" ) AS from_number" +
			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" AND i.status = :status" +
			" AND kot.code_of_tax = :kindOfTax" +
			" ORDER BY i.date_present DESC" +
			" LIMIT 1" +
			" ) AS to_number," +
			" " +
			" SUM(id.price_of_tax) as price_of_tax, sum(id.price_after_tax) as price_after_tax," +
			" sum(id.price_before_tax) as price_before_tax, sum(id.quantity) as quantity," +
			"  i.date_present" +
			" " +
			" FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND  i.flag_invoice_type = :invoiceType" +
			" AND i.status = :status" +
			" AND kot.code_of_tax = :kindOfTax" +
			" GROUP BY i.date_present" +
			" ORDER BY i.date_present ASC"
			, nativeQuery = true)
	List<InvoiceEntity> findAllReportByInvoiceTypeAndStatusAndKindOfTax(@Param("invoiceType") String invoiceType,
																		@Param("status") String status,
																		@Param("kindOfTax") String kindOfTax,
																		@Param("fromDate") String fromDate,
																		@Param("toDate") String toDate
																			  );
	/*_____Status____*/
	@Query(value = "SELECT" +
			" ii.symbol" +
			" , i.id " +
			" , i.number_of_invoice " +
			" , i.issue_date " +
			" , i.release_date " +
			" , i.status " +
			" , i.reason_for_cancellation " +
			" , i.sum_price " +
			" , i.symbol " +
			" , i.flag_invoice_type " +
			" , i.cancel_date " +
			" , i.status_present " +
			" , i.invoice_type " +
			" , i.mst " +
			" , i.customer_name " +
			" , i.phone " +
			" , i.address " +
			" , i.business_id " +
			" , i.issue_invoice_id " +
			" , i.customer_id " +
			" , i.input_warehouse_id " +
			" , i.output_warehouse_id " +
			" , i.issuer_id " +
			" , i.releaser_id " +

//			" , i.issue_quantity " +
//			" , i.issue_from_number " +
//			" , i.issue_to_number " +
//			" , i.issue_number_of_invoice " +
//			" , i.issue_number_empty " +

			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND i.status = :status" +
			" ORDER BY i.date_present ASC" +
			" LIMIT 1" +
			" ) AS from_number" +
			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND i.status = :status" +
			" ORDER BY i.date_present DESC" +
			" LIMIT 1" +
			" ) AS to_number," +
			" " +
			" SUM(id.price_of_tax) as price_of_tax, sum(id.price_after_tax) as price_after_tax," +
			" sum(id.price_before_tax) as price_before_tax, sum(id.quantity) as quantity," +
			"  i.date_present" +
			" " +
			" FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND i.status = :status" +
			" GROUP BY i.date_present" +
			" ORDER BY i.date_present ASC"
			, nativeQuery = true)
	List<InvoiceEntity> findAllReportByStatus(@Param("status") String status,
											  @Param("fromDate") String fromDate,
											  @Param("toDate") String toDate);
	@Query(value = "SELECT" +
			" ii.symbol" +
			" , i.id " +
			" , i.number_of_invoice " +
			" , i.issue_date " +
			" , i.release_date " +
			" , i.status " +
			" , i.reason_for_cancellation " +
			" , i.sum_price " +
			" , i.symbol " +
			" , i.flag_invoice_type " +
			" , i.cancel_date " +
			" , i.status_present " +
			" , i.invoice_type " +
			" , i.mst " +
			" , i.customer_name " +
			" , i.phone " +
			" , i.address " +
			" , i.business_id " +
			" , i.issue_invoice_id " +
			" , i.customer_id " +
			" , i.input_warehouse_id " +
			" , i.output_warehouse_id " +
			" , i.issuer_id " +
			" , i.releaser_id " +

//			" , i.issue_quantity " +
//			" , i.issue_from_number " +
//			" , i.issue_to_number " +
//			" , i.issue_number_of_invoice " +
//			" , i.issue_number_empty " +

			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND i.status = :status" +
			" AND kot.code_of_tax = :kindOfTax" +
			" ORDER BY i.date_present ASC" +
			" LIMIT 1" +
			" ) AS from_number" +
			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND i.status = :status" +
			" AND kot.code_of_tax = :kindOfTax" +
			" ORDER BY i.date_present DESC" +
			" LIMIT 1" +
			" ) AS to_number," +
			" " +
			" SUM(id.price_of_tax) as price_of_tax, sum(id.price_after_tax) as price_after_tax," +
			" sum(id.price_before_tax) as price_before_tax, sum(id.quantity) as quantity," +
			"  i.date_present" +
			" " +
			" FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND i.status = :status" +
			" AND kot.code_of_tax = :kindOfTax" +
			" GROUP BY i.date_present" +
			" ORDER BY i.date_present ASC"
			, nativeQuery = true)
	List<InvoiceEntity> findAllReportByStatusAndKindOfTax(@Param("status") String status,
														  @Param("kindOfTax") String kindOfTax,
														  @Param("fromDate") String fromDate,
														  @Param("toDate") String toDate);
	/*_____KindOfTax____*/
	@Query(value = "SELECT" +
			" ii.symbol" +
			" , i.id " +
			" , i.number_of_invoice " +
			" , i.issue_date " +
			" , i.release_date " +
			" , i.status " +
			" , i.reason_for_cancellation " +
			" , i.sum_price " +
			" , i.symbol " +
			" , i.flag_invoice_type " +
			" , i.cancel_date " +
			" , i.status_present " +
			" , i.invoice_type " +
			" , i.mst " +
			" , i.customer_name " +
			" , i.phone " +
			" , i.address " +
			" , i.business_id " +
			" , i.issue_invoice_id " +
			" , i.customer_id " +
			" , i.input_warehouse_id " +
			" , i.output_warehouse_id " +
			" , i.issuer_id " +
			" , i.releaser_id " +

//			" , i.issue_quantity " +
//			" , i.issue_from_number " +
//			" , i.issue_to_number " +
//			" , i.issue_number_of_invoice " +
//			" , i.issue_number_empty " +

			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND kot.code_of_tax = :kindOfTax" +
			" ORDER BY i.date_present ASC" +
			" LIMIT 1" +
			" ) AS from_number" +
			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND kot.code_of_tax = :kindOfTax" +
			" ORDER BY i.date_present DESC" +
			" LIMIT 1" +
			" ) AS to_number," +
			" " +
			" SUM(id.price_of_tax) as price_of_tax, sum(id.price_after_tax) as price_after_tax," +
			" sum(id.price_before_tax) as price_before_tax, sum(id.quantity) as quantity," +
			"  i.date_present" +
			" " +
			" FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" AND kot.code_of_tax = :kindOfTax" +
			" GROUP BY i.date_present" +
			" ORDER BY i.date_present ASC"
			, nativeQuery = true)
	List<InvoiceEntity> findAllReportByKindOfTax(@Param("kindOfTax") String kindOfTax,
												 @Param("fromDate") String fromDate,
												 @Param("toDate") String toDate);
	@Query(value = "SELECT" +
			" ii.symbol" +
			" , i.id " +
			" , i.number_of_invoice " +
			" , i.issue_date " +
			" , i.release_date " +
			" , i.status " +
			" , i.reason_for_cancellation " +
			" , i.sum_price " +
			" , i.symbol " +
			" , i.flag_invoice_type " +
			" , i.cancel_date " +
			" , i.status_present " +
			" , i.invoice_type " +
			" , i.mst " +
			" , i.customer_name " +
			" , i.phone " +
			" , i.address " +
			" , i.business_id " +
			" , i.issue_invoice_id " +
			" , i.customer_id " +
			" , i.input_warehouse_id " +
			" , i.output_warehouse_id " +
			" , i.issuer_id " +
			" , i.releaser_id " +

//			" , i.issue_quantity " +
//			" , i.issue_from_number " +
//			" , i.issue_to_number " +
//			" , i.issue_number_of_invoice " +
//			" , i.issue_number_empty " +

			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" ORDER BY i.date_present ASC" +
			" LIMIT 1" +
			" ) AS from_number" +
			" , (" +
			" SELECT i.number_of_invoice FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" ORDER BY i.date_present DESC" +
			" LIMIT 1" +
			" ) AS to_number," +
			" " +
			" SUM(id.price_of_tax) as price_of_tax, sum(id.price_after_tax) as price_after_tax," +
			" sum(id.price_before_tax) as price_before_tax, sum(id.quantity) as quantity," +
			"  i.date_present" +
			" " +
			" FROM invoice i" +
			" JOIN issue_invoice ii ON ii.id = i.issue_invoice_id" +
			" JOIN invoice_type it ON it.id = ii.invoice_type_id" +
			" JOIN invoice_detail id ON id.invoice_id = i.id" +
			" JOIN kind_of_tax kot ON kot.id = id.kind_of_tax_id" +
			" WHERE i.date_present BETWEEN CAST(:fromDate AS DATETIME) AND CAST(:toDate AS DATETIME)" +
			" GROUP BY i.date_present" +
			" ORDER BY i.date_present ASC"
			, nativeQuery = true)
	List<InvoiceEntity> findAllReportByDate(@Param("fromDate") String fromDate,
											@Param("toDate") String toDate);

}