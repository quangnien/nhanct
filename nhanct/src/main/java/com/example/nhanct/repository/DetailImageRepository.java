package com.example.nhanct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.nhanct.entity.DetailImageEntity;

@Repository
public interface DetailImageRepository extends JpaRepository<DetailImageEntity, Integer>{
	
	// làm phần list ảnh minh họa
	List<DetailImageEntity> findByProductId(int id);
	
	@Query("SELECT s FROM DetailImageEntity s WHERE s.productId = ?1")
	List<DetailImageEntity> findDetailImageByProductId(Integer id);
}
