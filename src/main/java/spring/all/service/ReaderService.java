package spring.all.service;

import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReaderService {

	@Autowired
	private SparkSession spark;

	Logger logger = LoggerFactory.getLogger(ReaderService.class);
	public ResponseEntity<String> readWrite() throws EncryptedDocumentException, IOException {
		Properties connectionProperties = new Properties();
		connectionProperties.put("user", "root");
		connectionProperties.put("password", "Pratik@123456");
		connectionProperties.put("driver", "com.mysql.cj.jdbc.Driver");
		connectionProperties.put("url", "jdbc:mysql://localhost:3306/infdb");
//csvFile
		logger.info("reading and writing csv file");
		Dataset<Row> csvDataSet = spark.read().option("header", "true") // Use first row as header
				.option("inferSchema", "true") // Infer data types automatically
				.csv("file:///C:\\Users\\DELL\\Downloads\\ford_escort.csv");
		Dataset<Row> csvDataFrame = csvDataSet.toDF();
		csvDataFrame.createOrReplaceTempView("Tempcsv");
		Dataset<Row> tempData = spark.sql("SELECT * from Tempcsv");
		// csvDataFrame.show();
		tempData.show();
		csvDataFrame.write().mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/infdb", "sparkcsv",
				connectionProperties);
//psvFile
		logger.info("reading and writing psv file");
		Dataset<Row> psvDataSet = spark.read()
				// .option("header", "true")	
				.option("inferSchema", "true").option("delimiter", "|") // to seprate file using special
																		// character('|'',''t')
				.csv("D:\\boot\\SamplePSVFile_2kb.txt");
		Dataset<Row> psvDataFrame = psvDataSet.toDF();

		psvDataFrame.show();

		psvDataFrame.write().mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/infdb", "sparkpsv",
				connectionProperties);
//tsvFile
		logger.info("reading and writing tsv file");
		Dataset<Row> tsvDataSet = spark.read().option("header", "true").option("inferSchema", "true")
				.option("delimiter", "\t").csv("D:\\boot\\mlb_players.tsv");
		Dataset<Row> tsvDataFrame = tsvDataSet.toDF();

		tsvDataFrame.show();

		tsvDataFrame.write().mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/infdb", "sparktsv",
				connectionProperties);

//JsonFile
		logger.info("reading and writing Json file");
		Dataset<Row> jsonDataSet = spark.read().option("header", "true").option("inferSchema", "true")
				.option("multiline", "true").option("dropMalformed", "true") // to drop corrupt rows
				.json("C:\\Users\\DELL\\Downloads\\example_2.json");

		Dataset<Row> jsonDataFrame = jsonDataSet.toDF();
		Dataset<String> jsonStrings = jsonDataFrame.toJSON();
		jsonStrings.show();
		jsonStrings.write().mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/infdb", "sparkjson",
				connectionProperties);
//xlsxFile
		logger.info("reading and writing xlsx file");
		Dataset<Row> xlsxDataSet = spark.read().format("com.crealytics.spark.excel").option("header", "true")
				.option("inferSchema", "true").load("C:\\Users\\DELL\\Downloads\\file_example_XLSX_50.xlsx");
		Dataset<Row> xlsxDataFrame = xlsxDataSet.toDF();

//		String sheetName = ExcelFileSaver.DEFAULT_SHEET_NAME();

		xlsxDataFrame.select("Gender").show();

		xlsxDataFrame.write().mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/infdb", "sparkxlsx",
				connectionProperties);

//		spark.stop();
		return ResponseEntity.ok("reading Done : "); // +sheetName);

	}
}
