package com.example.nhanct.repository;

import com.example.nhanct.entity.IssueInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueInvoiceRepository extends JpaRepository<IssueInvoiceEntity, Integer>{

//	@Query("SELECT s FROM IssueInvoiceEntity s " +
//			" JOIN CategoryEntity c ON c.id = s.categoryId" +
//			" JOIN BrandEntity  b ON b.id = s.brandId" +
//			" WHERE s.productName LIKE %?1%" +
//			" OR c.categoryName LIKE %?1%" +
//			" OR b.brandName LIKE %?1%")
//	public List<IssueInvoiceEntity> findAllByKeyword(String keyword);

//	List<IssueInvoiceEntity> findByCategoryId(int id);

	//paging by category
//	@Query("SELECT s FROM IssueInvoiceEntity s WHERE s.categoryId = ?1")
//	public List<IssueInvoiceEntity> findAllIssueInvoiceByCategory(int id, Pageable pageable);

}