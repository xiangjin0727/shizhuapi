package com.hz.core.util;

import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomNum {

	public static String getStringNum(int length){
		 Random rnd = new Random();  
		 StringBuilder sb = new StringBuilder(length);  
		 for(int i=0; i < length; i++)  {
		    sb.append((char)('0' + rnd.nextInt(10))); 
		 }
		 String code = sb.toString(); 
		 return code;
	}
	
	public static String getSeconds(Date dateStart,Date dateEnd){
		  String rtn ="0";
		  if(dateStart!=null&&dateEnd!=null){
			  long scd= (dateEnd.getTime()-dateStart.getTime())/1000;
			  rtn= Objects.toString(scd,"0");
		  }
		 return rtn;
	}
	 public static boolean isMobile(String str) {   
	        Pattern p = null;  
	        Matcher m = null;  
	        boolean b = false;   
	        p = Pattern.compile("^[1][2,3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号  
	        m = p.matcher(str);  
	        b = m.matches();   
	        return b;  
	 }
	 
/**  
	 
      * 验证邮箱地址是否正确  
 
      * @param email  
 
      * @return  
 
      */  
 
     public static boolean isEmail(String email){  
 
      boolean flag = false;  
 
      try{  
 
       String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
 
       Pattern regex = Pattern.compile(check);  
 
       Matcher matcher = regex.matcher(email);  
 
       flag = matcher.matches();  
 
      }catch(Exception e){  
 
       flag = false;  
 
      }  
      return flag;  
 
     }  

}
