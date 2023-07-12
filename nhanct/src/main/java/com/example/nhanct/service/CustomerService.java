package com.example.nhanct.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.nhanct.entity.CustomerEntity;

public interface CustomerService {
	
	List<CustomerEntity> findAll();
	CustomerEntity getById(int id);
	void add(CustomerEntity customer);
	void update(CustomerEntity customer);
	void delete(int id);
	
	Optional<CustomerEntity> findByEmail(String email);
	
	CustomerEntity findbyemail(String email);

	
	public CustomerEntity login(String userName, String password);
	
	//public CustomerEntity findByUserName(String userName);
	Optional<CustomerEntity> findByUserName(String userName);
	
	//CustomerEntity findByUserName(String userName);
	CustomerEntity findByUserNameAndPasswordKH(String userName, String password);
	
	public Optional<CustomerEntity> findByKhachHangemail(String email);
    public Optional<CustomerEntity> findKhachHangByResetToken(String resetToken);
    
    public CustomerEntity find(String userName);

  //change password:
  	void updatePassword(CustomerEntity customer);
  	
  	//paging
  	Page<CustomerEntity> findAll(int pageNumber);
  	
  	//seaching
  	List<CustomerEntity> findAll(String keyword);
  	
	public boolean isExceptionEmail(CustomerEntity customer);
	
	CustomerEntity findByUserNameKhiDangKyHoacSua(String userName);
	
	
}
