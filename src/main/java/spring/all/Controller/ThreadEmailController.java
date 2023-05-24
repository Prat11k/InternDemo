package spring.all.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.all.Components.EmailRequest;
import spring.all.Entity.Notification;

import spring.all.service.ThreadMailService;

@RestController
@RequestMapping("threadmail")
public class ThreadEmailController {

	@Autowired
	private ThreadMailService threadMailService;

	@PostMapping("/send-email")
	public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
		return threadMailService.sendEmail(emailRequest);
	}

	@GetMapping("get-all")
	public List<Notification> getAll() {
		return threadMailService.getAll();
	}

	@GetMapping("get-all/{status}")
	public List<Notification> getFailed(@PathVariable String status) {
		return threadMailService.getFailed(status);
	}

	@GetMapping("get-all/list/{status}")
	public List<String> getStatus(@PathVariable String status) {
		return threadMailService.getStatus(status);
	}
}
