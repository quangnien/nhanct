package com.example.nhanct.dto;

import com.example.nhanct.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReportInvoiceDto {
	private String symbol;
	private Integer  fromNumber;
	private Integer  toNumber;
	private Date datePresent;
	private BigDecimal sumPriceOfTax;
	private BigDecimal sumPriceBeforeTax;
	private BigDecimal sumPriceAfterTax;
	private BigDecimal sumQuantity;

	public ReportInvoiceDto(String fromNumber, String toNumber, Date datePresent,
							BigDecimal sumPriceOfTax, BigDecimal sumPriceBeforeTax,
							BigDecimal sumPriceAfterTax) {
		this.fromNumber = Integer.parseInt(fromNumber);
		this.toNumber = Integer.parseInt(toNumber);
		this.datePresent = datePresent;
		this.sumPriceOfTax = sumPriceOfTax;
		this.sumPriceBeforeTax = sumPriceBeforeTax;
		this.sumPriceAfterTax = sumPriceAfterTax;
	}
}

