package com.sentiments.splunk;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.splunk.HttpService;
import com.splunk.Index;
import com.splunk.IndexCollection;
import com.splunk.SSLSecurityProtocol;
import com.splunk.Service;
import com.splunk.ServiceArgs;

public class IndexProcessor {
	private static Service service = null;

	private Service makeConnection() {
		if (service == null) {
			Properties prop = new Properties();
			InputStream input = null;

			try {
				ClassLoader classLoader = getClass().getClassLoader();
				File file = new File(classLoader.getResource("splunkConfig.properties").getFile());
				input = new FileInputStream(file);
				// load a properties file
				prop.load(input);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
			ServiceArgs loginArgs = new ServiceArgs();
			loginArgs.setUsername(prop.getProperty("username"));
			loginArgs.setPassword(prop.getProperty("password"));
			loginArgs.setPort(Integer.valueOf(prop.getProperty("port")));
			loginArgs.setHost(prop.getProperty("host"));
			// Connect to Splunk Enterprise
			service = Service.connect(loginArgs);
			return service;
		}
		return service;

	}

	public boolean createIndex(String indexName) {
		Service service = makeConnection();
		IndexCollection myIndexes = service.getIndexes();
		if (myIndexes.get(indexName) == null) {
			// Create a new index
			System.out.println(indexName + " Index creation Successful.");
			myIndexes.create(indexName);
			return true;
		}
		return false;

	}

	public void uploadFile(File file, String indexName) {
		Service service = makeConnection();
		Index myIndex = service.getIndexes().get(indexName);
		// Specify a file and upload it
		String uploadme = file.getAbsolutePath();
		myIndex.upload(uploadme);
	}
}
