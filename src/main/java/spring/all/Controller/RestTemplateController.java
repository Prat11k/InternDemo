package spring.all.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.all.Entity.FactoryUser;
import spring.all.service.RestTemplateService;
@RestController
public class RestTemplateController {

	@Autowired
	RestTemplateService restTemplateService;
	  
	@GetMapping("/rest/getall/{type}/{token}")
	  public List<FactoryUser> getFactoryUserList(@PathVariable String type,@PathVariable String token) {
		return restTemplateService.getFactoryUserList(type,token);
	}
	
	@PostMapping("/rest/add/{type}/{token}")
	public String addFactoryUser(@PathVariable String type,@PathVariable String token,@RequestBody FactoryUser user)  {
			restTemplateService.addFactoryUser(type, token, user);
		 return "User added";
			}
	@DeleteMapping("/rest/delete/{id}/{type}")
	public String deleteFactoryUser(@PathVariable Integer id,@PathVariable String type,@RequestParam String token)  {
			restTemplateService.deleteFactoryUser(id,type, token);
		 return "User deleted";
			}
	@PutMapping("/rest/update/{type}/{token}")
	public String updateFactoryUser(@PathVariable String type,@PathVariable String token,@RequestBody FactoryUser user)  {
			return restTemplateService.updateFactoryUser(type, token, user);
		 
			}
}
