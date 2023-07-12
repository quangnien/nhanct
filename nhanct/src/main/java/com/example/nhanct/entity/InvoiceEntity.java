package com.example.nhanct.entity;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="invoice")
@Getter
@Setter
public class InvoiceEntity {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "invoice_time")
	@DateTimeFormat(pattern =  "yyyy-MM-dd")
	private Date invoiceTime;

	@NotBlank(message = "Please Input This Field!")
	@Length(min = 5 , message = "This field length must be more than 5 characters!")
	@Column(name = "status")
	private String status;
	
	@Column(name = "flag_cancel", columnDefinition = "int(1) default 0")
	private int flagCancel;
	
	/*______begin______*/
	@Column(name="customer_id")
	private int customerId;
	
	@ManyToOne
	@JoinColumn(name = "customer_id"  , insertable = false, updatable = false)
	private CustomerEntity customer;
	/*______end______*/
	
	@OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
	private List<DetailInvoiceEntity> detailInvoice;

	/*______begin______*/
	@Column(name = "user_id")
	private int userId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private UserEntity user;
	/*______end______*/

	@Override
	public String toString() {
		return "InvoiceEntity [id=" + id + ", invoiceTime=" + invoiceTime + ", status=" + status
				+ ", customerId=" + customerId + ", customer=" + customer + ", detailInvoice=" + detailInvoice
				+ "]";
	}
	
}
