//package com.example.nhanct.service;
//
//import java.util.List;
//
//import org.springframework.data.domain.Page;
//
//import com.example.nhanct.entity.ReviewEntity;
//
//public interface ReviewService {
//	List<ReviewEntity> findAll();
//	List<ReviewEntity> getAllByIdproduct(int id);
//	void add(ReviewEntity review);
//	boolean delete(int id);
//	ReviewEntity getById(int id);
//
//	//paging
//	Page<ReviewEntity> findAll(int pageNumber);
//	//List<ReviewEntity> findAll(int pageNumber, int id);
//	Page<ReviewEntity> getAllByIdproduct(int pageNumber, int idSanpham);
//
//
//	//seach
//	List<ReviewEntity> findAllByKeyWord(int keyword);
//}
