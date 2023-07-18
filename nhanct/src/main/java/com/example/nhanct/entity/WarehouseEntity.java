package com.example.nhanct.entity;

import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

//	@OneToMany(mappedBy = "khach_hang")
//	private List<ReviewEntity> review;

}
