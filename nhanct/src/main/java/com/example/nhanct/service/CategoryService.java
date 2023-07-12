package com.example.nhanct.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.nhanct.dto.CategoryDto;
import com.example.nhanct.entity.CategoryEntity;

public interface CategoryService {
		
	List<CategoryEntity> findAll();
	CategoryEntity getById(int id);
	void add(CategoryEntity category);
	void update(CategoryEntity category);
	void delete(int id);
	
	//paging
	Page<CategoryEntity> findAll(int pageNumber);
	
	//seaching
	List<CategoryEntity> findAll(String keyword);
	
	//làm paging theo a Lâm 
	List<CategoryDto> findAll(Pageable pageable);
	int getTotalItem();
	
	CategoryEntity findByCategory (String categoryName);
}
