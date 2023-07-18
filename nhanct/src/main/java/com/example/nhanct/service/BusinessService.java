package com.example.nhanct.service;

import com.example.nhanct.entity.BusinessEntity;
import org.springframework.data.domain.Page;

public interface BusinessService {
	Page<BusinessEntity> findAll(int pageNumber);
}