package com.example.nhanct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nhanct.entity.RoleUserEntity;
import com.example.nhanct.entity.RoleUserId;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUserEntity, RoleUserId>{

}
