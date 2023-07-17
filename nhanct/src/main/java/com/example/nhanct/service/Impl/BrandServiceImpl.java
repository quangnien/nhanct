//package com.example.nhanct.service.Impl;
//
//import com.example.nhanct.entity.BrandEntity;
//import com.example.nhanct.repository.BrandRepository;
//import com.example.nhanct.service.BrandService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BrandServiceImpl implements BrandService {
//
//	@Autowired
//	private BrandRepository brandRepository;
//
//	@Override
//	public List<BrandEntity> findAll() {
//		return brandRepository.findAll();
//	}
//
//	@Override
//	public BrandEntity getById(int id) {
//		return brandRepository.findById(id).get();
//	}
//
//	@Override
//	public void add(BrandEntity brand) {
//		brandRepository.save(brand);
//	}
//
//	@Override
//	public void update(BrandEntity brand) {
//		BrandEntity entity = brandRepository.findById(brand.getId()).get();
//
//		if (entity != null) {
//			entity.setBrandName(brand.getBrandName());
//			entity.setPhone(brand.getPhone());
//			entity.setAddress(brand.getAddress());
//			entity.setEmail(brand.getEmail());
//			brandRepository.save(entity);
//		}
//	}
//
//	@Override
//	public void delete(int id) {
//		brandRepository.deleteById(id);
//	}
//
//	@Override
//	public Page<BrandEntity> findAll(int pageNumber) {
//		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
//		return brandRepository.findAll(pageable);
//	}
//
//	@Override
//	public List<BrandEntity> findAll(String keyword) {
//		return brandRepository.findAll(keyword);
//	}
//
//	@Override
//	public BrandEntity findByEmail(String email) {
//		return brandRepository.findByEmail(email);
//	}
//
//	@Override
//	public BrandEntity findByBrandName(String brandName) {
//		return brandRepository.findByBrandName(brandName);
//	}
//
//	@Override
//	public boolean isExceptionEmail(BrandEntity brand) {
//		String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//	            + "gmail.com";
//	    String email1 = brand.getEmail();
//	    Boolean b = email1.matches(EMAIL_REGEX);
//	    if(b == true) return false;
//	    else return true;
//	}
//
//}
