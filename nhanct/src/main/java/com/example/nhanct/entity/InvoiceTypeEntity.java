package com.example.nhanct.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="invoice_type")
public class InvoiceTypeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name_of_invoice_type")
	@NotBlank(message = "Please Input This Field!")
	private String nameOfInvoiceType;

	/*------------------------*/
	@OneToMany(mappedBy = "invoiceType", fetch = FetchType.LAZY)
	private List<IssueInvoiceEntity> issueInvoiceEntityList;

}
