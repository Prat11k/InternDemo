package spring.all.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.all.service.MillionCsvReader;

@RestController
@RequestMapping("/csvM")
public class CsvMillionController {
	@Autowired
	private MillionCsvReader millionCsvReader;
	@GetMapping("/readM")
	public String read() {
		millionCsvReader.readerWritter();
	return "Reading and Writing Complete";
	}
}
