package perfecto;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class PerfectoLabUtils {

	private static final String HTTPS = "https://";
	private static final String MEDIA_REPOSITORY = "/services/repositories/media/";
	private static final String UPLOAD_OPERATION = "operation=upload&overwrite=true";
	private static final String UTF_8 = "UTF-8";
	private static final String TESTINGCLOUD_HOST = "mobilecloud.perfectomobile.com";
	private static final String UPLOAD_OPERATION_TESTINGCLOUD = "cloud=testingcloud-perfectomobile-com&operation=upload&overwrite=true";

	/**
	 * Uploads a file to the media repository.
	 * Example:
	 * uploadMedia("demo.perfectomobile.com", "john@perfectomobile.com", "123456", "C:\\test\\ApiDemos.apk", "PRIVATE:apps/ApiDemos.apk");
	 */
	public static void uploadMedia(String host, String securityToken, String path, String repositoryKey) throws IOException {
		File file = new File(path);
		byte[] content = readFile(file);
		uploadMedia(host + ".perfectomobile.com", securityToken, content, repositoryKey);
	}

	/**
	 * Uploads a file to the media repository.
	 * Example:
	 * URL url = new URL("http://file.appsapk.com/wp-content/uploads/downloads/Sudoku%20Free.apk");
	 * uploadMedia("demo.perfectomobile.com", "john@perfectomobile.com", "123456", url, "PRIVATE:apps/ApiDemos.apk");
	 */
	public static void uploadMedia(String host, String securityToken, URL mediaURL, String repositoryKey) throws IOException {
		byte[] content = readURL(mediaURL);
		uploadMedia(host, securityToken, content, repositoryKey);
	}

	/**
	 * Uploads content to the media repository.
	 * Example:
	 * uploadMedia("demo.perfectomobile.com", "john@perfectomobile.com", "123456", content, "PRIVATE:apps/ApiDemos.apk");
	 */
	public static void uploadMedia(String host, String securityToken, byte[] content, String repositoryKey) throws UnsupportedEncodingException, MalformedURLException, IOException {
		if (content != null) {
			String encodedSecurityToken = URLEncoder.encode(securityToken, "UTF-8");
			//			String encodedPassword = URLEncoder.encode(password, "UTF-8");
			String urlStr = HTTPS + host + MEDIA_REPOSITORY + repositoryKey + "?" + UPLOAD_OPERATION + "&securityToken=" + encodedSecurityToken;
			URL url = new URL(urlStr);

			sendRequest(content, url);
		}
	}

	/**
	 * Uploads a file to the media repository on TestingCloud.
	 * Example:
	 * uploadMedia("<security_token_string>", "C:\\test\\ApiDemos.apk", "PRIVATE:apps/ApiDemos.apk");
	 */
	public static void uploadMediaTestingCloud(String securityToken, String path, String repositoryKey) throws IOException {
		File file = new File(path);
		byte[] content = readFile(file);
		uploadMediaTestingCloud(securityToken, content, repositoryKey);
	}

	/**
	 * Uploads content to the media repository on TestingCloud.
	 * Example:
	 * uploadMedia("<security_token_string>", content, "PRIVATE:apps/ApiDemos.apk");
	 */
	public static void uploadMediaTestingCloud(String securityToken, byte[] content, String repositoryKey) throws UnsupportedEncodingException, MalformedURLException, IOException {
		if (content != null) {
			String urlStr = HTTPS + TESTINGCLOUD_HOST + MEDIA_REPOSITORY + repositoryKey + "?" + UPLOAD_OPERATION_TESTINGCLOUD + "&securityToken=" + securityToken;
			URL url = new URL(urlStr);

			sendRequest(content, url);
		}
	}


	private static void sendRequest(byte[] content, URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/octet-stream");
		connection.connect();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		outStream.write(content);
		outStream.writeTo(connection.getOutputStream());
		outStream.close();
		int code = connection.getResponseCode();
		if (code > HttpURLConnection.HTTP_OK) {
			handleError(connection);
		}
	}

	private static void handleError(HttpURLConnection connection) throws IOException {
		String msg = "Failed to upload media.";
		InputStream errorStream = connection.getErrorStream();
		if (errorStream != null) {
			InputStreamReader inputStreamReader = new InputStreamReader(errorStream, UTF_8);
			BufferedReader bufferReader = new BufferedReader(inputStreamReader);
			try {
				StringBuilder builder = new StringBuilder();
				String outputString;
				while ((outputString = bufferReader.readLine()) != null) {
					if (builder.length() != 0) {
						builder.append("\n");
					}
					builder.append(outputString);
				}
				String response = builder.toString();
				msg += "Response: " + response;
			}
			finally {
				bufferReader.close();
			}
		}
		throw new RuntimeException(msg);
	}

	private static byte[] readFile(File path) throws FileNotFoundException, IOException {
		int length = (int)path.length();
		byte[] content = new byte[length];
		InputStream inStream = new FileInputStream(path);
		try {
			inStream.read(content);
		}
		finally {
			inStream.close();
		}
		return content;
	}

	private static byte[] readURL(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setDoOutput(true);
		int code = connection.getResponseCode();
		if (code > HttpURLConnection.HTTP_OK) {
			handleError(connection);
		}
		InputStream stream = connection.getInputStream();

		if (stream == null) {
			throw new RuntimeException("Failed to get content from url " + url + " - no response stream");
		}
		byte[] content = read(stream);
		return content;
	}

	private static byte[] read(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			byte[] buffer = new byte[1024];
			int nBytes = 0;
			while ((nBytes = input.read(buffer)) > 0) {
				output.write(buffer, 0, nBytes);
			}
			byte[] result = output.toByteArray();
			return result;
		} finally {
			try{
				input.close();
			} catch (IOException e){

			}
		}
	}

}