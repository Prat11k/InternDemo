package spring.all.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import spring.all.config.ReaderCallable;
@Service
public class ThreadReaderService {

	@Autowired
	private ReaderCallable rc;
	Logger logger = LoggerFactory.getLogger(ThreadReaderService.class);
	public ResponseEntity<String> csvreaderWritter(){
		try {
			logger.info("start reading and writing csv file....");
			rc.readWritecsv();
			return ResponseEntity.ok("file received successfully performing Reading and Writing.........");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.ok("file reading failed");
		}
	}
}
