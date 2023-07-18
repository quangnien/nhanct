package com.example.nhanct.service;

import com.example.nhanct.entity.WarehouseEntity;
import org.springframework.data.domain.Page;

public interface WarehouseService {
	Page<WarehouseEntity> findAll(int pageNumber);
}