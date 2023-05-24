package spring.all.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import spring.all.Entity.FactoryUser;
import spring.all.factoryInterfaces.DataBases;
import spring.all.repository.UserMongoRepo;

@Component
public class MongoCrud implements DataBases {

	@Autowired
	private UserMongoRepo userMongoRepo;
	@Autowired
	private MongoTemplate mongoTemplate;

	private static final Logger logger = LoggerFactory.getLogger(MongoCrud.class);

	@Override
	public List<FactoryUser> getUser() {
		logger.info("Retriving all users from Mongodb");
		MongoCollection<Document> col = mongoTemplate.getCollection("factoryUser");
		List<FactoryUser> data = new ArrayList<>();
		try (MongoCursor<Document> cursor = col.find().iterator()) {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				Integer id = doc.getInteger("_id");
				String name = doc.getString("name");
				String city = doc.getString("City");
				FactoryUser factoryUser = new FactoryUser();
				factoryUser.setId(id);
				factoryUser.setName(name);
				factoryUser.setCity(city);
				data.add(factoryUser);
			}
		}
		return data;
	}

	@Override
	public Optional<FactoryUser> getUserByid(Integer id) {
		logger.info("finding User in MongoDb with id : " + id);
		return userMongoRepo.findById(id);
	}

	@Override
	public String addUser(FactoryUser factoryUser) {
		logger.info("Adding User to MongoDb....");
		userMongoRepo.save(factoryUser);
		return "user added";
	}

	@Override
	public String updateUser(FactoryUser factoryUser) {
		logger.info("Updating User in MongoDb....");
		userMongoRepo.save(factoryUser);
		return "User updated";
	}

	@Override
	public String deleteUser(Integer id) {
		logger.info("Deleting User in MongoDb with id : " + id);
		userMongoRepo.deleteById(id);
		return "user deleted for id : " + id;
	}

}
