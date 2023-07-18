package com.example.nhanct.service;

import com.example.nhanct.entity.InvoiceTypeEntity;
import org.springframework.data.domain.Page;

public interface InvoiceTypeService {
	Page<InvoiceTypeEntity> findAll(int pageNumber);
}
