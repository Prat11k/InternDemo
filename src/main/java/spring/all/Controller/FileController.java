package spring.all.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import spring.all.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {
	@Autowired
	private FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multi) {
		return fileService.uploadFile(multi);
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> fileDownload(@RequestParam("path") String path,
			@RequestParam("filename") String filename) throws IOException {
		return fileService.fileDownload(path, filename);
	}

	@GetMapping("/directory")
	public List<List<String>> getDirectoryContents(@RequestParam("path") String path) throws Exception {
		return fileService.getDirectoryContents(path);
	}

}
