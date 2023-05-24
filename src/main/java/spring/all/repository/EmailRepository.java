package spring.all.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import spring.all.Entity.Notification;

public interface EmailRepository extends MongoRepository<Notification, String> {

}
