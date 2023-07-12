package com.example.nhanct.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role_user")
@IdClass(RoleUserId.class)
public class RoleUserEntity implements Serializable {
	
	@Id
	@Column(name = "role_id")
	private int roleId;
	
	@Id
	@Column(name = "user_id")
	private int userId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id", insertable = false, updatable = false)
	private RoleEntity role;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private UserEntity user;
}
