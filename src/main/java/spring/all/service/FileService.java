package spring.all.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import spring.all.Components.FileUD;

@Service
public class FileService {
	@Autowired
	private FileUD fileUD;
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	public ResponseEntity<String> uploadFile(MultipartFile multi) {
		logger.info("Inside upload file method");
		try {
			logger.info("Uploading file");
			boolean files = fileUD.uploadFile(multi);
			if (files) {
				logger.info("File uploaded successfully..");
				return ResponseEntity.ok("File is successfully uploaded");
			}
		} catch (Exception e) {
			logger.error("Failed to upload file");
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went Wrong");
	}

	public ResponseEntity<Resource> fileDownload(String path, String filename) throws IOException {
		logger.info("inside file download method");
		path = URLDecoder.decode(path, "UTF-8");
		filename = URLDecoder.decode(filename, "UTF-8");
		logger.info("requesting download path");
		Resource resource = new FileSystemResource(path + "\\" + filename);
		logger.info("filepath acquired");
		if (!resource.exists()) {
			logger.error("File not fount");
			throw new FileNotFoundException("File not found: " + resource.getFilename());
		}
		logger.info("file download complete");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	public List<List<String>> getDirectoryContents(String path) throws Exception {

		logger.info("inside method get directory");
		path = URLDecoder.decode(path, "UTF-8");
		logger.info("acquiring directory path");
		File directory = new File(path);
		logger.info("directory path acquired");
		logger.info("Getting contents of directory");
		String[] contents = directory.list();
		if (contents == null) {
			logger.error("Failed to acquire content of directory");
			throw new Exception("Failed to get contents of directory: " + path);
		}
		logger.info("content acquired");
		List<String> folders = new ArrayList<>();
		List<String> files = new ArrayList<>();
		logger.info("sorting content into file and directory");
		for (String content : contents) {
			File file = new File(directory, content);
			if (file.isDirectory()) {
				folders.add("Dir : " + content);
			} else if (file.isFile()) {
				files.add("File : " + content);
			}
		}
		List<List<String>> result = new ArrayList<>();
		result.add(folders);
		result.add(files);
		logger.info("Sorting success");
		return result;
	}

}
