package com.example.nhanct.dto;

public class ContactDto {

	private String yourEmail;
	private String yourName;
	private String subject;
	private String message;

	public String getYourEmail() {
		return yourEmail;
	}

	public void setYourEmail(String yourEmail) {
		yourEmail = yourEmail;
	}

	public String getyourName() {
		return yourName;
	}

	public void setyourName(String yourName) {
		yourName = yourName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ContactDto(String yourEmail, String yourName, String subject, String message) {
		super();
		yourEmail = yourEmail;
		yourName = yourName;
		subject = subject;
		this.message = message;
	}

	public ContactDto() {
		super();
	}

}
