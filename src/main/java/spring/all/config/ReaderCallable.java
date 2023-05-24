package spring.all.config;

import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReaderCallable implements Callable<Boolean> {

	@Autowired
	private SparkSession spark;
	Logger logger = LoggerFactory.getLogger(ReaderCallable.class);
	@Override
	public Boolean call() throws Exception {

		Properties connectionProperties = new Properties();
		connectionProperties.put("user", "root");
		connectionProperties.put("password", "Pratik@123456");
		connectionProperties.put("driver", "com.mysql.cj.jdbc.Driver");
		connectionProperties.put("url", "jdbc:mysql://localhost:3306/infdb");

		Dataset<Row> csvDataSet = spark.read().option("header", "true") // Use first row as header
				.option("inferSchema", "true") // Infer data types automatically
				.csv("D:\\CSV\\convertcsv1.csv");
		logger.info("Reading Complete");
		csvDataSet.write().mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/infdb", "csvmillion2",
				connectionProperties);
		logger.info("Writing Complete");
		return true;
	}

	public FutureTask<Boolean> readWritecsv() {
		FutureTask<Boolean> futureTask = new FutureTask<>(this);
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		executorService.submit(futureTask);
		return futureTask;
	}
}
