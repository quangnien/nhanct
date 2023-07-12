package com.example.nhanct.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleMenuId implements Serializable {
	
	private int roleId;
	private int menuId;

}
