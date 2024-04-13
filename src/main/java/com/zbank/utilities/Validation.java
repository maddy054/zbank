package com.zbank.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zbank.enums.ErrorCode;
import com.zbank.exceptions.BankingException;



public class Validation {
	
	public static void isvalidPassword(String password) throws BankingException {
		
		Pattern pattern =getPattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{8,16}$");
		Matcher match = getMatcher(pattern, password);
		if( !match.matches()) {
			throw new BankingException(ErrorCode.INVALID_PATTERN);
		}
	}
	public static boolean isValidNumber(String number) {
		Pattern pattern = getPattern("^[7-9]\\d{9}$");
		Matcher match = getMatcher(pattern,number);
		return match.matches();
	}
	public static boolean checkLength(String values,int length) {
	   Pattern pattern = Pattern.compile("^.{1,"+length+"}$");
	   Matcher match = pattern.matcher(values);
	   return match.matches();
	 }
	  
	private static Pattern getPattern(String regEx) {
		return Pattern.compile(regEx);
	}
	private static Matcher getMatcher(Pattern pattern, String match) {
		return pattern.matcher(match);
	}
	
	  public List<String> getHtmlTag(String htmlString){
		  List<String> tagList = new ArrayList<String>();
		  Pattern pattern = Pattern.compile("<[^>]+>");
		  Matcher matcher = pattern.matcher(htmlString);
		  while(matcher.find()) {
			  tagList.add(matcher.group());
		  }
		  return tagList;
	  }
	  
		public StringBuilder deleteString(StringBuilder myStringBuilder, String name) {
			
			int index = myStringBuilder.indexOf(name);
			int length = name.length();
			return myStringBuilder.delete(index, index+length-1);
		}
		
		public void deleteTag(String input) {
			List<String> tags = getHtmlTag(input);
			StringBuilder strBuilder = new StringBuilder(input);
			if(!tags.isEmpty()) {
				for(String tag:tags) {
					deleteString(strBuilder, tag);
				}
				
			}
		}
}
