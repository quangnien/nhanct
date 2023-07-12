package com.example.nhanct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeAndTextDto {

	private String code;
	private String text;

	public CodeAndTextDto(String code, String text) {
		this.code = code;
		this.text = text;
	}
}