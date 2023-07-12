package com.example.nhanct.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WrapMenuForRoleDto {
	private List<MenuForRoleDto> menuForRoleDto;

	public void addMenuForRoleDto(MenuForRoleDto menuForRoleDto) {
        this.menuForRoleDto.add(menuForRoleDto);
    }
	
	public WrapMenuForRoleDto() {
		super();
	}
	
	public WrapMenuForRoleDto(List<MenuForRoleDto> menuForRoleDto) {
		super();
		this.menuForRoleDto = menuForRoleDto;
	}

}
