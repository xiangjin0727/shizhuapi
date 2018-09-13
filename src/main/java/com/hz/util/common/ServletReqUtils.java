package com.hz.util.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletReqUtils {
	private static Logger logger = LoggerFactory.getLogger(ServletReqUtils.class);
	/**
     * 接收数据流
     * @param request
     * @return
     * @throws Exception
     */
    public static  String getReaMsg(HttpServletRequest request){
    	
    	String reqMsg = null;
    	  try{
    	        BufferedInputStream  br = null;
				br = new BufferedInputStream(request.getInputStream());
				byte[] reqData = new byte[1024];
				ByteArrayOutputStream  bos = new ByteArrayOutputStream();
				while(br.read(reqData)>0){
					bos.write(reqData);
				}
				reqMsg = new String(bos.toByteArray(),"UTF-8");
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
           return  reqMsg;
        }
    
    
	/**
     * 接收数据流
     * @param request
     * @return
     * @throws Exception
     * @author XJin
     */
    public static String getRequestStr(HttpServletRequest request) throws UnsupportedEncodingException{  
        byte buffer[]=getRequestBytes(request);  
        String charEncoding=request.getCharacterEncoding();  
        if(charEncoding==null){  
            charEncoding="UTF-8";  
        }  
        return new String(buffer,charEncoding);  
    }  
    public static byte[] getRequestBytes(HttpServletRequest request){  
        int contentLength = request.getContentLength();  
        byte buffer[] = new byte[contentLength];  
        for (int i = 0; i < contentLength;) {  
            try {  
  
                int readlen = request.getInputStream().read(buffer, i,  
                        contentLength - i);  
                if (readlen == -1) {  
                    break;  
                }  
                i += readlen;  
            } catch (IOException ioexception) {  
            	logger.debug("接收数据流EXCEPTION",ioexception);
            } 
        }  
        return buffer;  
    }  

}
