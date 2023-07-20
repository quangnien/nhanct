//package com.example.nhanct.entity;
//
//
//
//import lombok.AllArgsConstructor;
//import lombok.*;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import javax.persistence.*;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotBlank;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "product")
//public class ProductEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//
//	@Column(name = "product_name")
//	@NotBlank(message = "Please Input This Field!")
//	private String productName;
//
//	@Column(name = "price")
//	@Min(value=1000, message = "Min price is 1000 VNĐ!")
//	private int price;
//
//	@Column(name="price_actually")
//	private int priceActually;
//
//	@Column(name="last_update")
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date lastUpdate;
//
//	@Column(name = "des", columnDefinition="TEXT")
//	@NotBlank(message = "Please Input This Field!")
//	private String des;
//
//	@Column(name = "image")
//	private String image;
//
//	@Column(name = "quantity")
//	@Min(value=0, message = "Minimum is 0!")
//	private int quantity;
//
//	@Column(name = "status")
//	@NotBlank(message = "Please Input This Field!")
//	private String status;
//
//
//
//	/*------------ begin ------------*/
//	@Column(name = "category_id")
//	private int categoryId;
//
//	@ManyToOne
//	@JoinColumn(name = "category_id", insertable = false, updatable = false)
//	private CategoryEntity category;
//	/*------------ end ------------*/
//
//	/*------------ begin ------------*/
////	@Column(name = "sale_id")
////	private int saleId;
////
////	@ManyToOne
////	@JoinColumn(name = "sale_id", insertable = false, updatable = false)
////	private SaleEntity sale;
//
//	/*------------ end ------------*/
//
//	/*------------ begin ------------*/
//	@Column(name = "brand_id")
//	private int brandId;
//
//	@ManyToOne
//	@JoinColumn(name = "brand_id", insertable = false, updatable = false)
//	private BrandEntity brand;
//	/*------------ end ------------*/
//
////	@OneToMany(mappedBy = "product")
////	private List<ReviewEntity> review;
//
//	@OneToMany(mappedBy = "product")
//	private List<DetailImageEntity> detailImage;
//
//	@OneToMany(mappedBy = "product")
//	private List<DetailInvoiceEntity> detailInvoice;
//
//    @Transient
//    private String _startDatePromotion;
//
//    @Transient
//    private String _endDatePromotion;
//
//}
//
