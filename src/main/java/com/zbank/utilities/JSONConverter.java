package com.zbank.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;


import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONConverter {
	
	
	public static String getJson(Object object) {
		 ObjectMapper objectMapper = new ObjectMapper();
		 String json = "";
	     try {
			json = objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			
		}
	     return json;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> getMapFromJson(HttpServletRequest request) {
		 BufferedReader reader;
		 HashMap<String, Object> json = null;
			try {
				reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			

		        // Read the request body and convert it to a string
		        StringBuilder stringBuilder = new StringBuilder();
		        String line;
		        while ((line = reader.readLine()) != null) {
		            stringBuilder.append(line);
		        }
		        String requestBody = stringBuilder.toString();
		        JSONParser parser = new JSONParser(requestBody);  
		        json = (HashMap<String, Object>) parser.parse(); 
		      
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return json;
	}
	
}
