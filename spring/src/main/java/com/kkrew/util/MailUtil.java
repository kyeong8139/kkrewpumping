package com.kkrew.util;

import java.io.IOException;
import java.util.Random;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class MailUtil{

	JavaMailSender mailSender;
	
	public MailUtil(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendSimpleEmail(String email, String title, String code) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
        message.setSubject(title);
        message.setText(code);

        mailSender.send(message);
	}
	
	public void sendImegeMail(String email, String title, String code) {
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(email);
			helper.setSubject(title);
			
			String content = "<html><body>"
					+ "<img src='cid:image' style='width:400px;'><br><br>"
					+ "<p style='font-size:20px;'>kkrewpuming의 인증 번호는 <strong>" + code + "</strong>입니다.</p>"
					+ "</body></html>";
			
			helper.setText(content, true);						
			helper.addInline("image", new ClassPathResource("static/logo.png"));
			
			mailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String createCode() {
		StringBuilder code = new StringBuilder();

		for (int i = 0; i < 6; i++) {
			int num = new Random().nextInt(10);
			code.append(num);
		}

		return code.toString();
	}
}
