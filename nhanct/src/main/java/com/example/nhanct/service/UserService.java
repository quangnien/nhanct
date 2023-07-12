package com.example.nhanct.service;

import com.example.nhanct.dto.UserDto;
import com.example.nhanct.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
	
	Page<UserEntity> findAll(int pageNumber);
	UserEntity getById(int id);
	UserEntity findByEmail(String email);
	void add(UserEntity user);	
	void update(UserEntity user);
	boolean delete(int id);
	List<UserDto> getAllDto();
	void changePassword(UserEntity user);
	
	Page<UserEntity> find(String name, Pageable pageable);
	Page<UserEntity> findAll(int pageNumber, String sortField, String sortDir);
	
	List<UserEntity> findAll(String keyword);

	//change password:
	void updatePassword(UserEntity user);
	
	public boolean isException(UserEntity user);
	
	public boolean isExceptionEmail(UserEntity user);
	
	UserEntity findByTenTaiKhoanUser(String userName);
}
