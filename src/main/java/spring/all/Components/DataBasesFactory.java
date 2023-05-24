package spring.all.Components;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.all.factoryInterfaces.DataBases;

@Component
public class DataBasesFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(DataBasesFactory.class);
	@Autowired
	private SqlCrud sqlCrud;
	@Autowired
	private	MongoCrud mongoCrud;
	
    public DataBases createUserRepository(String type) {
        if (type.equalsIgnoreCase("mysql")) {
        	logger.info("Selecting DB : "+type);
            return sqlCrud;
        } else if (type.equalsIgnoreCase("mongodb")) {
        	logger.info("Selecting DB : "+type);
            return mongoCrud;
        } else {
        	logger.error("Invalid Selection : "+type);
            throw new IllegalArgumentException("Invalid database type : " + type);
        }
    }
}
