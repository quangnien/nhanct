package com.example.nhanct.repository;

import com.example.nhanct.entity.RoleMenuEntity;
import com.example.nhanct.entity.RoleMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenuEntity, RoleMenuId>{

}
