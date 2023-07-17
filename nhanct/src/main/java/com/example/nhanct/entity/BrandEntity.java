//package com.example.nhanct.entity;
//
//import com.example.nhanct.annotation.Email;
//import com.example.nhanct.annotation.Phone;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.validator.constraints.Length;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import java.util.List;
//
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name="brand")
//public class BrandEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//
//	@Column(name="brand_name")
//	@NotBlank(message = "Please Input This Field!")
//	private String brandName;
//
//	@Column(name="email")
//	@NotBlank(message = "Please Input This Field!")
//	@Email(message = "Please Input Correct Format!")
//	private String email;
//
//	@Column(name="phone")
//	@Phone
//	@NotBlank(message = "Please Input This Field!")
//	private String phone;
//
//	@Column(name="address")
//	@NotBlank(message = "Please Input This Field!")
//	@Length(min = 15 , message = "This field length must be more than 15 characters!")
//	private String address;
//
//	/*------------------------*/
//	@OneToMany(mappedBy = "brand")
//	private List<ProductEntity> product;
//}
