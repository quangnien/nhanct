package com.example.nhanct.entity;


import com.example.nhanct.annotation.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="business")
public class BusinessEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "mst")
	@NotBlank(message = "Please Input This Field!")
	private String mst;

	@Column(name = "business_name")
	@NotBlank(message = "Please Input This Field!")
	private String businessName;

	@Column(name="phone")
	@Phone
	@NotBlank(message = "Please Input This Field!")
	private String phone;

	@Column(name="address")
	@NotBlank(message = "Please Input This Field!")
	@Length(min = 15 , message = "This field length must be more than 15 characters!")
	private String address;

	@Column(name = "logo")
	private String logo;

	@Column(name = "sample_signature")
	private String sampleSignature;

	/*------------------------*/
	@OneToMany(mappedBy = "business", fetch = FetchType.LAZY)
	private List<InvoiceEntity> invoiceEntityList;

}
