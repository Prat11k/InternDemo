package spring.all.Components;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUD {

public final String UPLOAD_DIR="C:\\Users\\DELL\\OneDrive\\Desktop\\upload";
	
private static final Logger logger = LoggerFactory.getLogger(FileUD.class);
	public boolean uploadFile(MultipartFile file)
	{
		logger.info("inside method uploadFile");
		boolean files= false;
		try {			
		logger.info("setting upload path and getting file to upload");	
			Files.copy(file.getInputStream(),Paths.get(UPLOAD_DIR+File.separator+file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			files=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return files;
	}
	
}
