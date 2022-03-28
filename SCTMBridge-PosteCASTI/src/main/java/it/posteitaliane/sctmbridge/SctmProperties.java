package it.posteitaliane.sctmbridge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SctmProperties {
	
	public static Properties createProperties(String PROPERTIES_PATH, String PROPERTIES_NAME) {
		try {
			File myFile = new File(PROPERTIES_PATH + File.separator + PROPERTIES_NAME);
			FileReader myReader = new FileReader(myFile);
			Properties myPropertiesFile = new Properties();
			myPropertiesFile.load(myReader);
			return myPropertiesFile;
		}catch(Exception e) {
			System.err.println("Si è verificato un errore nella createProperties: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return null;
		}
	}
	
	public static HashMap<String, String> readExcel(String PROPERTIES_PATH, String PROPERTIES_NAME) {
		try {
			FileInputStream myFileInputStream = new FileInputStream(new File(PROPERTIES_PATH + File.separator + PROPERTIES_NAME));
			XSSFWorkbook myWorkbook = new XSSFWorkbook(myFileInputStream);
			XSSFSheet mySheet = myWorkbook.getSheetAt(0);
			HashMap<String, String> myHashMapProperties = new HashMap<String, String>();
			ArrayList<String> mySingleRowArrayList = new ArrayList<String>();
			for(int iterator = 0; iterator <= 1; iterator++) {
				for(Row myRow : mySheet) {
					String mySingleRow = myRow.getCell(iterator).toString();
					if(iterator == 0) {
						mySingleRowArrayList.add(mySingleRow);
						myHashMapProperties.put(mySingleRow, null);
					}else {
						myHashMapProperties.put(mySingleRowArrayList.get(myRow.getRowNum()), mySingleRow);
					}
				}
			}
			myHashMapProperties.remove("KEY");
			return myHashMapProperties;
		}catch(Exception e) {
			System.out.println("Si è verificato un errore nella readExcel: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
			return null;
		}
	}

}
