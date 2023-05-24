package spring.all.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import spring.all.Components.PythonScriptExecutor;
import spring.all.Components.UnixScriptExecutor;
import spring.all.Components.WindowsBatchScriptExecutor;
import spring.all.factoryInterfaces.ScriptExecutor;
@Configuration
public class ScriptExecutorFactory {
	@Autowired
	private PythonScriptExecutor pythonScriptExecutor;
	@Autowired
	private UnixScriptExecutor unixScriptExecutor;
	@Autowired
	private WindowsBatchScriptExecutor windowsBatchScriptExecutor;
	
    public  ScriptExecutor createScriptExecutor(String type, String scriptPath) {
        switch (type) {
            case "python"	: return pythonScriptExecutor;
            case "unix"		: return unixScriptExecutor;
            case "windows"	: return windowsBatchScriptExecutor;
            default:
                throw new IllegalArgumentException("Unsupported script type: " + type);
        }
    }
}
