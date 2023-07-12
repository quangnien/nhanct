package com.example.nhanct.repository;

import com.example.nhanct.entity.UserEntity;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	
	UserEntity findByEmail(String email);
	UserEntity findByUserName(String username);
	
//	@Query("SELECT new com.example.nhanct.dto.UserDto(n.id, n.userName, n.dob, n.sex, n.phone, n.address, n.email, n.taiKhoanUser, v.roleCode) FROM UserEntity n JOIN n.role v")
//	List<UserDto> findAll();

	@Query("SELECT n FROM UserEntity n WHERE n.userName LIKE %?1% OR n.phone LIKE %?1% OR n.email LIKE %?1% OR n.taiKhoanUser LIKE %?1%" +
			"OR n.address LIKE %?1%")
	public List<UserEntity> findAll(String keyword);
	
	@Transactional
	@Modifying
	@Query("UPDATE UserEntity u SET u.password = ?1 where u.id = ?2")
	public void updatePassword(String password, Integer id);
	
	//TODO : role_account
	@Query("SELECT vt.roleCode from RoleEntity vt "
			+ "left join RoleUserEntity vtnv on vt.id = vtnv.roleId "
			+ "left join UserEntity nv on nv.id = vtnv.userId "
			+ "where nv.taiKhoanUser = ?1")
	public List<String> findListRoleByUserName(String username);
}
