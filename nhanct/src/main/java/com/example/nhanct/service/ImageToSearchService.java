package com.example.nhanct.service;

import com.example.nhanct.entity.DetailImageEntity;
import com.example.nhanct.entity.ImageToSearch;

import java.util.List;


public interface ImageToSearchService {
	
	List<ImageToSearch> findAll();
	ImageToSearch getById(int id);
	void add(ImageToSearch imageToSearch);
	void delete(int id);
	
//	List<DetailImageEntity> getDetailImageByIdproduct(int id);
}
