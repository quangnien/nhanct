//package com.example.nhanct.entity;
//
//
//import org.hibernate.validator.constraints.Length;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.persistence.*;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.NotBlank;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//@Entity
//@Table(name = "sale")
//public class SaleEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//
//	@NotBlank(message = "Vui lòng nhập tên sự kiện khuyến mãi!")
//	@Length(min = 10 , message = "Tên sự kiến khuyến mãi chứa ít nhất 10 ký tự!")
//	@Column(name = "event_name", columnDefinition = "nvarchar(50)")
//    private String eventName;
//
//	@Column(name="giam_gia")
//	@Max(value=100,  message = "Vui lòng nhập giá giảm (%)") // min -> xử lý ở code
//	private int giamGia;
//
//	//xử lý ngày đã bắt trong DAO
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "start_date")
//    private LocalDate startDate;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "end_date")
//    private LocalDate endDate;
//
//    @OneToMany(mappedBy = "sale")
//	private List<ProductEntity> product;
//
//    @Transient
//    private String _startDate;
//
//    @Transient
//    private String _endDate;
//
//    @Transient
//    private String status_message;
//
////    @Transient
////    private double discount_amount_max;
////
////    public double getDiscount_amount_max() {
////        double max = 0;
////        for (Discount d : this.discountList){
////            max = (max < d.getDiscountAmount()) ? d.getDiscountAmount() : max;
////        }
////        this.discount_amount_max = max;
////        return discount_amount_max;
////    }
//
//    public String get_startDate() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
//        return this.getStartDate().format(formatter);
//    }
//
//    public String get_endDate() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
//        return this.getEndDate().format(formatter);
//    }
//
////	public void setDiscount_amount_max(double discount_amount_max) {
////		this.discount_amount_max = discount_amount_max;
////	}
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getEventName() {
//		return eventName;
//	}
//
//	public void setEventName(String eventName) {
//		this.eventName = eventName;
//	}
//
//	public int getGiamGia() {
//		return giamGia;
//	}
//
//	public void setGiamGia(int giamGia) {
//		this.giamGia = giamGia;
//	}
//
//	public LocalDate getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(LocalDate startDate) {
//		this.startDate = startDate;
//	}
//
//	public LocalDate getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(LocalDate endDate) {
//		this.endDate = endDate;
//	}
//
//	public List<ProductEntity> getSanpham() {
//		return product;
//	}
//
//	public void setSanpham(List<ProductEntity> product) {
//		this.product = product;
//	}
//
//	public void set_startDate(String _startDate) {
//		this._startDate = _startDate;
//	}
//
//	public void set_endDate(String _endDate) {
//		this._endDate = _endDate;
//	}
//
//	public String getStatus_message() {
//		return status_message;
//	}
//
//	public void setStatus_message(String status_message) {
//		this.status_message = status_message;
//	}
//
//	public SaleEntity() {
//		super();
//	}
//
//	public SaleEntity(int id, @NotBlank(message = "Vui lòng nhập tên sự kiện khuyến mãi!")
//	@Length(min = 10 , message = "Tên sự kiến khuyến mãi chứa ít nhất 10 ký tự!")  String eventName, LocalDate startDate, LocalDate endDate, String _startDate,
//					  @Max(value = 100, message = "Vui lòng nhập giá giảm (%)") int giamGia,
//					  String _endDate, List<ProductEntity> product) {
//		super();
//		this.id = id;
//		this.eventName = eventName;
//		this.startDate = startDate;
//		this.endDate = endDate;
//		this.giamGia = giamGia;
//		this._startDate = _startDate;
//		this._endDate = _endDate;
//		this.product = product;
////		this.discount_amount_max = discount_amount_max;
//	}
//
//
//
//
//}