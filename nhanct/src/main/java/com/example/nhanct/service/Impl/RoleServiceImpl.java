package com.example.nhanct.service.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.nhanct.dto.RoleForAccountDto;
import com.example.nhanct.entity.RoleEntity;
import com.example.nhanct.repository.RoleRepository;
import com.example.nhanct.service.RoleService;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<RoleEntity> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Page<RoleEntity> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return roleRepository.findAll(pageable);
	}

	@Override
	public List<RoleEntity> findAll(String keyword) {
		if(keyword != null) {
			return roleRepository.findAll(keyword);
		}
		return roleRepository.findAll();
	}

	@Override
	public List<RoleForAccountDto> getListRoleForUpdate() {
		return roleRepository.getListRoleForUpdate();
	}

	@Override
	public List<String> getListRoleNameOfUserAtPresent(int id) {
		return roleRepository.getListRoleNameOfUserAtPresent(id);
	}

	@Override
	public List<Integer> getListRoleIdOfUserAtPresent(int id) {
		return roleRepository.getListRoleIdOfUserAtPresent(id);
	}

	@Override
	@Transactional
	public void add(RoleEntity role) {
		role.setRoleCode(role.getRoleCode().trim());
		roleRepository.save(role);
	}

	@Override
	public void update(RoleEntity role) {
//		UserEntity entity = userRepository.findById(user.getId()).get();

		ModelMapper modelMapper = new ModelMapper();
		RoleEntity roleEntity = modelMapper.map(role, RoleEntity.class);
		roleRepository.save(roleEntity);
	}

	@Override
	public boolean delete(int id) {
		roleRepository.deleteById(id);
		return true;
	}

	@Override
	public RoleEntity getById(int id) {
		return roleRepository.findById(id).get();
	}

	@Override
	public RoleEntity findByRoleCode(String roleCode) {
		return roleRepository.findByRoleCode(roleCode);
	}

	@Override
	public boolean isExceptionRoleCode(RoleEntity roleEntity) {
		RoleEntity entity = roleRepository.findByRoleCode(roleEntity.getRoleCode());
		return entity != null;
	}

	@Override
	public boolean isExceptionFormatRoleCode(RoleEntity roleEntity) {
		return roleEntity.getRoleCode().startsWith("ROLE_");
	}

}
