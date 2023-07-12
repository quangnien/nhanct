package com.example.nhanct.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="detail_invoice")
public class DetailInvoiceEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "invoice_id")
	private int invoiceId;
	
	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "sum_money")
	private int sumMoney;
	
	@Column(name = "quantity")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "invoice_id"  , insertable = false, updatable = false)
	private InvoiceEntity invoice;
	
	@ManyToOne
	@JoinColumn(name = "product_id"  , insertable = false, updatable = false)
	private ProductEntity product;

//	@Id
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "invoice_id", insertable = false, updatable = false)
//	private ProductEntity product;
//
//	@Id
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "product_id", insertable = false, updatable = false)
//	private InvoiceEntity invoice;

}