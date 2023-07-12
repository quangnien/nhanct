package com.example.nhanct.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nhanct.entity.RoleUserEntity;
import com.example.nhanct.repository.RoleUserRepository;
import com.example.nhanct.service.RoleUserService;

@Service
public class RoleUserServiceImpl implements RoleUserService {
	
	@Autowired
	private RoleUserRepository roleUserRepository;

	@Override
	public RoleUserEntity createNew(RoleUserEntity roleUser) {
		return roleUserRepository.save(roleUser);
	}

	@Override
	public void deleteRole(RoleUserEntity roleUser) {
		roleUserRepository.delete(roleUser);
	}
	
}
