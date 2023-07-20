package com.example.nhanct.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration 
public class MailConfig {
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		
		/*https://support.tenten.vn/index.php?type=page&urlcode=241751&title=H%C6%B0%E1%BB%9Bng-d%E1%BA%ABn-t%E1%BA%A1o-th%C3%B4ng-tin-SMTP-m%E1%BA%ADt-kh%E1%BA%A9u-c%E1%BA%A5p-2--c%E1%BB%A7a-Gmail*/
		mailSender.setUsername("nhanct@gmail.com");
	    mailSender.setPassword("nbpkzdbbfodyfpat");
	
	    // tạo đối tượng Properties và chỉ định thông tin host, port
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	
	    return mailSender;
	}
	
}