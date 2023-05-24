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
public class WindowsBatchScriptExecutor implements ScriptExecutor {

    private static final Logger logger = LoggerFactory.getLogger(WindowsBatchScriptExecutor.class);

    @Autowired
    private ScriptExecutorFactory scriptExecutorFactory;

    @Override
    public String execute(String scriptPath) throws ScriptExecutorException {
        scriptExecutorFactory.createScriptExecutor("windows", scriptPath);
        try {
        	  logger.info("Executing Windows batch script: {}", scriptPath);
        	  ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c",scriptPath);
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
         		 logger.error("Batch script exited with code " + exitCode);
         		    throw new Exception("Batch script exited with code " + exitCode);
         		}
        	  
        } catch (Exception e) {
            throw new ScriptExecutorException("Batch script execution failed");
        }
		return "windows Executing";
    }
}
