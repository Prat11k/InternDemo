package spring.all.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import spring.all.Entity.FactoryUser;
import spring.all.Entity.TargetFactoryUser;
import spring.all.repository.FactoryKafkaRepository;

@Service
public class KafkaConsumer {
	@Autowired
	private FactoryKafkaRepository factoryKafkaRepository;
	Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
	
	@KafkaListener(topics = "factory", groupId = "myGroup")
	public void processData(FactoryUser entity) {
		
		TargetFactoryUser targetFactoryUser=new TargetFactoryUser();
		logger.info(String.format("Sending -> %s", entity.toString()));
	    targetFactoryUser.setId(entity.getId());
	    targetFactoryUser.setCity(entity.getCity());
	    targetFactoryUser.setName(entity.getName());
	    
		factoryKafkaRepository.save(targetFactoryUser);
	
		
	}
}