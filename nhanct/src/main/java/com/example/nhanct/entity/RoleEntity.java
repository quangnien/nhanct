package com.example.nhanct.entity;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="role")
public class RoleEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "role_code", unique=true)
	@NotBlank(message = "Please Input This Field!")
	private String roleCode;
	
	@Column(name = "role_name")
	@NotBlank(message = "Please Input This Field!")
	private String roleName;
	
	/*____________________________________*/

	@OneToMany(mappedBy = "role")
	private List<RoleUserEntity> roleUser;

	@OneToMany(mappedBy = "role")
	private List<RoleMenuEntity> roleMenu;
	
}
