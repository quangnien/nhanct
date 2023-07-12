package com.example.nhanct.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="menu")
public class MenuEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "menu_code")
	@NotBlank(message = "Please Input This Field!")
	private String menuCode;

	@Column(name = "menu_name")
	@NotBlank(message = "Please Input This Field!")
	private String menuName;
	
	@OneToMany(mappedBy = "menu")
	private List<RoleMenuEntity> menuRole;

	public MenuEntity(String menuCode, String menuName) {
		this.menuCode = menuCode;
		this.menuName = menuName;
	}
}
