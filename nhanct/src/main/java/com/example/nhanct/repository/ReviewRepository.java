//package com.example.nhanct.repository;
//
//import java.util.List;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import com.example.nhanct.entity.ReviewEntity;
//
//public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer>{
//
//	 List<ReviewEntity> findByProductId(int id);
//
//	 @Query("SELECT s FROM ReviewEntity s WHERE s.productId = ?1")
//	 public List<ReviewEntity> findAllByKeyWord(int keyword);
//
//	 Page<ReviewEntity> findAllByProductId(Pageable pageable,int id);
//}
