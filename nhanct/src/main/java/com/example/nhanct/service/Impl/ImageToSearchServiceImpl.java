//package com.example.nhanct.service.Impl;
//
//import com.example.nhanct.entity.ImageToSearch;
//import com.example.nhanct.repository.ImageToSearchRepository;
//import com.example.nhanct.service.ImageToSearchService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ImageToSearchServiceImpl implements ImageToSearchService {
//
//	@Autowired
//	private ImageToSearchRepository imageToSearchRepository ;
//
//	@Override
//	public List<ImageToSearch> findAll() {
//		return imageToSearchRepository.findAll();
//	}
//
//	@Override
//	public ImageToSearch getById(int id) {
//		return imageToSearchRepository.findById(id).get();
//	}
//
//	@Override
//	public void add(ImageToSearch imageToSearch) {
//		imageToSearchRepository.save(imageToSearch);
//	}
//
//	@Override
//	public void delete(int id) {
//		imageToSearchRepository.deleteById(id);
//	}
//
////	@Override
////	public List<ImageToSearch> getImageToSearchByIdproduct(int id) {
////		return ImageToSearchRepository.findImageToSearchByIdproduct(id);
////	}
//
//}
