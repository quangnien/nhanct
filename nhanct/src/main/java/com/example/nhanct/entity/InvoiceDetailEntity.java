package com.example.nhanct.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice_detail")
public class InvoiceDetailEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "kind_of_tax_id")
	private int kindOfTaxId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "kind_of_tax_id", insertable = false, updatable = false)
	private KindOfTaxEntity kindOfTax;

	@Column(name = "invoice_id")
	private int invoiceId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "invoice_id", insertable = false, updatable = false)
	private InvoiceEntity invoice;

	/* ________________________*/

	@Column(name = "quantity")
	@Min(value=0, message = "Min price is 0")
	private int quantity;

	@Column(name = "price_before_tax")
	@Min(value=0, message = "Min price is 0")
	private BigDecimal priceBeforeTax;

	@Column(name = "price_after_tax")
	@Min(value=0, message = "Min price is 0")
	private BigDecimal priceAfterTax;

	@Column(name = "sum_money_after_tax")
	@Min(value=0, message = "Min price is 0")
	private BigDecimal sumMoneyAfterTax;
}
