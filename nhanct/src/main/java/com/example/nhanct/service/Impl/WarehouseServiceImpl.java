package com.example.nhanct.service.Impl;

import com.example.nhanct.entity.WarehouseEntity;
import com.example.nhanct.repository.WarehouseRepository;
import com.example.nhanct.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseRepository WarehouseRepository;
    
	@Override
	public Page<WarehouseEntity> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		return WarehouseRepository.findAll(pageable);
	}
}