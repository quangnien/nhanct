package com.example.nhanct.service;

import com.example.nhanct.entity.RoleMenuEntity;

public interface RoleMenuService {
	
	RoleMenuEntity save(RoleMenuEntity roleMenu);
	void delete(RoleMenuEntity roleMenu);
}
