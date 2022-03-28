package perfecto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.posteitaliane.sctmbridge.InsertResultClass;

public class FetchAnalyzeReport {

	static ArrayList<InsertResultClass> myResultsArrayList = new ArrayList<InsertResultClass>();

	public static String fetchReport(Properties myPropertiesFile, String executionId) {

		String CLOUD_NAME = myPropertiesFile.getProperty("CLOUD_NAME").toLowerCase();
		final String OFFLINE_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJkMjRiMGYxYy0yOTMxLTQ3OTAtYWRiNC01ZWU0NmEzNGQzYTgifQ."
				+ "eyJpYXQiOjE2NDE5MjA2NzcsImp0aSI6IjBjOWM0NWRlLTM3NjctNDBhMy1iNDYxLTQ0M2JhNTNiZDI5NCIsImlzcyI6Imh0dHBzOi8vYXV0aDIucGVyZmVjdG9tb"
				+ "2JpbGUuY29tL2F1dGgvcmVhbG1zL2FjY2VudHVyZXRpLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aDIucGVyZmVjdG9tb2JpbGUuY29tL2F"
				+ "1dGgvcmVhbG1zL2FjY2VudHVyZXRpLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6IjJmNzdhNjM1LTY3MGItNGQ1ZC04ZmU3LTYyMTE3OTA5YjU4ZSIsInR5cCI6I"
				+ "k9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiZDgyZGEyZjItYmQ3MC00YTkzLTlhMDItNjUzOTk0MDM0Nzk1Iiwic2Vzc2lv"
				+ "bl9zdGF0ZSI6ImRhM2E5OTgzLWRmMTQtNGEwNC1hNmE5LTQ2MTNlNGFjYjgwNyIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIGVtYWlsIHByb2ZpbGUifQ."
				+ "88sTiF9KCOZ0f_WJFQ8Ysgth8tzCirrs4SXEpRWroeQ";

		try {
			String REPORTING_SERVER_URL = "https://" + CLOUD_NAME + ".reporting.perfectomobile.com";
			URIBuilder myUriBuilder = new URIBuilder(REPORTING_SERVER_URL + "/export/api/v1/test-executions");
			myUriBuilder.addParameter("externalId[0]", executionId);
			final URI myUri = myUriBuilder.build();
			Thread.sleep(10000);
			HttpClient myClient = HttpClient.newHttpClient();
			HttpRequest myRequest = HttpRequest.newBuilder(myUri).header("PERFECTO-AUTHORIZATION", OFFLINE_TOKEN).build();
			HttpResponse<String> myResponse = myClient.send(myRequest, BodyHandlers.ofString());
			String myResponseBody = myResponse.body();
			JSONObject myReportObject = new JSONObject(myResponseBody);
			JSONArray myReportFirstJsonArray = myReportObject.getJSONArray("resources");
			JSONObject myObjectWithID = myReportFirstJsonArray.getJSONObject(0);
			String resourceID = myObjectWithID.getString("id");
			String reportString = fetchReportCommands(CLOUD_NAME, REPORTING_SERVER_URL, OFFLINE_TOKEN, resourceID);
			return reportString;
		}catch(Exception e) {
			System.err.println("Si è verificato un errore nella fetchReport: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return null;
		}
	}

	private static String fetchReportCommands(String CLOUD_NAME, String REPORTING_SERVER_URL, String OFFLINE_TOKEN, String resourceID) {

		try {
			String reportString = "";
			URIBuilder myUriBuilder = new URIBuilder(REPORTING_SERVER_URL + "/export/api/v1/test-executions/" + resourceID + "/commands");
			final URI myUri = myUriBuilder.build();
			HttpClient myClient = HttpClient.newHttpClient();
			HttpRequest myRequest = HttpRequest.newBuilder(myUri).header("PERFECTO-AUTHORIZATION", OFFLINE_TOKEN).build();
			while(reportString.equals("")) {
				HttpResponse<String> myResponse = myClient.send(myRequest, BodyHandlers.ofString());
				reportString = myResponse.body();
				if(reportString.equals("")) {
					Thread.sleep(20000);
				}else {

				}
			}
			return reportString;
		}catch(Exception e) {
			System.out.println("Si è verificato un errore nella fetchReportCommands: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return null;
		}
	}

	public static void saveReportFile(String reportString, String REPORT_PATH) {
		String now = LocalDateTime.now().toString().replace('-', '_').replace(":", "");
		File myReportFile = new File(REPORT_PATH + File.separator + "Perfecto" + "_" + now + ".txt");
		try {
			myReportFile.createNewFile();
			FileWriter myFileWriter = new FileWriter(myReportFile);
			myFileWriter.write(reportString);
			myFileWriter.close();
		} catch (IOException e) {
			System.out.println("Si è verificato un errore nella saveReportFile: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
		}
	}

	public static void saveReportFile(StringBuilder reportStringBuilder, String REPORT_PATH) {
		String reportString = reportStringBuilder.toString();
		String now = LocalDateTime.now().toString().replace('-', '_').replace(":", "");
		File myReportFile = new File(REPORT_PATH + File.separator + "Perfecto" + "_" + now + "_HTML.txt");
		try {
			myReportFile.createNewFile();
			FileWriter myFileWriter = new FileWriter(myReportFile);
			myFileWriter.write(reportString);
			myFileWriter.close();
		} catch (IOException e) {
			System.err.println("Si è verificato un errore nella saveReportFile: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
		}
	}

	private static ArrayList<InsertResultClass> setIdNomeVersioneScript(ArrayList<AssertPositioningClass> cdtIdPositioningArrayList, ArrayList<AssertPositioningClass> scriptIdPositioningArrayList) {
		try {
			for(int i = 0; i < myResultsArrayList.size(); i++) {
				AssertPositioningClass singleCdtIdAssertPositioning = cdtIdPositioningArrayList.get(i);
				int commandsArrayNumberCdtId = singleCdtIdAssertPositioning.getCommandsArrayNumber();
				int positionCdtId = singleCdtIdAssertPositioning.getPosition();
				for(int iterator = 0; iterator < scriptIdPositioningArrayList.size(); iterator++) {
					AssertPositioningClass singleScriptIdAssertPositioning = scriptIdPositioningArrayList.get(iterator);
					int commandsArrayNumberScriptId = singleScriptIdAssertPositioning.getCommandsArrayNumber();
					int positionScriptId = singleScriptIdAssertPositioning.getPosition();
					if((commandsArrayNumberCdtId == commandsArrayNumberScriptId) && (positionCdtId > positionScriptId)) {
						myResultsArrayList.get(i).setNOME_SCRIPT(scriptIdPositioningArrayList.get(iterator).getID());
						break;
					}else {
						boolean isBefore = true;
						ArrayList<AssertPositioningClass> singleArray = new ArrayList<AssertPositioningClass>();
						for(int iterator2 = 0; iterator2 < scriptIdPositioningArrayList.size(); iterator2++) {
							if(scriptIdPositioningArrayList.get(iterator2).getCommandsArrayNumber() == commandsArrayNumberCdtId) {singleArray.add(scriptIdPositioningArrayList.get(iterator2));}
						}
						for(int iterator3 = 0; iterator3 < singleArray.size(); iterator3++) {
							if(commandsArrayNumberCdtId < singleArray.get(iterator3).getPosition()) {}else {isBefore = false;}
						}
						int targetArrayPosition = commandsArrayNumberCdtId - 1;
						ArrayList<AssertPositioningClass> arrayListPrecedente = new ArrayList<AssertPositioningClass>();
						if(isBefore) {
							for(int iterator4 = 0; iterator4 < scriptIdPositioningArrayList.size(); iterator4++) {
								if(scriptIdPositioningArrayList.get(iterator4).getCommandsArrayNumber() == targetArrayPosition) {
									arrayListPrecedente.add(scriptIdPositioningArrayList.get(iterator4));
								}
							}
						}
						myResultsArrayList.get(i).setNOME_SCRIPT(arrayListPrecedente.get(arrayListPrecedente.size() - 1).getID());
					}
				}
			}
			return myResultsArrayList;
		}catch(Exception e) {
			System.err.println("Si è verificato un errore nella setIdNomeVersioneScript: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return null;
		}
	}

	private static ArrayList<InsertResultClass> getAllResults(JSONArray myResourcesJsonArray) {
		try {
			int iterator = 0;
			ArrayList<AssertPositioningClass> cdtIdPositioningArrayList = new ArrayList<AssertPositioningClass>();
			ArrayList<AssertPositioningClass> scriptIdPositioningArrayList = new ArrayList<AssertPositioningClass>();
			for(Object commands : myResourcesJsonArray) {
				iterator++;

				String commandsString = commands.toString();
				JSONObject commandsJsonObject = new JSONObject(commandsString);
				JSONArray singleArrayJsonArray = commandsJsonObject.getJSONArray("commands");
				InsertResultClass mySingleResultObject = new InsertResultClass();
				AssertPositioningClass singleAssertScriptId = new AssertPositioningClass();
				for(int i = 0; i < singleArrayJsonArray.length(); i++) {
					JSONObject singleObjectJsonObject = singleArrayJsonArray.getJSONObject(i);
					String name = singleObjectJsonObject.getString("name");
					String message = null;
					Long startTime = singleObjectJsonObject.getLong("startTime");
					if(singleObjectJsonObject.optString("message").equals("")) {} else {message = singleObjectJsonObject.getString("message");}
					if((name != null && message != null) && (name.equals("Assert") && message.startsWith("CDT ID"))) {
						AssertPositioningClass singleAssertCdtId = new AssertPositioningClass();
						String[] messageSplit = message.split("CDT ID");
						String[] idSplit = messageSplit[1].split(":");
						String CDT_ID = idSplit[0].trim();
						String CDT_RESULT = idSplit[1].trim();
						String cdtResultDaParsare = CDT_RESULT.substring(1);
						cdtResultDaParsare = cdtResultDaParsare.toLowerCase();
						char char1 = CDT_RESULT.charAt(0);
						CDT_RESULT = char1 + cdtResultDaParsare;
						mySingleResultObject.setID_TEST_SCTM(Integer.parseInt(CDT_ID));
						mySingleResultObject.setVALORE_ESITO(CDT_RESULT);
						singleAssertCdtId.setID(CDT_ID);
						singleAssertCdtId.setCommandsArrayNumber(iterator - 1);
						singleAssertCdtId.setPosition(i);
						cdtIdPositioningArrayList.add(singleAssertCdtId);

						if(CDT_RESULT.equals("Passed")) {
							mySingleResultObject.setESITO(1);
							mySingleResultObject.setDESCRIZIONE_ESITO("");
						}else {
							mySingleResultObject.setESITO(0);
							mySingleResultObject.setDESCRIZIONE_ESITO(CDT_RESULT);
						}

						myResultsArrayList.add(mySingleResultObject);
					}
					if((name != null && message != null) && (name.equals("Assert") && message.startsWith("Script"))) {
						String[] messageScriptIDSplit = message.split(" ");
						String idScript = messageScriptIDSplit[1];
						singleAssertScriptId.setID(idScript);
						singleAssertScriptId.setPosition(i);
						singleAssertScriptId.setCommandsArrayNumber(iterator - 1);
						scriptIdPositioningArrayList.add(singleAssertScriptId);
					}
					if((name != null && message != null) && (name.equals("Assert") && message.startsWith("IDPoste"))) {
						String[] messageSplit = message.split(" ");
						String ID_POSTE = messageSplit[1].trim();
						mySingleResultObject.setID_SCRIPT(Integer.parseInt(ID_POSTE));
					}
					if((name != null && message != null) && (name.equals("Assert") && message.startsWith("Nome Test"))) {
						String[] messageSplit = message.split("Nome Test:");
						String NOME_TEST = messageSplit[1].trim();
						LocalDateTime orarioPartenzaTest = longToDateTime(startTime);
						mySingleResultObject.setNOME_TEST(NOME_TEST);
						mySingleResultObject.setNOME_SCENARIO(NOME_TEST);
						mySingleResultObject.setDATA_ORA_INIZIO_TEST(orarioPartenzaTest);
					} 
				}
			}
			myResultsArrayList = setIdNomeVersioneScript(cdtIdPositioningArrayList, scriptIdPositioningArrayList);
			return myResultsArrayList;
		}catch(Exception e) {
			System.err.println("Si è verificato un errore nella getAllResults: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return null;
		}
	}

	private static ArrayList<InsertResultClass> setUtenteComputerDatabase(JSONObject myMetadataJsonObject, Properties myPropertiesFile){

		try {
			String emailEsecutore = myMetadataJsonObject.getString("userId");
			String[] emailEsecutoreArray = emailEsecutore.split("@");
			String NOME_UTENTE = emailEsecutoreArray[0];
			String NOME_COMPUTER = System.getProperty("user.name");
			String NOME_DATABASE_SCRIPT = myPropertiesFile.getProperty("CLOUD_NAME");

			for(int i = 0; i < myResultsArrayList.size(); i++) {
				myResultsArrayList.get(i).setNOME_UTENTE(NOME_UTENTE);
				myResultsArrayList.get(i).setNOME_COMPUTER(NOME_COMPUTER);
				myResultsArrayList.get(i).setNOME_DATABASE_SCRIPT(NOME_DATABASE_SCRIPT);
			}
			return myResultsArrayList;
		}catch(Exception e) {
			System.err.println("Si è verificato un errore nella setUtenteComputer: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return null;
		}
	}

	private static String[] verifyParsing(String hourMinSec){
		try {
			String[] elementsToCheck = hourMinSec.split(":");
			for(int i = 0; i < elementsToCheck.length; i++) {
				if(elementsToCheck[i].length() == 2) {
					continue;
				}else {
					String elementToReplace = "0" + elementsToCheck[i];
					elementsToCheck[i] = elementToReplace;
				}
			}
			return elementsToCheck;
		}catch(Exception e) {
			System.err.println("Si è verificato un errore nella verifyParsing: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return null;
		}
	}

	private static ArrayList<InsertResultClass> setDurataTest(LocalDateTime DATA_ORA_INIZIO_SCRIPT, LocalDateTime DATA_ORA_FINE_TEST) {
		Duration DURATA_TEST = Duration.between(DATA_ORA_INIZIO_SCRIPT, DATA_ORA_FINE_TEST);
		String durataTestString = DURATA_TEST.toString();
		String durataTestStringFirstParsing = durataTestString.replace("PT", "").replace("M", ":").replace("H", ":");
		String firstSplit[] = durataTestStringFirstParsing.split("\\.");
		String hourMinSec = firstSplit[0];
		String[] checkArrayContent = hourMinSec.split(":");
		String finalDuration = "";
		String[] elementsChecked = verifyParsing(hourMinSec);
		switch(checkArrayContent.length) {
		case 1:
			finalDuration = "00:" + "00:" + elementsChecked[0];
			break;
		case 2:
			finalDuration = "00:" + elementsChecked[0] + ":" + elementsChecked[1];
			break;
		case 3:
			finalDuration = elementsChecked[0] + ":" + elementsChecked[1] + ":" + elementsChecked[2];
			break;
		}
		for(int i = 0; i < myResultsArrayList.size(); i++) {
			myResultsArrayList.get(i).setDURATA_TEST(finalDuration);
		}
		return myResultsArrayList;
	}

	private static ArrayList<InsertResultClass> setDataOraInizioScript(JSONObject myResourcesJsonObject){
		try {
			long dataOraInizioScriptLong = myResourcesJsonObject.getLong("startTime");
			long dataOraFineScriptLong = myResourcesJsonObject.getLong("endTime");
			LocalDateTime DATA_ORA_INIZIO_SCRIPT = longToDateTime(dataOraInizioScriptLong);
			LocalDateTime DATA_ORA_FINE_TEST = longToDateTime(dataOraFineScriptLong);

			for(int i = 0; i < myResultsArrayList.size(); i++) {
				myResultsArrayList.get(i).setDATA_ORA_INIZIO_SCRIPT(DATA_ORA_INIZIO_SCRIPT);
				//myResultsArrayList.get(i).setDATA_ORA_INIZIO_TEST(DATA_ORA_INIZIO_SCRIPT);
				myResultsArrayList.get(i).setDATA_ORA_FINE_TEST(DATA_ORA_FINE_TEST);
				myResultsArrayList = setDurataTest(DATA_ORA_INIZIO_SCRIPT, DATA_ORA_FINE_TEST);
			}
			return myResultsArrayList;
		}catch(Exception e) {
			System.err.println("Si è verificato un errore nella setDataOraInizioScript: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return null;
		}
	}

	private static ArrayList<InsertResultClass> setIdSctm(Properties myPropertiesFile){
		try {
			int ID_ISTANZA_EP_SCTM = Integer.parseInt(myPropertiesFile.getProperty("ID_ISTANZA_EP_SCTM"));
			int ID_PROGETTO_SCTM = Integer.parseInt(myPropertiesFile.getProperty("ID_PROGETTO_SCTM"));
			for(int i = 0; i < myResultsArrayList.size(); i++) {
				myResultsArrayList.get(i).setID_ISTANZA_EP_SCTM(ID_ISTANZA_EP_SCTM);
				myResultsArrayList.get(i).setID_PROGETTO_SCTM(ID_PROGETTO_SCTM);
			}
			return myResultsArrayList;
		}catch(Exception e) {
			System.err.println("Si è verificato un errore nella setIdSctm: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return null;
		}
	}

	public static ArrayList<InsertResultClass> analyzeReport(String reportString, Properties myPropertiesFile) {

		try {
			JSONObject myFullReportJsonObject = new JSONObject(reportString);
			JSONObject myMetadataJsonObject = myFullReportJsonObject.getJSONObject("metadata");
			JSONArray myResourcesJsonArray = myFullReportJsonObject.getJSONArray("resources");
			JSONObject myResourcesJsonObject = myResourcesJsonArray.getJSONObject(0);

			myResultsArrayList = getAllResults(myResourcesJsonArray);
			myResultsArrayList = setUtenteComputerDatabase(myMetadataJsonObject, myPropertiesFile);
			myResultsArrayList = setDataOraInizioScript(myResourcesJsonObject);
			myResultsArrayList = setIdSctm(myPropertiesFile);
			return myResultsArrayList;
		}catch(Exception e) {
			System.err.println("Si è verificato un errore nella analyzeReport: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return null;
		}
	}

	public static StringBuilder jsonToHtml(Object obj) {
		StringBuilder html = new StringBuilder( );

		try {
			if (obj instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject)obj;
				String[] keys = JSONObject.getNames( jsonObject );
				html.append("<div class=\"Report HTML\">");

				if (keys.length > 0) {
					for (String key : keys) {
						// print the key and open a DIV
						html.append("<div><span class=\"json_key\">")
						.append(key).append("</span> : ");

						Object val = jsonObject.get(key);
						// recursive call
						html.append( jsonToHtml( val ) );
						// close the div
						html.append("</div>");
					}
				}

				html.append("</div>");

			} else if (obj instanceof JSONArray) {
				JSONArray array = (JSONArray)obj;
				for (int i=0; i < array.length( ); i++) {
					// recursive call
					html.append(jsonToHtml(array.get(i)));                    
				}
			} else {
				// print the value
				html.append(obj);
			}                
		} catch (JSONException e) { return null; }

		return html;
	}

	private static LocalDateTime longToDateTime(long unixTime) {
		LocalDateTime myLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(unixTime), TimeZone.getDefault().toZoneId());
		return myLocalDateTime;
	}

}
