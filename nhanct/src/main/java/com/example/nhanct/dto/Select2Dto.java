package com.example.nhanct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Select2Dto {

	private String id;
	private String text;
	private String name;

	public Select2Dto() {
	}

	public Select2Dto(String id, String text, String name) {
		this.id = id;
		this.text = text;
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}