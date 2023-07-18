package com.example.nhanct.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "role_menu")
@IdClass(RoleMenuId.class)
public class RoleMenuEntity implements Serializable {
	
	@Id
	@Column(name = "role_id")
	private int roleId;
	
	@Id
	@Column(name = "menu_id")
	private int menuId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id", insertable = false, updatable = false)
	private RoleEntity role;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "menu_id", insertable = false, updatable = false)
	private MenuEntity menu;
}
