package com.example.nhanct.controller;

import com.example.nhanct.entity.MenuEntity;
import com.example.nhanct.service.MenuService;
import com.example.nhanct.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

/**
 * doan
 *
 * @author NhanCT
 * @date 7/5/2023 6:29 PM
 */
public class FunctionCommon {
    @Autowired
    private SecurityUtils myUser;
    @Autowired
    private MenuService menuService;

    /*---------------- begin -> VIEW MENU ------------*/
    public void menuListRole(ModelMap model) {
        List<MenuEntity> menuEntityList = listRole();
        model.addAttribute(menuEntityList);
    }
    /*---------------- end -> VIEW MENU ------------*/

    /*---------------- begin AUTHOR ------------*/
    public boolean hasRoleAuthor(String menuCode) {
        List<MenuEntity> menuEntityList = listRole();
        for(MenuEntity menuEntity : menuEntityList){
            if(menuEntity.getMenuCode().equals(menuCode)){
                return true;
            }
        }
        return false;
    }
    /*---------------- end AUTHOR ------------*/

    /*---------------- begin COMMON ------------*/
    public List<MenuEntity> listRole() {
        Object[] tg = myUser.getPrincipal().getAuthorities().toArray();

        List<MenuEntity> menuEntityList = new ArrayList<>();

        for(Object itemRole : tg) {
            List<MenuEntity> menuEntityListItem = menuService.findAllByRoleCode(itemRole.toString());
            for(MenuEntity menuEntity : menuEntityListItem){
                menuEntityList.add(menuEntity);
            }
        }

        /* All role will be have 2 menu default: Dashboard, Profile */
        MenuEntity menuEntityDashBoard = new MenuEntity("", "Dashboard");
        MenuEntity menuEntityProfile = new MenuEntity("profile", "Profile");
        menuEntityList.add(menuEntityDashBoard);
        menuEntityList.add(menuEntityProfile);

        return menuEntityList;
    }
    /*---------------- end COMMON ------------*/

}