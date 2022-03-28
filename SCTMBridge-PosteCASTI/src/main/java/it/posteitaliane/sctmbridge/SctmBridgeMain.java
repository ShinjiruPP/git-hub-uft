package it.posteitaliane.sctmbridge;

import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class SctmBridgeMain {

	public static void main(String args[]) {
		ConfigurableApplicationContext context = null;
		String PROPERTIES_PATH = args[0];
		String PROPERTIES_NAME = args[1];
		try {
			Properties myPropertiesFile = SctmProperties.createProperties(PROPERTIES_PATH, PROPERTIES_NAME);
			String automationAppName = myPropertiesFile.getProperty("AUTOMATION_APP");
			String isSCTM = myPropertiesFile.getProperty("SCTM");
			if(Boolean.parseBoolean(isSCTM.toLowerCase())) {context = SpringApplication.run(SctmBridgeMain.class, args);}
			
			switch(automationAppName.toUpperCase()) {
				case "UFT":
					//download the report from UFT/Jenkins
					//Parse the report related to InserResultClass for insert the data on TA_LOG
					break;
			}
		}catch(Exception e) {
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
		}finally {
			if(context != null) {context.close();}
			System.exit(0);
		}
	}

}
