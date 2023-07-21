package com.example.nhanct.dto;

import com.example.nhanct.entity.InvoiceEntity;
import com.example.nhanct.entity.KindOfTaxEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailDto {

	private String col_4_0_;
	private String col_3_0_;
	private String col_2_0_;
	private int col_1_0_;
}
