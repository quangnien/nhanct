package com.example.nhanct.service;

import com.example.nhanct.entity.KindOfTaxEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface KindOfTaxService {
	Page<KindOfTaxEntity> findAll(int pageNumber);
	List<KindOfTaxEntity> findAll();
}