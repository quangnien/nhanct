package com.example.nhanct.service;

import java.util.List;

import com.example.nhanct.dto.RoleForAccountDto;
import com.example.nhanct.entity.RoleEntity;
import org.springframework.data.domain.Page;

public interface RoleService {
	List<RoleEntity> findAll();
	Page<RoleEntity> findAll(int pageNumber);
	List<RoleEntity> findAll(String keyword);
	
	List<RoleForAccountDto> getListRoleForUpdate();
	
	List<String> getListRoleNameOfUserAtPresent(int id);
	List<Integer> getListRoleIdOfUserAtPresent(int id);
	void add(RoleEntity role);
	void update(RoleEntity role);
	boolean delete(int id);
	RoleEntity getById(int id);
	RoleEntity findByRoleCode(String id);
	public boolean isExceptionRoleCode(RoleEntity roleEntity);
	public boolean isExceptionFormatRoleCode(RoleEntity roleEntity);

}
