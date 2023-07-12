package com.example.nhanct.service;

import com.example.nhanct.entity.RoleUserEntity;

public interface RoleUserService {
	
	RoleUserEntity createNew(RoleUserEntity roleUser);
	
	void deleteRole(RoleUserEntity roleUser);
}
