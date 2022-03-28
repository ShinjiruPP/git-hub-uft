package proxy;

import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ProxyConfiguration {

	private static String proxyHost;
	private static String proxyPort; //80 if it isn't provided
	private static String nonProxyHosts;
	private static String protocolType;
	public static boolean flagProxy;

	//Set method used to read the flag Proxy
	public static boolean isProxyEnabled(Properties myPropertiesFile) {
		flagProxy = Boolean.parseBoolean(myPropertiesFile.getProperty("ENABLE_PROXY").toLowerCase());
		return flagProxy;
	}

	public static String getProxyHost() {
		return proxyHost;
	}

	//Set method used to read the Proxy host from properties
	public static void setProxyHost(Properties myPropertiesFile) {
		ProxyConfiguration.proxyHost = myPropertiesFile.getProperty("PROXY_HOST");
	}

	public static String getProxyPort() {
		return proxyPort;
	}

	//Set method used to read the Proxy port from properties
	public static void setProxyPort(Properties myPropertiesFile) {
		ProxyConfiguration.proxyPort = myPropertiesFile.getProperty("PROXY_PORT");
	}

	public static String getNonProxyHosts() {
		return nonProxyHosts.replace(",", "|");
	}

	//Set method used to read the exception from properties
	public static void setNonProxyHosts(Properties myPropertiesFile) {
		ProxyConfiguration.nonProxyHosts = myPropertiesFile.getProperty("PROXY_EXCEPTION");
	}
	
	public static String getProtocolType() {
		return protocolType;
	}

	//Set method used to read the exception from properties
	public static void setProtocolType(Properties myPropertiesFile) {
		ProxyConfiguration.protocolType = myPropertiesFile.getProperty("PROTOCOL_TYPE");
	}
	
	//Method used to set the Proxy settings on system
	public static void setProxyConfiguration(Properties myPropertiesFile) {

		try {
			ProxyConfiguration.setProtocolType(myPropertiesFile);
			ProxyConfiguration.setProxyHost(myPropertiesFile);
			ProxyConfiguration.setProxyPort(myPropertiesFile);
			ProxyConfiguration.setNonProxyHosts(myPropertiesFile);
			
			System.setProperty("java.net.useSystemProxies", "true");
			System.setProperty(ProxyConfiguration.getProtocolType() +".proxyHost", ProxyConfiguration.getProxyHost());
			System.setProperty(ProxyConfiguration.getProtocolType() +".proxyPort", ProxyConfiguration.getProxyPort());

			boolean exceptions = ProxyConfiguration.getNonProxyHosts().isEmpty();
			if(!exceptions) {
				System.setProperty(ProxyConfiguration.getProtocolType() +".nonProxyHosts", ProxyConfiguration.getNonProxyHosts());
			}
		} catch (Exception e) {
			System.err.println("Si è verificato un errore nella setProxyConfiguration: ");
			String exc = ExceptionUtils.getStackTrace(e);
			System.err.println(exc);
			System.exit(-1);
		}
	}
}
