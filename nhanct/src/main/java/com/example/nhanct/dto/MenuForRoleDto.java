package com.example.nhanct.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuForRoleDto {
	private int id;
	private String menuCode;
	private String menuName;
//	private int idRole;
	private Boolean status;

	public MenuForRoleDto(int id) {
		super();
		this.id = id;
	}
	
	public MenuForRoleDto(int id, String menuCode) {
		super();
		this.id = id;
		this.menuCode = menuCode;
	}
	
	public MenuForRoleDto(int id, String menuCode, String menuName) {
		super();
		this.id = id;
		this.menuCode = menuCode;
		this.menuName = menuName;
	}

}
