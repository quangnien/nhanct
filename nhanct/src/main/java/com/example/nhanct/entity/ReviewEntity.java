//package com.example.nhanct.entity;
//
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import lombok.AllArgsConstructor;
//import lombok.*;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.UpdateTimestamp;
//import org.hibernate.validator.constraints.Length;
//
//import java.time.LocalDateTime;
//
//@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "review")
//public class ReviewEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//
//	@Column(name = "review_product")
//	@NotBlank(message = "Please Input This Field!")
//	@Length(min = 6 , message = "This field length must be more than 6 characters!")
//	private String reviewProduct;
//
//	@Column(name = "give_comment")
//	private String giveComment;
//
//	@Column(name = "image")
//	private String image;
//
//	@Column(name="last_update")
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date lastUpdate;
//
//	@Column(name = "customer_id")
//	private int customerId;
//
//	@Column(name="customer_name")
//	private String customerName;
//
//	@Column(name = "product_name")
//	private String productName;
//
//	@Column(name = "product_id")
//	private int productId;
//
//	@ManyToOne
//	@JoinColumn(name = "product_id", insertable = false, updatable = false)
//	private ProductEntity product;
//
//	@ManyToOne
//	@JoinColumn(name = "customer_id", insertable = false, updatable = false)
//	private CustomerEntity customer;
//
//}
