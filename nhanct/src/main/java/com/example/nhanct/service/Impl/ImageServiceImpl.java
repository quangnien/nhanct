package com.example.nhanct.service.Impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.nhanct.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService{

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Value("${file.upload-result}")
	private String uploadResult;
	
	@Override
	public String upload(MultipartFile file) throws Exception {
		//lấy đương dẫn tuyệt đối tới thư mục chứa file
		String path = System.getProperty("user.dir") + uploadDir;
		File dir = new File(path);
		
		// Kiểm tra thư mục chứ file đã tồn tại chưa
		if(!dir.exists()) dir.mkdir(); // chưa tồn tại thì tạo thư mục
		
		// getOriginalFilename() ->> lấy ra tên file từ client gửi lên
		String fileName =LocalDateTime.now().toString()+ file.getOriginalFilename();
		fileName= fileName.toLowerCase().replaceAll(":", "-");
		
		Path saveTo  = Paths.get(path);
		if (ImageIO.read(file.getInputStream()) == null) {
			throw new Exception("Image không đúng định dạng.");
		}
		
		Files.copy(file.getInputStream(), saveTo.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
		
		//trả về đường dẫn file
		return uploadResult + fileName;
	//	return fileName;
	}

	@Override
	public void delete(String nameImage) {
		String path = "./src/main/resources/static"+ nameImage;
		File file =new File(path);
		file.delete();
	}

}
