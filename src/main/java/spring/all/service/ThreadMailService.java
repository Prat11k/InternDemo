package spring.all.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import spring.all.Components.EmailRequest;
import spring.all.Entity.Notification;
import spring.all.Entity.Status;

@Service
public class ThreadMailService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private MongoTemplate mongoTemplate;

	Logger logger = LoggerFactory.getLogger(ThreadMailService.class);

	public List<Notification> getAll() {
		logger.info("Inside method getAll");
		Aggregation agg = Aggregation.newAggregation(Aggregation.project("_id", "from", "to", "status", "date"),
				Aggregation.unwind("status"), Aggregation.project("_id", "from", "to", "status.status", "date"));
		AggregationResults<Document> results = mongoTemplate.aggregate(agg, "Notification", Document.class);
		List<Document> documents = results.getMappedResults();
		List<Notification> data = new ArrayList<>();
		for (Document doc : documents) {
			String uuid = doc.getString("_id");
			String from = doc.getString("from");
			String to = doc.getString("to");
			String status = doc.getString("status");
			Date date = doc.getDate("date");
			Notification info = new Notification();
			info.setUuid(uuid);
			info.setFrom(from);
			info.setTo(to);
			info.setDate(date);
			info.setStatus(Arrays.asList(new Status(status)));
			data.add(info);
		}
		logger.info("returning All Email Data");
		return data;
	}

	public List<Notification> getFailed(String status) {
		logger.info("Inside method getFailed");
		Aggregation agg = Aggregation.newAggregation(Aggregation.project("_id", "from", "to", "status", "date"),
				Aggregation.unwind("status"), Aggregation.match(Criteria.where("status.status").is(status)),
				Aggregation.project("_id", "from", "to", "status.status", "date"));
		AggregationResults<Document> results = mongoTemplate.aggregate(agg, "Notification", Document.class);
		List<Document> documents = results.getMappedResults();

		List<Notification> data = new ArrayList<>();
		for (Document doc : documents) {
			String uuid = doc.getString("_id");
			String from = doc.getString("from");
			String to = doc.getString("to");
			String statusl = doc.getString("status");
			Date date = doc.getDate("date");
			Notification info = new Notification();
			info.setUuid(uuid);
			info.setFrom(from);
			info.setTo(to);
			info.setDate(date);
			info.setStatus(Arrays.asList(new Status(statusl)));
			data.add(info);
		}
		logger.info("returning with Failed status");
		return data;
	}

	public List<String> getStatus(String status) {
		logger.info("Inside method getStatus");
		Aggregation agg = Aggregation.newAggregation(Aggregation.project("_id", "from", "to", "status"),
				Aggregation.unwind("status"), Aggregation.match(Criteria.where("status.status").is(status)),
				Aggregation.project("to")

		);
		AggregationResults<Document> results = mongoTemplate.aggregate(agg, "Notification", Document.class);
		List<Document> documents = results.getMappedResults();

		List<String> data = new ArrayList<>();
		for (Document doc : documents) {
			String to = doc.getString("to");
			List<String> retry = new ArrayList<>();
			retry.add(to);
			data.addAll(retry);
		}
		logger.info("returning Status");
		return data;
	}

	public ResponseEntity<String> sendEmail(EmailRequest emailRequest) {
		logger.info("Inside method sendEmail");
		try {
			emailService.sendEmailToMultipleAddresses(emailRequest.getEmailAddresses(), emailRequest.getSubject(),
					emailRequest.getMessage());
			return new ResponseEntity<>("Email Data sent successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Failed to send email data", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
