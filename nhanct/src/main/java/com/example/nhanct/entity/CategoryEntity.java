//package com.example.nhanct.entity;
//
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import lombok.AllArgsConstructor;
//import lombok.*;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name="category")
//public class CategoryEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//
//	@Column(name="category_name")
//	@NotBlank(message = "Please Input This Field!")
//	private String categoryName;
//
//	@OneToMany(mappedBy = "category")
//	private List<ProductEntity> product;
//
//}