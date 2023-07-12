package com.example.nhanct.service;

import java.util.List;

import com.example.nhanct.entity.DetailImageEntity;


public interface DetailImageService {
	
	List<DetailImageEntity> findAll();
	DetailImageEntity getById(int id);
	void add(DetailImageEntity detailImage);
	void delete(int id);
	
	List<DetailImageEntity> getDetailImageByIdproduct(int id);
}
