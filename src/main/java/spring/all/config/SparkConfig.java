package spring.all.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

	@Bean
	public SparkSession sparkSession() {
		SparkSession spark = SparkSession.builder().appName("File Reader").master("local[*]").getOrCreate();
		return spark;
	}

}
