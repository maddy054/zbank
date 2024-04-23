package com.zbank.tester;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;


public class ApiTester {
	public static void main(String... args) {
		 Scanner scanner = null;
		  HttpsURLConnection connection = null;
		try {
		//System.setProperty("javax.net.ssl.trustStore", "/home/madhavan-pt7281/mykeystore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "Maddy@432");
		
            URL apiUrl = new URL("https://localhost:8443/zbank/api/branch?apiKey=KN7iRWZ1wgqZWOQvz9pWMM5wWEGYfnNk&branchId=100");
            
            connection = (HttpsURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setHostnameVerifier(new HostnameVerifier() {
				
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
           
           
            if (responseCode == HttpURLConnection.HTTP_OK) {
            	
				scanner = new Scanner(connection.getInputStream());
				
            } else {
            	scanner = new Scanner(connection.getInputStream());
            }
            StringBuilder sb = new StringBuilder();
            
            while (scanner.hasNext()) {
				sb.append(scanner.next());
			}
			System.out.println(sb);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	
        	scanner.close();        
            connection.disconnect();
        }
	}

}
