package com.example.nhanct.repository;

import com.example.nhanct.dto.MenuForRoleDto;
import com.example.nhanct.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer>{

    @Query("SELECT menu FROM MenuEntity menu " +
            "JOIN RoleMenuEntity roleMenu ON roleMenu.menuId = menu.id " +
            "JOIN RoleEntity role ON roleMenu.roleId = role.id " +
            "AND role.roleCode = ?1")
    List<MenuEntity> findAllByRoleCode(String roleCode);

    MenuEntity findByMenuCode(String menuCode);
    @Query("SELECT n FROM MenuEntity n WHERE n.menuCode LIKE %?1% OR n.menuName LIKE %?1%")
    List<MenuEntity> findAll(String keyword);

    @Query("SELECT vt.menuCode "
            + "from MenuEntity vt "
            + "join RoleMenuEntity vtnv on vt.id = vtnv.menuId "
            + "join RoleEntity nv on nv.id = vtnv.roleId "
            + "and nv.id = ?1"
            + "")
    List<String> getListMenuRoleAtPresent(int id);

    @Query("SELECT new com.example.nhanct.dto.MenuForRoleDto(vt.id , vt.menuCode, vt.menuName)"
            + "from MenuEntity vt "
            + "")
    List<MenuForRoleDto> getListMenuForUpdate();


}