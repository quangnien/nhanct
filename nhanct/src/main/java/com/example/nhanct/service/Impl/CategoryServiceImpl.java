//package com.example.nhanct.service.Impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import com.example.nhanct.dto.CategoryDto;
//import com.example.nhanct.entity.CategoryEntity;
//import com.example.nhanct.repository.CategoryRepository;
//import com.example.nhanct.service.CategoryService;
//
//@Service
//public class CategoryServiceImpl implements CategoryService {
//
//	@Autowired
//	private CategoryRepository categoryRepository;
//
//	@Override
//	public List<CategoryEntity> findAll() {
//		return categoryRepository.findAll();
//	}
//
//	@Override
//	public CategoryEntity getById(int id) {
//		return categoryRepository.findById(id).get();
//	}
//
//	@Override
//	public void add(CategoryEntity category) {
//		categoryRepository.save(category);
//	}
//
//	@Override
//	public void update(CategoryEntity category) {
//		CategoryEntity entity = categoryRepository.findById(category.getId()).get();
//		if(entity != null) {
//			entity.setCategoryName(category.getCategoryName());
//			categoryRepository.save(entity);
//		}
//	}
//
//	@Override
//	public void delete(int id) {
//		categoryRepository.deleteById(id);
//	}
//
//	@Override
//	public List<CategoryEntity> findAll(String keyword) {
//		return categoryRepository.findAll(keyword);
//	}
//
//	@Override
//	public Page<CategoryEntity> findAll(int pageNumber) {
//		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
//		return categoryRepository.findAll(pageable);
//	}
//
//	@Override
//	public List<CategoryDto> findAll(Pageable pageable) {
//		//khi we làm việc vs JPA thì khi mà data đổ lên thì nó đổ lên entity trước
//		//h nv của we là từ entity đổ ngược lại thằng ...Model thôi (cụ thể ở đây là đổ lại thằng Newmodel)
//		//(hiểu đơn giản là bên servlet thì nó trả cái resourset còn bên spring mvc thì trả cái entity)
//		List<CategoryDto> models = new ArrayList<>();
//		List<CategoryEntity> entities = categoryRepository.findAll(pageable).getContent();
//		for (CategoryEntity item: entities) {
//			//convert thủ công
//			CategoryDto categoryDto = new CategoryDto();
//			categoryDto.setCategoryName(item.getCategoryName());
//			models.add(categoryDto);
//
//			//tạo pagage converter riêng, coi nó như 1 chức năng và use thôi :) :
////			NewDTO newDTO = newConverter.toDto(item);
////			models.add(newDTO);
//		}
//		return models;
//	}
//
//	@Override
//	public int getTotalItem() {
//		return (int) categoryRepository.count();
//	}
//
//	@Override
//	public CategoryEntity findByCategory(String categoryName) {
//		return categoryRepository.findByCategoryName(categoryName);
//	}
//
//}
