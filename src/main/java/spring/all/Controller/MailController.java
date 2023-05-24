package spring.all.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.all.service.MailService;

@RestController
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private MailService mailService;

	@PostMapping("/mail")
	public ResponseEntity<String> sendEmail(@RequestParam("to") String to, @RequestParam("subject") String subject,
			@RequestParam("message") String message) {
		return mailService.sendEmail(to, subject, message);
	}
}
