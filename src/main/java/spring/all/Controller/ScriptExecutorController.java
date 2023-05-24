package spring.all.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.all.customException.ScriptExecutorException;
import spring.all.service.ScriptReaderService;

@RestController
@RequestMapping("/script")
public class ScriptExecutorController {
    @Autowired
    private ScriptReaderService scriptReaderService;
    
    @GetMapping("/readScript/{type}")
    public ResponseEntity<String> reader(@PathVariable String type, @RequestParam String scriptPath) throws ScriptExecutorException {
    	return scriptReaderService.reader(type, scriptPath);
    }  
}
