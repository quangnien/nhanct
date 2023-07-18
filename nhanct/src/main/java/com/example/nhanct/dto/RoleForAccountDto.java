package com.example.nhanct.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleForAccountDto {
	private int id;
	private String roleCode;
	private String roleName;
//	private int idUser;
	private Boolean status = false;

	public RoleForAccountDto(int id) {
		super();
		this.id = id;
		this.status = false;
	}
	
	public RoleForAccountDto(int id, String roleCode) {
		super();
		this.id = id;
		this.roleCode = roleCode;
		this.status = false;
	}
	
	public RoleForAccountDto(int id, String roleCode, String roleName) {
		super();
		this.id = id;
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.status = false;
	}

}
