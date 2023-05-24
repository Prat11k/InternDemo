package spring.all.Controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.all.Entity.FactoryUser;
import spring.all.customException.MyException;
import spring.all.service.CrudService;

@RestController
@RequestMapping("/factorycrud")

public class UserController {

	@Autowired
	private CrudService crudService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/add/{type}")
	public String addUser(@RequestBody FactoryUser factoryUser, @PathVariable String type) {
		logger.info("Inside add method");
		crudService.addUser(factoryUser, type);
		return "User added";
	}

	@GetMapping("/id/{id}/{type}")
	public Optional<FactoryUser> findById(@PathVariable Integer id, @PathVariable String type) throws MyException {
		logger.info("Inside finById method");
		return crudService.findById(id, type);
	}

	@GetMapping("/getall/{type}")
	public List<FactoryUser> findAll(@PathVariable String type) {
		logger.info("Inside findall method");
		return crudService.findAll(type);
	}

	@DeleteMapping("/delete/{id}/{type}")
	public ResponseEntity<String> delete(@PathVariable Integer id, @PathVariable String type) throws MyException {
		logger.info("Inside delete method");
		return crudService.delete(id, type);
	}

	@PutMapping("/update/{type}")
	public String update(@RequestBody FactoryUser factoryUser, @PathVariable String type) throws Exception {
		logger.info("Inside update method");
		crudService.update(factoryUser, type);
		return "User Updated";
	}
}
