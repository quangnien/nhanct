package com.example.nhanct.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
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
