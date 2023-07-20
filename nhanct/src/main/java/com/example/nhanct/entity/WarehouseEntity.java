package com.example.nhanct.entity;

import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="warehouse")
public class WarehouseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="warehouse_name")
	@NotBlank(message = "Please Input This Field!")
	private String warehouseName;

	@Column(name="warehouse_code")
	@NotBlank(message = "Please Input This Field!")
	private String warehouseCode;

	/*______________________________________________*/

	@OneToMany(mappedBy = "inputWarehouse", fetch = FetchType.LAZY)
	private List<InvoiceEntity> invoiceEntityIssuer;

	@OneToMany(mappedBy = "outputWarehouse", fetch = FetchType.LAZY)
	private List<InvoiceEntity> invoiceEntityReleaser;

//	@OneToMany(mappedBy = "customer")
//	private List<ReviewEntity> review;



}
