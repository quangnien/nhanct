package com.example.nhanct.service.Impl;

import com.example.nhanct.dto.MenuForRoleDto;
import com.example.nhanct.entity.MenuEntity;
import com.example.nhanct.repository.MenuRepository;
import com.example.nhanct.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<MenuEntity> findAllByRoleCode(String roleCode) {
		return menuRepository.findAllByRoleCode(roleCode);
	}

	@Override
	public List<MenuEntity> findAll() {
		return menuRepository.findAll();
	}

	@Override
	public Page<MenuEntity> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return menuRepository.findAll(pageable);
	}

	@Override
	public List<MenuEntity> findAll(String keyword) {
		if(keyword != null) {
			return menuRepository.findAll(keyword);
		}
		return menuRepository.findAll();
	}

	@Override
	public boolean isExceptionMenuCode(MenuEntity menuEntity) {
		MenuEntity entity = menuRepository.findByMenuCode(menuEntity.getMenuCode());
		return entity != null;
	}

	@Override
	public void add(MenuEntity menu) {
		menu.setMenuCode(menu.getMenuCode().trim());
		menuRepository.save(menu);
	}

	@Override
	public void update(MenuEntity menu) {
		ModelMapper modelMapper = new ModelMapper();
		MenuEntity menuEntity = modelMapper.map(menu, MenuEntity.class);
		menuRepository.save(menuEntity);
	}

	@Override
	public boolean delete(int id) {
		menuRepository.deleteById(id);
		return true;
	}

	@Override
	public MenuEntity getById(int id) {
		return menuRepository.findById(id).get();
	}

	@Override
	public MenuEntity findByMenuCode(String menuCode) {
		return menuRepository.findByMenuCode(menuCode);
	}

	@Override
	public List<String> getListMenuRoleAtPresent(int id) {
		return menuRepository.getListMenuRoleAtPresent(id);

	}

	@Override
	public List<MenuForRoleDto> getListMenuForUpdate() {
		return menuRepository.getListMenuForUpdate();
	}

}
