package perfecto;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class UploadFile {
	
	public static boolean uploadFile(Properties myPropertiesFile) {
		
		String CLOUD_NAME = myPropertiesFile.getProperty("CLOUD_NAME").toLowerCase();
		final String OFFLINE_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJkMjRiMGYxYy0yOTMxLTQ3OTAtYWRiNC01ZWU0NmEzNGQzYTgifQ."
				+ "eyJpYXQiOjE2NDE5MjA2NzcsImp0aSI6IjBjOWM0NWRlLTM3NjctNDBhMy1iNDYxLTQ0M2JhNTNiZDI5NCIsImlzcyI6Imh0dHBzOi8vYXV0aDIucGVyZmVjdG9tb"
				+ "2JpbGUuY29tL2F1dGgvcmVhbG1zL2FjY2VudHVyZXRpLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aDIucGVyZmVjdG9tb2JpbGUuY29tL2F"
				+ "1dGgvcmVhbG1zL2FjY2VudHVyZXRpLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6IjJmNzdhNjM1LTY3MGItNGQ1ZC04ZmU3LTYyMTE3OTA5YjU4ZSIsInR5cCI6I"
				+ "k9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiZDgyZGEyZjItYmQ3MC00YTkzLTlhMDItNjUzOTk0MDM0Nzk1Iiwic2Vzc2lv"
				+ "bl9zdGF0ZSI6ImRhM2E5OTgzLWRmMTQtNGEwNC1hNmE5LTQ2MTNlNGFjYjgwNyIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIGVtYWlsIHByb2ZpbGUifQ."
				+ "88sTiF9KCOZ0f_WJFQ8Ysgth8tzCirrs4SXEpRWroeQ";
		String OS = myPropertiesFile.getProperty("OS").toUpperCase();
		String BUILD_PATH = myPropertiesFile.getProperty("BUILD_PATH");
		String BUILD_NAME = myPropertiesFile.getProperty("BUILD_NAME");
		
		try {
			if(OS.equals("ANDROID")) {
				PerfectoLabUtils.uploadMedia(CLOUD_NAME, OFFLINE_TOKEN, BUILD_PATH + File.separator + BUILD_NAME, "PUBLIC:svilApp.apk");
			}else if(OS.equals("IOS")) {
				PerfectoLabUtils.uploadMedia(CLOUD_NAME, OFFLINE_TOKEN, BUILD_PATH + File.separator + BUILD_NAME, "PUBLIC:svilApp.ipa");
			}
			return true;
		}catch(Exception e) {
			System.err.println("Si è verificato un errore nella uploadFile: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return false;
		}
		
	}

}
