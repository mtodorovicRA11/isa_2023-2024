package com.ftn.isa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SpringBootApplication
public class IsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsaApplication.class, args);
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("noreplystaristudentisatim@gmail.com");
		mailSender.setPassword("FinishISA2024");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		return mailSender;
	}
}
