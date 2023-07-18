package com.example.nhanct.repository;

import com.example.nhanct.entity.InvoiceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceTypeRepository extends JpaRepository<InvoiceTypeEntity, Integer>{
}
