package com.example.nhanct.service;

import com.example.nhanct.entity.BusinessEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BusinessService {
	Page<BusinessEntity> findAll(int pageNumber);
	List<BusinessEntity> findAll();
}