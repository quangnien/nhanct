package com.example.nhanct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nhanct.entity.InvoiceEntity;
import com.example.nhanct.entity.DetailInvoiceEntity;

@Repository
public interface DetailInvoiceRepository extends JpaRepository<DetailInvoiceEntity, Integer>{
	
	List<DetailInvoiceEntity> findByInvoice(InvoiceEntity invoice);
	
}
