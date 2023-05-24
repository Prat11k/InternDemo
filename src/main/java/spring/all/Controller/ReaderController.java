package spring.all.Controller;

import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.all.service.ReaderService;


@RestController
@RequestMapping("/reading")
public class ReaderController {

	@Autowired
	private ReaderService readerService;
	
	@GetMapping("/read-files")
	public ResponseEntity<String> readWrite() throws EncryptedDocumentException, IOException {
		return readerService.readWrite();
	}
}
