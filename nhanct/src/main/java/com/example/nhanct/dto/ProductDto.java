package com.example.nhanct.dto;

public class ProductDto {
	
	private int id;
	private String productName;
	private int price;
//	private int sale;
	private String des;
	private String image;
	private int quantity;
	private String status;
	private String tenCategory;
	public ProductDto() {
		super();
	}
	public ProductDto(int id, String productName, int price, String des, String image, int quantity,
					  String status, String tenCategory) {
		super();
		this.id = id;
		this.productName = productName;
		this.price = price;
//		this.sale = sale;
		this.des = des;
		this.image = image;
		this.quantity = quantity;
		this.status = status;
		this.tenCategory = tenCategory;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
//	public int getSale() {
//		return sale;
//	}
//	public void setSale(int sale) {
//		this.sale = sale;
//	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTenCategory() {
		return tenCategory;
	}
	public void setTenCategory(String tenCategory) {
		this.tenCategory = tenCategory;
	}
	
	
	
}
