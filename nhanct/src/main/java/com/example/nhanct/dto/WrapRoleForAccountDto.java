package com.example.nhanct.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WrapRoleForAccountDto {
	private List<RoleForAccountDto> roleForAccountDto;

	public void addRoleForAccountDto(RoleForAccountDto roleForAccountDto) {
        this.roleForAccountDto.add(roleForAccountDto);
    }
	
	public WrapRoleForAccountDto() {
		super();
	}
	
	public WrapRoleForAccountDto(List<RoleForAccountDto> roleForAccountDto) {
		super();
		this.roleForAccountDto = roleForAccountDto;
	}

}
