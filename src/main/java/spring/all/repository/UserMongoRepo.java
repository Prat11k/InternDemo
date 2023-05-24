package spring.all.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import spring.all.Entity.FactoryUser;

public interface UserMongoRepo extends MongoRepository<FactoryUser, Integer> {

	
}
