package spring.all.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import spring.all.Components.DataBasesFactory;
import spring.all.Entity.FactoryUser;
import spring.all.customException.MyException;
import spring.all.factoryInterfaces.DataBases;

@Service
public class CrudService {

	@Autowired
	private DataBasesFactory dataBaseFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(CrudService.class);

	public String addUser(FactoryUser factoryUser, String type) {
		logger.info("Inside adduser method");
		logger.info("Creating Repository for type : " + type);
		DataBases db = dataBaseFactory.createUserRepository(type);
		logger.info("Adding User");
		db.addUser(factoryUser);
		return "User added";
	}

	public Optional<FactoryUser> findById(@PathVariable Integer id, @PathVariable String type) throws MyException {
		logger.info("Inside findbyid method");
		logger.info("Creating Repository for type : " + type);
		DataBases db = dataBaseFactory.createUserRepository(type);
		logger.info("Finding User");
		return db.getUserByid(id);
	}

	public List<FactoryUser> findAll(@PathVariable String type) {
		logger.info("Inside getall method");
		logger.info("Creating Repository for type : " + type);
		DataBases db = dataBaseFactory.createUserRepository(type);
		logger.info("Retriving Users");
		return db.getUser();
	}

	public ResponseEntity<String> delete(Integer id, @PathVariable String type) throws MyException {
		logger.info("Inside delete method");
		logger.info("Creating Repository for type : " + type);
		try {
			DataBases db = dataBaseFactory.createUserRepository(type);
			db.deleteUser(id);
			return ResponseEntity.ok("User deleted for id : " + id);
		} catch (Exception e) {
			logger.info("Deleting User with id : " + id);
			throw new MyException("Required USER not found");
		}
	}

	public String update(@RequestBody FactoryUser factoryUser, @PathVariable String type) throws Exception {
		logger.info("Inside update method");
		logger.info("Creating Repository for type : " + type);
		DataBases db = dataBaseFactory.createUserRepository(type);
		db.updateUser(factoryUser);
		logger.info("Updating User");
		return "User Updated";
	}
}
