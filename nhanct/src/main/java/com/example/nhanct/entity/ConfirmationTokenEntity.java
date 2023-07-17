//package com.example.nhanct.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.UUID;
//
//@Data
//@AllArgsConstructor
//@Entity
//@Table(name="confirmation_token")
//public class ConfirmationTokenEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="token_id")
//    private long tokenid;
//
//	@Column(name="confirmation_token")
//	private String confirmationToken;
//
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date createdDate;
//
//	@Column(name="flag", columnDefinition = "int(1) default 0")
//    private int flag;
//
//	@OneToOne(targetEntity = CustomerEntity.class, fetch = FetchType.EAGER)
//	@JoinColumn(nullable = false, name = "id")
//	private CustomerEntity customer;
//
//	public ConfirmationTokenEntity() {
//		this.flag = 0;
//	}
//
//	public ConfirmationTokenEntity(CustomerEntity customer) {
//		this.customer = customer;
//		createdDate = new Date();
//		this.flag = 0;
//        confirmationToken = UUID.randomUUID().toString(); //  đại diện cho mã định danh duy nhất
//	}
//
//}
