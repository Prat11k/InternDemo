package spring.all.factoryInterfaces;

import spring.all.customException.ScriptExecutorException;

public interface ScriptExecutor {
	String execute(String scriptPath) throws ScriptExecutorException;
}
