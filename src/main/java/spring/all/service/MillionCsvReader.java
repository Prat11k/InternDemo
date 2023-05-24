package spring.all.service;

import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MillionCsvReader {
	@Autowired
	private SparkSession spark;
	Logger logger = LoggerFactory.getLogger(MillionCsvReader.class);
	public void readerWritter() {

		Properties connectionProperties = new Properties();
		connectionProperties.put("user", "root");
		connectionProperties.put("password", "Pratik@123456");
		connectionProperties.put("driver", "com.mysql.cj.jdbc.Driver");
		connectionProperties.put("url", "jdbc:mysql://localhost:3306/infdb");
		connectionProperties.setProperty("batchsize", "1000");
		logger.info("starting to read a csv file");
		Dataset<Row> csvDataSet = spark.read().option("header", "true") // Use first row as header
				.option("inferSchema", "true") // Infer data types automatically
				.csv("D:\\CSV\\convertcsv1.csv");
		logger.info("reading complete");
		csvDataSet.write().mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/infdb", "TestCSV",
				connectionProperties);
		logger.info("writing complete");
	}
}
