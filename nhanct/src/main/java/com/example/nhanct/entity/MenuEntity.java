package com.example.nhanct.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
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
	
	@OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
	private List<RoleMenuEntity> menuRole;

	public MenuEntity(String menuCode, String menuName) {
		this.menuCode = menuCode;
		this.menuName = menuName;
	}
}
