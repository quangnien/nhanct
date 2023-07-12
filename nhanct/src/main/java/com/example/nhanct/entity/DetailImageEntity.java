package com.example.nhanct.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="detail_image")
public class DetailImageEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "product_id")
	private int productId;
	
	@ManyToOne
	@JoinColumn(name = "product_id"  , insertable = false, updatable = false)
	private ProductEntity product;
	
}
