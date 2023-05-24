package spring.all.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	@Autowired
	private JavaMailSender javaMailSender;

	public ResponseEntity<String> sendEmail(String to, String subject, String message) {
		logger.info("Entering Method sendEmail");
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		logger.info("Retrieved mail details");
		logger.info(String.format("send mail to : %s ", to));
		try {
			javaMailSender.send(mailMessage);
			logger.info("Mail sent successfully");
			return ResponseEntity.ok("Email sent successfully!");
		} catch (Exception e) {
			logger.error("Failed to send mail");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to send email: " + e.getMessage());
		}
	}
}
