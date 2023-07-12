package com.example.nhanct.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nhanct.entity.DetailImageEntity;
import com.example.nhanct.repository.DetailImageRepository;
import com.example.nhanct.service.DetailImageService;

@Service
public class DetailImageServiceImpl implements DetailImageService{

	@Autowired
	private DetailImageRepository detailImageRepository ;
	
	@Override
	public List<DetailImageEntity> findAll() {
		return detailImageRepository.findAll();
	}

	@Override
	public DetailImageEntity getById(int id) {
		return detailImageRepository.findById(id).get();
	}

	@Override
	public void add(DetailImageEntity detailImage) {
		detailImageRepository.save(detailImage);
	}

	@Override
	public void delete(int id) {
		detailImageRepository.deleteById(id);
	}

	@Override
	public List<DetailImageEntity> getDetailImageByIdproduct(int id) {
		return detailImageRepository.findDetailImageByProductId(id);
	}

}
