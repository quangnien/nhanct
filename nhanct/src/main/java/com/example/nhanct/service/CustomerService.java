package com.example.nhanct.service;

import com.example.nhanct.entity.CustomerEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
	Page<CustomerEntity> findAll(int pageNumber);
	List<CustomerEntity> findAll();
}