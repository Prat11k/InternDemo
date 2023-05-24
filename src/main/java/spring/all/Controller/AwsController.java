package spring.all.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.model.PublishResult;

import spring.all.service.AwsSNSClient;

@RestController
@RequestMapping("/aws")
public class AwsController {
	@Autowired
	private AwsSNSClient awsSNSClient;
	@GetMapping("/sms")
	public PublishResult sendMessage(@RequestParam("message")String message,@RequestParam("phoneNumber")String phoneNumber){
		try{
			return	awsSNSClient.sendSingleSMS(message,phoneNumber);
		}catch(Exception e) {
			e.getStackTrace();
			e.toString();
			return null;
		}
	}
	@GetMapping("/multiplesms")
	public void sendMessages(@RequestParam("message")String message,@RequestParam List<String> phoneNumbers){
		try{
			for(String phoneNumber:phoneNumbers) {
				awsSNSClient.sendSingleSMS(message,phoneNumber);
			}
		}catch(Exception e) {
			e.getStackTrace();
			e.toString();
		}
	}
}
