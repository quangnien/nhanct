package com.example.nhanct.service.Impl;

import com.example.nhanct.entity.BusinessEntity;
import com.example.nhanct.repository.BusinessRepository;
import com.example.nhanct.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	private BusinessRepository BusinessRepository;
    
	@Override
	public Page<BusinessEntity> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		return BusinessRepository.findAll(pageable);
	}

	@Override
	public List<BusinessEntity> findAll() {
		return BusinessRepository.findAll();
	}
}