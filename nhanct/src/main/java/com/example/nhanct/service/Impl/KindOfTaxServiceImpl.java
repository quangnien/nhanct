package com.example.nhanct.service.Impl;

import com.example.nhanct.entity.KindOfTaxEntity;
import com.example.nhanct.repository.KindOfTaxRepository;
import com.example.nhanct.service.KindOfTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KindOfTaxServiceImpl implements KindOfTaxService {

	@Autowired
	private KindOfTaxRepository kindOfTaxRepository;
    
	@Override
	public Page<KindOfTaxEntity> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return kindOfTaxRepository.findAll(pageable);
	}

	@Override
	public List<KindOfTaxEntity> findAll() {
		return kindOfTaxRepository.findAll();
	}
}