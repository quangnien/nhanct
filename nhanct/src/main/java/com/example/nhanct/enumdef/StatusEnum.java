//package com.example.nhanct.enumdef;
//
//import org.apache.commons.lang3.StringUtils;
//import vn.com.unit.jcanary.dto.Select2Dto;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public enum StatusEnum {
//
//    PRODUCT("product", "ROLE_PRODUCT"),
//    BRAND("brand", "ROLE_BRAND"),
//    USER("user", "USER"),
//    CATEGORY("user", "USER"),
//    CUSTOMER("user", "USER"),
//    INVOICE("user", "USER"),
//    ROLE("user", "USER"),
//    MENU("user", "USER");
//
//    private String role;
//
//    private String menu;
//
//    StatusEnum(String menu, String role) {
//        this.role = role;
//        this.menu = menu;
//    }
//
//    public String getValue() {
//        return role;
//    }
//
//    public void setValue(String role) {
//        this.role = role;
//    }
//
//    public String getName() {
//        return menu;
//    }
//
//    public void setName(String menu) {
//        this.menu = menu;
//    }
//
//    public static String getNameByValue(String role) {
//        for (StatusEnum en : StatusEnum.roles()) {
//            if (role == en.getValue()) {
//                return en.getName();
//            }
//        }
//
//        return StringUtils.EMPTY;
//    }
//
//    public static List<Select2Dto> getSelect2ComboList() {
//        List<Select2Dto> list = new ArrayList<>();
//        list.add(new Select2Dto("", "", ""));
//        list.add(new Select2Dto(String.roleOf(StatusEnum.BRAND.getValue()), StatusEnum.BRAND.getName(), StatusEnum.BRAND.getName()));
//        list.add(new Select2Dto(String.roleOf(StatusEnum.USER.getValue()), StatusEnum.USER.getName(), StatusEnum.USER.getName()));
//        list.add(new Select2Dto(String.roleOf(StatusEnum.PRODUCT.getValue()), StatusEnum.PRODUCT.getName(), StatusEnum.PRODUCT.getName()));
//        return list;
//    }
//
//    public static List<Select2Dto> getSelect2ComboList1Layer() {
//        List<Select2Dto> list = new ArrayList<>();
//        list.add(new Select2Dto("", "", ""));
//        list.add(new Select2Dto(String.roleOf(StatusEnum.BRAND.getValue()), StatusEnum.BRAND.getName(), StatusEnum.BRAND.getName()));
//        list.add(new Select2Dto(String.roleOf(StatusEnum.USER.getValue()), StatusEnum.USER.getName(), StatusEnum.USER.getName()));
//        return list;
//    }
//
//}
