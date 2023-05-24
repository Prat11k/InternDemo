package spring.all.Components;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.all.config.ScriptExecutorFactory;
import spring.all.customException.ScriptExecutorException;
import spring.all.factoryInterfaces.ScriptExecutor;

@Component
public class UnixScriptExecutor implements ScriptExecutor {

	   @Autowired
	    private ScriptExecutorFactory scriptExecutorFactory;
	    private static final Logger logger = LoggerFactory.getLogger(UnixScriptExecutor.class);
	
	@Override
	public String execute(String scriptPath) throws ScriptExecutorException {
		 scriptExecutorFactory.createScriptExecutor("unix", scriptPath);
		
		 try {
       	  logger.info("Executing Unix script: {}", scriptPath);
       	  ProcessBuilder processBuilder = new ProcessBuilder("C:/Program Files/Git/bin/bash.exe",scriptPath);
       	  logger.info("ProcessBuilder Started");
       	  processBuilder.redirectErrorStream(true);
       	  Process process = processBuilder.start();
       	  logger.info("Process Started");
       	  BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
       	  logger.info("reading Started");
       	 int exitCode= process.waitFor();
       	 
       	  String line;
       	  while ((line = reader.readLine()) != null) {
       		//  logger.info(line);
       	      System.out.println(line);
       	  }
       	  if (exitCode != 0) {
        		 logger.error("Unix script exited with code " + exitCode);
        		    throw new Exception("Unix script exited with code " + exitCode);
        		}
       } catch (Exception e) {
           throw new ScriptExecutorException("Unix script execution failed");
       }
		return "Unix Executing";
   }

}
