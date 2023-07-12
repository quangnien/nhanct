package com.example.nhanct.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	String upload(MultipartFile file) throws Exception;
	void delete(String nameImage);
}
