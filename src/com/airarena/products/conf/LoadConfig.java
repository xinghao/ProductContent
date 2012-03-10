package com.airarena.products.conf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class LoadConfig {
	
	private static final Logger _logger = Logger.getLogger(LoadConfig.class);
	
	public static void loadConfig(String configfilePath, String logFilePath) throws FileNotFoundException, IOException {
		// Read log4j property file here
		PropertyConfigurator.configure("conf/log4j.properties");
		_logger.info("Loading app config");
		
		 // Read app's/api properties file.
	    Properties properties = new Properties();

        properties.load(new FileInputStream(configfilePath));
        for (String pKey :properties.stringPropertyNames()) {	        	
        	_logger.info("[Properties]Key=" + pKey + ", Value="+properties.getProperty(pKey));
        	System.out.println("[Properties]Key=" + pKey + ", Value="+properties.getProperty(pKey));
        	System.setProperty(pKey, properties.getProperty(pKey));
        }	        
		
	}

}
