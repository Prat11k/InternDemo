package spring.all.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.all.service.KafkaDataService;
import spring.all.service.KafkaProducer;

@RestController
@RequestMapping("/kafka")
public class KafkaCrudController {
	@Autowired
	private KafkaProducer kafkaProducer;
	@Autowired
	private KafkaDataService kafkaDataService;
	@GetMapping("/getdata")
//	@Scheduled(fixedDelay = 20000)
	public ResponseEntity<String> produce(){
		kafkaProducer.sendMyData(kafkaDataService.fetchData());
	 return ResponseEntity.ok("data received");
	}
}
