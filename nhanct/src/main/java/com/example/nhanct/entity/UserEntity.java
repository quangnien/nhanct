package com.example.nhanct.entity;

import com.example.nhanct.annotation.Email;
import com.example.nhanct.annotation.Phone;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="user_name")
	@NotBlank(message = "Please Input This Field!")
	private String userName;
	
	@Column(name="dob")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dob;
	
	@Column(name="sex")
	@NotBlank(message = "Please Input This Field!")
	private String sex;
	
	@Column(name="phone")
	@Phone
	@NotBlank(message = "Please Input This Field!")
	private String phone;

	@Column(name="address")
	@NotBlank(message = "Please Input This Field!")
	@Length(min = 15 , message = "This field length must be more than 15 characters!")
	private String address;

	@Column(name="email")
	@NotBlank(message = "Please Input This Field!")
	@Email(message = "Please Input Correct Format!")
	private String email;
	
	@Column(name="tai_khoan_user")
	@NotBlank(message = "Please Input This Field!")
	private String taiKhoanUser;
	
	@Column(name="password")
	@NotBlank(message = "Please Input This Field!")
	private String password;
	
	@Column(name = "image")
	private String image;
	
//	@ManyToOne
//	@JoinColumn(name = "role_id"  , insertable = false, updatable = false)
//	private RoleEntity role;
	
	@OneToMany(mappedBy = "user")
	private List<RoleUserEntity> roleUser;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<InvoiceEntity> invoice;
	
}
