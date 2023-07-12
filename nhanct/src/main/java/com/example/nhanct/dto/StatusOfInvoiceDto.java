package com.example.nhanct.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatusOfInvoiceDto {
	private String status;

	public StatusOfInvoiceDto(String status) {
		this.status = status;
	}
}