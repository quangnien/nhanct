package com.example.nhanct.entity;

import com.example.nhanct.annotation.Email;
import com.example.nhanct.annotation.Phone;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customer")
public class CustomerEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="customer_name")
	@NotBlank(message = "Please Input This Field!")
	private String customerName;
	
	@Column(name="phone")
	@Phone
	@NotBlank(message = "Please Input This Field!")
	private long phone;
	
	@Column(name="email")
	@NotBlank(message = "Please Input This Field!")
	@Email(message = "Please Input Correct Format!")
	private String email;
	
	@Column(name="address")
	@NotBlank(message = "Please Input This Field!")
	@Length(min = 15 , message = "This field length must be more than 15 characters!")
	private String address;
	
	@Column(name="user_name")
	@NotBlank(message = "Please Input This Field!")
	private String userName;

	@JsonIgnore
	@Column(name="password")
	@NotBlank(message = "Please Input This Field!")
	private String password;
	
	@Column(name = "reset_token")
	private String resetToken;
	
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<InvoiceEntity> invoice;

//	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
//	private List<ReviewEntity> review;

}
