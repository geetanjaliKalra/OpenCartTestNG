package com.qa.opencart.utils;

import java.util.Random;

public class StringUtils {
		
		
		public static String getEmailId() {
			Random rand=new Random();
			int randomNo= rand.nextInt();
			String emailid= "auto"+randomNo +"@gmail.com";
			return emailid;
		}
}
