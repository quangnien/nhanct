package com.example.nhanct.entity;

import com.example.nhanct.annotation.Email;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="contact")
public class ContactEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="your_email")
	@NotBlank(message = "Please Input This Field!")
	@Email(message = "Please Input Correct Format!")
	private String yourEmail;
	
	@Column(name="your_name")
	private String yourName;
	
	@Column(name="subject")
	private String subject;
	
	@Column(name="message")
	private String message;

}