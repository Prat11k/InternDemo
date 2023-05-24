package spring.all.config;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import spring.all.Entity.Notification;
import spring.all.Entity.Status;
import spring.all.repository.EmailRepository;

@AllArgsConstructor
@Data
@RequiredArgsConstructor
@Configuration
public class EmailSender implements Callable<Boolean> {
	private JavaMailSender javaMailSender;
	private List<String> emailAddresses;
	private String subject;
	private String message;
	private Notification information;
	private Status status;

	private EmailRepository emailRepository;
	Logger logger = LoggerFactory.getLogger(EmailSender.class);

	@Override
	public Boolean call() throws Exception {

		for (String email : emailAddresses) {
			information.setTo(email);
			logger.info("Inside method");
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			logger.info(email);
			mailMessage.setTo(email);
			mailMessage.setFrom("pp4deshmukh@gmail.com");
			information.setFrom("pp4deshmukh@gmail.com");
			logger.info(subject);
			mailMessage.setSubject(subject);
			logger.info(message);
			mailMessage.setText(message);

			boolean isSent = false;
			try {
				logger.info("sending mail");
				javaMailSender.send(mailMessage);
				// if(mailMessage!=null)
				isSent = true;
				logger.info("mail sent to : " + email);
			} catch (Exception e) {
				// handle the exception
			}
			if (isSent) {
				status.setStatus("Success");
				information.setStatus(Arrays.asList(status));
				// logger.info("mail sent to : "+email);
			} else {
				status.setStatus("Failed");
				information.setStatus(Arrays.asList(status));
				logger.info("mail not sent to : " + email);
			}

			information.setUuid(UUID.randomUUID().toString());
			// information.setVersion(System.currentTimeMillis());
			emailRepository.save(information);
		}
		return true;
	}

	public FutureTask<Boolean> sendEmailAsync() {
		FutureTask<Boolean> futureTask = new FutureTask<>(this);
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		executorService.submit(futureTask);
		return futureTask;
	}

	public EmailSender(JavaMailSender javaMailSender2, List<String> emailAddresses2, String subject2, String message2,
			Notification information2, Status status2, EmailRepository emailRepository2) {

		this.javaMailSender = javaMailSender2;
		this.emailAddresses = emailAddresses2;
		this.subject = subject2;
		this.message = message2;
		this.information = information2;
		this.status = status2;
		this.emailRepository = emailRepository2;
	}

}
