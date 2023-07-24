package com.example.nhanct.entity;

import com.example.nhanct.annotation.Email;
import com.example.nhanct.annotation.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="invoice")
public class InvoiceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="number_of_invoice")
	private int numberOfInvoice;

	@Column(name="issue_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date issueDate;

	@Column(name="release_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date releaseDate;

	@Column(name = "status")
	private String status;

	@Column(name = "reason_for_cancellation")
	private String reasonForCancellation;

	@Column(name = "sum_price")
	private int sumPrice;

	@Column(name = "symbol")
	private String symbol;

	@Column(name = "flag_invoice_type")
	private String flagInvoiceType;

	/* _____ begin FOR CUSTOMER IF INVOICE-TYPE IS VAT _____ */
	@Transient
	private String invoiceType;

	@Transient
	private String mst;

	@Transient
	private String customerName;

	@Transient
	private String phone;

	@Transient
	private String address;
	/* _____ end FOR CUSTOMER IF INVOICE-TYPE IS VAT _____ */

	/*------------------------*/
	@OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
	private List<InvoiceDetailEntity> invoiceDetailEntityList;

	/*------------ begin ------------*/
	@Column(name = "business_id")
	private int businessId;

	@ManyToOne
	@JoinColumn(name = "business_id", insertable = false, updatable = false)
	private BusinessEntity business;
	/*------------ end ------------*/

	/*------------ begin ------------*/
	@Column(name = "issue_invoice_id")
	private int issueInvoiceId;

	@ManyToOne
	@JoinColumn(name = "issue_invoice_id", insertable = false, updatable = false)
	private IssueInvoiceEntity issueInvoice;
	/*------------ end ------------*/

	/*------------ begin ------------*/
	@Column(name = "customer_id")
	private int customerId;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "customer_id", insertable = false, updatable = false)
	private CustomerEntity customer;
	/*------------ end ------------*/

	/*------------ begin ------------*/
	@Column(name = "input_warehouse_id")
	private int inputWarehouseId;

	@ManyToOne
	@JoinColumn(name = "input_warehouse_id", insertable = false, updatable = false)
	private WarehouseEntity inputWarehouse;

	@Column(name = "output_warehouse_id")
	private int outputWarehouseId;

	@ManyToOne
	@JoinColumn(name = "output_warehouse_id", insertable = false, updatable = false)
	private WarehouseEntity outputWarehouse;
	/*------------ end ------------*/

	/*------------ begin ------------*/
	@Column(name="issuer_id")
	private int issuerId;

	@ManyToOne
	@JoinColumn(name = "issuer_id", insertable = false, updatable = false)
	private UserEntity issuerUser;

	@Column(name="releaser_id")
	private int releaserId;

	@ManyToOne
	@JoinColumn(name = "releaser_id", insertable = false, updatable = false)
	private UserEntity releaserUser;
	/*------------ end ------------*/


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

}

