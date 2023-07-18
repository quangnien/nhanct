package com.example.nhanct.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class RoleUserId implements Serializable {
	
	private int roleId;
	private int userId;

}
