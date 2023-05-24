package spring.all.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.all.service.ThreadReaderService;

@RestController
@RequestMapping("/thread")
public class ThreadReaderController {
	@Autowired
	private ThreadReaderService threadReaderService;

	@GetMapping("/threadreader")
	public ResponseEntity<String> csvreaderWritter() {
		return threadReaderService.csvreaderWritter();
	}
}
