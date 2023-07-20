package com.example.nhanct.service.Impl;

import com.example.nhanct.entity.CustomerEntity;
import com.example.nhanct.repository.CustomerRepository;
import com.example.nhanct.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository CustomerRepository;
    
	@Override
	public Page<CustomerEntity> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		return CustomerRepository.findAll(pageable);
	}

	@Override
	public List<CustomerEntity> findAll() {
		return CustomerRepository.findAll();
	}
}