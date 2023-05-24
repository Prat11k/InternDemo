package spring.all.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.all.Entity.FactoryUser;

@Service
@RequiredArgsConstructor
public class KafkaDataService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private KafkaProducer kafkaProducer;
	@Autowired
	private KafkaDataService kafkaDataService;
	int targetcount;
	int factorycount;
	Logger logger = LoggerFactory.getLogger(KafkaDataService.class);

	@Scheduled(fixedDelay = 10000)
	public void checkCount() {
		String sql = "SELECT count(*) FROM factory_user";
		String sql2 = "SELECT count(*) FROM target_user";
		targetcount = jdbcTemplate.queryForObject(sql2, Integer.class);
		// logger.info(String.format("inside checkcount :oldcount %s", targetcount));
		factorycount = jdbcTemplate.queryForObject(sql, Integer.class);
//		 logger.info(String.format("inside checkcount :newcount %s", factorycount));
		if (factorycount > targetcount) {
			kafkaProducer.sendMyData(kafkaDataService.fetchData());
		}
	}
	
//	@Scheduled(fixedDelay = 20000)
	@Scheduled(cron = "0 */15 * * * *")
	public List<FactoryUser> fetchData() {
		String sql1 = "SELECT * FROM factory_user";
		String sql4 = "SELECT * FROM target_user";
		logger.info("Executing fetch data at scheduled time using cron");
		logger.info("inside fetch data");
		String sql2 = "SELECT count(*) FROM target_user";
		String sql3 = "SELECT count(*) FROM factory_user";
		factorycount = jdbcTemplate.queryForObject(sql3, Integer.class);
		targetcount = jdbcTemplate.queryForObject(sql2, Integer.class);
		logger.info(String.format("inside fetchdata :oldcount %s", targetcount));
		List<FactoryUser> olddata = jdbcTemplate.query(sql1, new BeanPropertyRowMapper<>(FactoryUser.class));
		if (factorycount > targetcount) {
			List<FactoryUser> newdata = jdbcTemplate.query(sql4, new BeanPropertyRowMapper<>(FactoryUser.class));
			List<FactoryUser> extra = new ArrayList<FactoryUser>(olddata);
			extra.removeAll(newdata);
		 logger.info("returning extra added");
			return extra;
		}
		else
			return olddata;
	}
	
}
