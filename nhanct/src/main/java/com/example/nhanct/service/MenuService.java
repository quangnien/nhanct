package com.example.nhanct.service;

import com.example.nhanct.dto.MenuForRoleDto;
import com.example.nhanct.dto.RoleForAccountDto;
import com.example.nhanct.entity.MenuEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuService {
	List<MenuEntity> findAllByRoleCode(String roleCode);
	List<MenuEntity> findAll();
	Page<MenuEntity> findAll(int pageNumber);
	List<MenuEntity> findAll(String keyword);
	public boolean isExceptionMenuCode(MenuEntity menuEntity);
	void add(MenuEntity menu);
	void update(MenuEntity menu);
	boolean delete(int id);
	MenuEntity getById(int id);
	MenuEntity findByMenuCode(String menuCode);

	List<String> getListMenuRoleAtPresent(int id);
	List<MenuForRoleDto> getListMenuForUpdate();

}
