package perfecto;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;
import org.springframework.context.ConfigurableApplicationContext;

import it.posteitaliane.sctmbridge.InsertResultClass;
import it.posteitaliane.sctmbridge.InsertService;
import proxy.ProxyConfiguration;

public class SwitchToPerfecto {

	private static String executionId;

	public static void executeSwitchToPerfecto(Properties myPropertiesFile, ConfigurableApplicationContext context) {

		try {
			if(ProxyConfiguration.isProxyEnabled(myPropertiesFile)) {
				ProxyConfiguration.setProxyConfiguration(myPropertiesFile);
			}
			boolean isAppUploaded = true;
			boolean isUpload = Boolean.parseBoolean(myPropertiesFile.getProperty("UPLOAD"));
			if(isUpload) {
				isAppUploaded = UploadFile.uploadFile(myPropertiesFile);
			}
			if(isAppUploaded) {
				executionId = StartExecution.startExecution(myPropertiesFile);
			}

			if(executionId != null) {
				String reportString = FetchAnalyzeReport.fetchReport(myPropertiesFile, executionId);
				System.out.println(reportString);
				if(reportString != null) {
					String REPORT_PATH = myPropertiesFile.getProperty("REPORT_PATH");
					JSONObject myReportJson = new JSONObject(reportString);
					StringBuilder myHTML = FetchAnalyzeReport.jsonToHtml(myReportJson);
					FetchAnalyzeReport.saveReportFile(reportString, REPORT_PATH);
					FetchAnalyzeReport.saveReportFile(myHTML, REPORT_PATH);
				}else {
					System.err.println("Impossibile recuperare il report dell'esecuzione. ReportString == null");
				}
				if((context != null && executionId != null)) {
					ArrayList<InsertResultClass> myResultsArrayList = FetchAnalyzeReport.analyzeReport(reportString, myPropertiesFile);
					InsertService.insertResult(myResultsArrayList);
				}
			}else {
				System.err.println("Impossibile recuperare il report dell'esecuzione. ExecutionID == null");
			}
		}catch(Exception e) {
			System.err.println("Si è verificato un errore: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
		}
	}

}
