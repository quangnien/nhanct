package com.example.nhanct.service;

import com.example.nhanct.entity.CustomerEntity;
import org.springframework.data.domain.Page;

public interface CustomerService {
	Page<CustomerEntity> findAll(int pageNumber);
}