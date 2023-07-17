package com.example.nhanct.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="kind_of_tax")
public class KindOfTaxEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="name_of_tax")
	@NotBlank(message = "Please Input This Field!")
	private String nameOfTax;

	@Column(name = "ratio")
	@Min(value=0, message = "Min price is 0")
	private int ratio;

	@OneToMany(mappedBy = "kindOfTax")
	private List<InvoiceDetailEntity> invoiceDetailEntityList;

}
