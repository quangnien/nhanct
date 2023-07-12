package com.example.nhanct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.nhanct.dto.RoleForAccountDto;
import com.example.nhanct.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer>{



//	@Query("SELECT new com.example.nhanct.dto.RoleForAccountDto(role.id , role.roleCode, role.roleName)"
//			+ "from RoleEntity role " 
//			+ "join RoleUserEntity roleUser on role.id = roleUser.roleId "
//			+ "join UserEntity user on user.id = roleUser.userId "
//			+ "and user.id = ?1"
//			+ "")
	@Query("SELECT new com.example.nhanct.dto.RoleForAccountDto(role.id , role.roleCode, role.roleName)"
			+ "from RoleEntity role "
			+ "")
	List<RoleForAccountDto> getListRoleForUpdate();

	@Query("SELECT role.roleName "
	+ "from RoleEntity role "
	+ "join RoleUserEntity roleUser on role.id = roleUser.roleId "
	+ "join UserEntity user on user.id = roleUser.userId "
	+ "and user.id = ?1"
	+ "")
	List<String> getListRoleNameOfUserAtPresent(int id);

	@Query("SELECT role.id "
			+ "from RoleEntity role "
			+ "join RoleUserEntity roleUser on role.id = roleUser.roleId "
			+ "join UserEntity user on user.id = roleUser.userId "
			+ "and user.id = ?1"
			+ "")
	List<Integer> getListRoleIdOfUserAtPresent(int id);

	@Query("SELECT n FROM RoleEntity n WHERE n.roleCode LIKE %?1% OR n.roleName LIKE %?1%")
	List<RoleEntity> findAll(String keyword);

	RoleEntity findByRoleCode(String roleCode);
}
