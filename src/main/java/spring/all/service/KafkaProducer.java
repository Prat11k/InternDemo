package spring.all.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import spring.all.Entity.FactoryUser;

@Service
public class KafkaProducer {
	Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
	@Autowired
	private KafkaTemplate<String, FactoryUser> kafkaTemplate;

	public void sendMyData(List<FactoryUser> myDataList) {
		for (FactoryUser factoryUser : myDataList) {
			logger.info(String.format("Receiving -> %s", factoryUser));
			kafkaTemplate.send("factory", factoryUser);
		}
	}
}