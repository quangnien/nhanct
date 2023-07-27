package com.example.nhanct.dto;


import com.example.nhanct.entity.InvoiceEntity;
import com.example.nhanct.entity.InvoiceTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date toDate;

	private String invoiceType;
	private String status;
	private String kindOfTax;

	private String isExport;

}

