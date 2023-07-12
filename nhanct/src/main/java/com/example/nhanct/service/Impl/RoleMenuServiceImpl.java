package com.example.nhanct.service.Impl;

import com.example.nhanct.entity.RoleMenuEntity;
import com.example.nhanct.repository.RoleMenuRepository;
import com.example.nhanct.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {
	
	@Autowired
	private RoleMenuRepository roleMenuRepository;

	@Override
	public RoleMenuEntity save(RoleMenuEntity roleMenu) {
		return roleMenuRepository.save(roleMenu);
	}

	@Override
	public void delete(RoleMenuEntity roleMenu) {
		roleMenuRepository.delete(roleMenu);
	}
	
}
