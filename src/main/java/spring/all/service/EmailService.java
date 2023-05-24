package spring.all.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import spring.all.Entity.Notification;
import spring.all.Entity.Status;
import spring.all.config.EmailSender;
import spring.all.repository.EmailRepository;

@Component
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private EmailRepository emailRepository;
	@Autowired
	private Status status;
	@Autowired
	private Notification information;

	public void sendEmailToMultipleAddresses(List<String> emailAddresses, String subject, String message)
			throws ExecutionException, InterruptedException {
		EmailSender emailSender = new EmailSender(javaMailSender, emailAddresses, subject, message, information, status,
				emailRepository);

		emailSender.sendEmailAsync();

	}
}
