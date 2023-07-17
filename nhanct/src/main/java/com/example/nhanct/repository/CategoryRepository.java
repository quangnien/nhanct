//package com.example.nhanct.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.example.nhanct.entity.CategoryEntity;
//
//@Repository
//public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer>{
//	// thực hiện chức năng tìm kiếm tất cả category bằng cái keyword mình truyền vào
//	@Query("SELECT s FROM CategoryEntity s WHERE s.categoryName LIKE %?1%")
//	public List<CategoryEntity> findAll(String keyword);
//
//	CategoryEntity findByCategoryName(String CategoryName);
//
//
//	//add
//}
