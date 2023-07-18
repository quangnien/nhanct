package com.example.nhanct.entity;


import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "issue_invoice")
public class IssueInvoiceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "mst")
	@NotBlank(message = "Please Input This Field!")
	private String mst;

	@Column(name = "symbol")
	@NotBlank(message = "Please Input This Field!")
	private String symbol;

	@Column(name = "quantity")
	@Min(value=0, message = "Min price is 0")
	private int quantity;

	@Column(name = "from_number")
	@Min(value=0, message = "Min price is 0")
	private int fromNumber;

	@Column(name = "to_number")
	@Min(value=0, message = "Min price is 0")
	private int toNumber;

	@Column(name="date_of_registration")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfRegistration;

	@Column(name = "current_invoice_number", columnDefinition = "integer default 0")
	private int currentInvoiceNumber;

	/*------------------------*/
	@OneToMany(mappedBy = "issueInvoice", fetch = FetchType.LAZY)
	private List<InvoiceEntity> invoiceEntityList;

	/*------------ begin ------------*/
	@Column(name = "invoice_type_id")
	private int invoiceTypeId;

	@ManyToOne
	@JoinColumn(name = "invoice_type_id", insertable = false, updatable = false)
	private InvoiceTypeEntity invoiceType;
	/*------------ end ------------*/

}

