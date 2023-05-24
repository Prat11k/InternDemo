package spring.all.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import spring.all.config.ScriptExecutorFactory;
import spring.all.customException.ScriptExecutorException;
import spring.all.factoryInterfaces.ScriptExecutor;
@Service
public class ScriptReaderService {

    @Autowired
    private ScriptExecutorFactory scriptExecutorFactory;
    private static final Logger logger = LoggerFactory.getLogger(ScriptReaderService.class);
    
    public ResponseEntity<String> reader(@PathVariable String type, @RequestParam String scriptPath) throws ScriptExecutorException {
        try {
        	logger.info("inside ScriptReader");
            logger.info("selecting script reader type");
        	ScriptExecutor scriptExecutor = scriptExecutorFactory.createScriptExecutor(type, scriptPath);
            logger.info("executing {} : "+type);
            String output = scriptExecutor.execute(scriptPath);
            logger.info("Script Executed");
            return ResponseEntity.ok(output);
        } catch (ScriptExecutorException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ScriptExecutorException("Error Executing Script");
        }
    }
    
}
