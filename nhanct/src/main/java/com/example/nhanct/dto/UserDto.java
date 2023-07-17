package com.example.nhanct.dto;

import java.util.Date;

public class UserDto {
	
	private int id;
	private String userName;
	private Date dob;
	private String sex;
	private String phone;
	private String address;
	private String email;
	private String taiKhoanUser;
	private String	roleCode;
	public UserDto() {
		super();
	}
	public UserDto(int id, String userName, Date dob, String sex, String phone, String address,
				   String email, String taiKhoanUser, String roleCode) {
		super();
		this.id = id;
		this.userName = userName;
		this.dob = dob;
		this.sex = sex;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.taiKhoanUser = taiKhoanUser;
		this.roleCode = roleCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTaiKhoanUser() {
		return taiKhoanUser;
	}
	public void setTaiKhoanUser(String taiKhoanUser) {
		this.taiKhoanUser = taiKhoanUser;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	
	
	
	
	
}
