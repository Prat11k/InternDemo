package spring.all.Components;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.all.Entity.FactoryUser;
import spring.all.factoryInterfaces.DataBases;
import spring.all.repository.UserSqlRepo;

@Component
public class SqlCrud implements DataBases{
	@Autowired
	private UserSqlRepo userSqlRepo;

	private static final Logger logger = LoggerFactory.getLogger(SqlCrud.class);
	@Override
	public List<FactoryUser> getUser() {
		logger.info("Retriving all users from Sql");
		return userSqlRepo.findAll();
	}

	@Override
	public Optional<FactoryUser> getUserByid(Integer id) {
		logger.info("Finding user in Sql with id : "+id);
		return userSqlRepo.findById(id);
	}

	@Override
	public String addUser(FactoryUser factoryUser) {
		logger.info("Adding User to Sql....");
		userSqlRepo.save(factoryUser);
		return "user added";
	}

	@Override
	public String updateUser(FactoryUser factoryUser) {
		logger.info("Updating User in Sql....");
		userSqlRepo.save(factoryUser);
		return "user updated";
	}

	@Override
	public String deleteUser(Integer id) {
		logger.info("Deleting User in Sql with id : "+id);
		try {
		userSqlRepo.deleteById(id);
		return "user deleted for id : "+id;
		}catch(Exception e) {
		logger.error("User not found");
		return "User not found";
		}
	}
	
}
