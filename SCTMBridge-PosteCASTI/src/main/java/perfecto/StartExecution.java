package perfecto;

import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class StartExecution {
	
	public static String startExecution(Properties myPropertiesFile) {
		final String CLOUD_NAME = myPropertiesFile.getProperty("CLOUD_NAME").toLowerCase();
		final String OFFLINE_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJkMjRiMGYxYy0yOTMxLTQ3OTAtYWRiNC01ZWU0NmEzNGQzYTgifQ."
				+ "eyJpYXQiOjE2NDE5MjA2NzcsImp0aSI6IjBjOWM0NWRlLTM3NjctNDBhMy1iNDYxLTQ0M2JhNTNiZDI5NCIsImlzcyI6Imh0dHBzOi8vYXV0aDIucGVyZmVjdG9tb"
				+ "2JpbGUuY29tL2F1dGgvcmVhbG1zL2FjY2VudHVyZXRpLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aDIucGVyZmVjdG9tb2JpbGUuY29tL2F"
				+ "1dGgvcmVhbG1zL2FjY2VudHVyZXRpLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6IjJmNzdhNjM1LTY3MGItNGQ1ZC04ZmU3LTYyMTE3OTA5YjU4ZSIsInR5cCI6I"
				+ "k9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiZDgyZGEyZjItYmQ3MC00YTkzLTlhMDItNjUzOTk0MDM0Nzk1Iiwic2Vzc2lv"
				+ "bl9zdGF0ZSI6ImRhM2E5OTgzLWRmMTQtNGEwNC1hNmE5LTQ2MTNlNGFjYjgwNyIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIGVtYWlsIHByb2ZpbGUifQ."
				+ "88sTiF9KCOZ0f_WJFQ8Ysgth8tzCirrs4SXEpRWroeQ";
		//final String OFFLINE_TOKEN = myPropertiesFile.getProperty("OFFLINE_TOKEN");
		final String SCRIPT_VISIBILITY = myPropertiesFile.getProperty("SCRIPT_VISIBILITY").toUpperCase();
		final String SCRIPT_PATH = myPropertiesFile.getProperty("SCRIPT_PATH");
		final String SCRIPT_NAME = myPropertiesFile.getProperty("SCRIPT_NAME");
	
		try {
			final URIBuilder myUriBuilder = new URIBuilder("https://" + CLOUD_NAME + ".perfectomobile.com/services/executions");
			myUriBuilder.addParameter("operation", "execute");
			myUriBuilder.addParameter("scriptKey", SCRIPT_VISIBILITY + ":" + SCRIPT_PATH + "//" + SCRIPT_NAME);
			myUriBuilder.addParameter("securityToken", OFFLINE_TOKEN);
			final URI myUri = myUriBuilder.build();
			
			HttpClient myClient = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder(myUri).build();
			HttpResponse<String> response = myClient.send(request, BodyHandlers.ofString());
			String myResponseBody = response.body();
			
			JSONObject myJsonObject = new JSONObject(myResponseBody);
			String myExecutionId = myJsonObject.getString("executionId");
			getExecutionStatus(CLOUD_NAME, myExecutionId, OFFLINE_TOKEN);
			return myExecutionId;
		}catch(Exception e) {
			System.out.println("Si è verificato un problema nella startExecution: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return null;
		}
	}
	
	private static void getExecutionStatus(String CLOUD_NAME, String executionIDString, String OFFLINE_TOKEN) {
		
		try {
			final URIBuilder myUriBuilder = new URIBuilder("https://" + CLOUD_NAME + ".perfectomobile.com/services/executions/" + executionIDString);
			myUriBuilder.addParameter("operation", "status");
			myUriBuilder.addParameter("securityToken", OFFLINE_TOKEN);
			final URI myUri = myUriBuilder.build();
			
			HttpClient myClient = HttpClient.newHttpClient();
			HttpRequest myRequest = HttpRequest.newBuilder(myUri).build();
			
			boolean isNotCompleted = true;
			while(isNotCompleted){
				HttpResponse<String> response = myClient.send(myRequest, BodyHandlers.ofString());
				String myResponseBody = response.body();
				
				JSONObject myResponseJsonObject = new JSONObject(myResponseBody);
				String descriptionString = myResponseJsonObject.getString("description");
				if(descriptionString.equals("Completed")) {
					isNotCompleted = !isNotCompleted;
				}else {
					Thread.sleep(5000);
				}
			}
			
		}catch(Exception e) {
			System.out.println("Si è verificato un problema nella getExecutionStatus: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
		}
	}

}
