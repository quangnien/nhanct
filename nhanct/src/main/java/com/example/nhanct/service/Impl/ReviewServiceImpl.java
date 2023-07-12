//package com.example.nhanct.service.Impl;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import com.example.nhanct.entity.ReviewEntity;
//import com.example.nhanct.repository.ReviewRepository;
//import com.example.nhanct.service.ReviewService;
//
//@Service
//public class ReviewServiceImpl implements ReviewService{
//
//	@Autowired
//	private ReviewRepository reviewRepository;
//
//	@Override
//	public List<ReviewEntity> findAll() {
//		return reviewRepository.findAll();
//	}
//
//	@Override
//	public void add(ReviewEntity review) {
//		review.setLastUpdate(LocalDateTime.now());
//		reviewRepository.save(review);
//	}
//
//	@Override
//	public boolean delete(int id) {
//		reviewRepository.deleteById(id);
//		return true;
//	}
//
//	@Override
//	public Page<ReviewEntity> getAllByIdproduct(int pageNumber, int idSanpham) {
//		Pageable pageable = PageRequest.of(pageNumber-1, 2);
//		Page<ReviewEntity> pagereview = reviewRepository.findAllByProductId(pageable, idSanpham);
//		return pagereview;
//	}
//
//	@Override
//	public List<ReviewEntity> getAllByIdproduct(int id) {
//		return reviewRepository.findByProductId(id);
//	}
//
//	@Override
//	public Page<ReviewEntity> findAll(int pageNumber) {
//		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
//		return reviewRepository.findAll(pageable);
//	}
//
//	@Override
//	public List<ReviewEntity> findAllByKeyWord(int keyword) {
//		if(keyword != 0) {
//			return reviewRepository.findAllByKeyWord(keyword);
//		}
//		return reviewRepository.findAll();
//	}
//
//	@Override
//	public ReviewEntity getById(int id) {
//		return reviewRepository.findById(id).get();
//	}
//
////	@Override
////	public List<ReviewEntity> findAll(int pageNumber, int id) {
////		Pageable pageable = PageRequest.of(pageNumber-1, 2);
////		List<ReviewEntity> listtest = reviewRepository.findAllByProductId(id, pageable);
////		return listtest;
////	}
//}
