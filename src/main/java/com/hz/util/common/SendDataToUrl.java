package  com.hz.util.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager; 



public class SendDataToUrl{
	
	/**
	 * 把数据推送到地址中，得到返回值
	 * @param url   地址
	 * @param data  数据值
	 * @param methodType  执行方法  GET or POST
	 * @return
	 */
	 public  static  String getDataToUrl(String url,String data,String methodType){
			String ret="";
//			System.out.println("======调用开始=======");
//			System.setProperty( "javax.net.ssl.trustStore",   "d:/wsriakey"); 
//			System.setProperty( "javax.net.ssl.trustStorePassword",   "hzcf123");
			try {	
				URL urlObject = new URL(url);  
				HttpURLConnection httpConn = (HttpURLConnection) urlObject.openConnection();
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);
				httpConn.setRequestMethod(methodType);
				DataOutputStream out = new DataOutputStream(httpConn.getOutputStream());
				out.write(data.getBytes("UTF-8"));
				out.flush();
				out.close();			
				InputStream stream = httpConn.getInputStream();
				DataInputStream in = new DataInputStream(stream);
				byte[] bin = null;
				byte[] inc = new byte[1024];
				int datelength = 0;
				int insize = 0;
				while ((insize = in.read(inc)) != -1){
					int oldlength = datelength;
					datelength += insize;
					byte[] oldbin = new byte[datelength];
					for (int i = 0; i < oldlength; i++)
						oldbin[i] = bin[i];
					for (int i = oldlength; i < datelength; i++)
						oldbin[i] = inc[i - oldlength];
					bin = oldbin;
				}
				 ret = new String(bin,"UTF-8");
				in.close();
				stream.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return ret;
		}
	 
	 
	 public static String httpsRequest(String requestUrl, String requestMethod, String outputStr){  
	        String ret = null;    
	        StringBuffer buffer = new StringBuffer();    
	        try {    
	            // 创建SSLContext对象，并使用我们指定的信任管理器初始化    
	            TrustManager[] tm = { new TrustAnyTrustManager() };    
	            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");    
	            sslContext.init(null, tm, new java.security.SecureRandom());    
	            // 从上述SSLContext对象中得到SSLSocketFactory对象    
	            SSLSocketFactory ssf = sslContext.getSocketFactory();    
	    
	            URL url = new URL(requestUrl);    
	            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();    
	            httpUrlConn.setSSLSocketFactory(ssf);    
	    
	            httpUrlConn.setDoOutput(true);    
	            httpUrlConn.setDoInput(true);    
	            httpUrlConn.setUseCaches(false);    
	            // 设置请求方式（GET/POST）    
	            httpUrlConn.setRequestMethod(requestMethod);    
	            httpUrlConn.connect();    
	    
	            // 当有数据需要提交时    
	            if (null != outputStr) {    
	                OutputStream outputStream = httpUrlConn.getOutputStream();    
	                // 注意编码格式，防止中文乱码    
	                outputStream.write(outputStr.getBytes("UTF-8"));    
	                outputStream.close();    
	            }    
	    
	            // 将返回的输入流转换成字符串    
	            InputStream inputStream = httpUrlConn.getInputStream();   
	            String str = null;    
				DataInputStream in = new DataInputStream(inputStream);
				byte[] bin = null;
				byte[] inc = new byte[1024];
				int datelength = 0;
				int insize = 0;
				while ((insize = in.read(inc)) != -1){
					int oldlength = datelength;
					datelength += insize;
					byte[] oldbin = new byte[datelength];
					for (int i = 0; i < oldlength; i++)
						oldbin[i] = bin[i];
					for (int i = oldlength; i < datelength; i++)
						oldbin[i] = inc[i - oldlength];
					bin = oldbin;
				}
				 ret = new String(bin,"UTF-8");
				in.close();     
	            // 释放资源    
	            inputStream.close();    
	            inputStream = null;    
	            httpUrlConn.disconnect();     
	        }catch (Exception e) {    
	            e.printStackTrace();    
	        }    
	        return ret;    
	    }  
	 
	 
	 
	 
	 
	 public static String doHttpsGetJson(String Url){
	        String message = "";
	        try{
	            System.out.println("doHttpsGetJson");//TODO:dd
	            URL urlGet = new URL(Url);
	            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection(); 
	            http.setRequestMethod("GET");                
	            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
	            http.setDoOutput(true);  
	            http.setDoInput(true);
	            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
	            System.setProperty("sun.net.client.defaultReadTimeout", "30000");        
	            http.connect();
	            InputStream is =http.getInputStream();
	            int size =is.available();
	            byte[] jsonBytes =new byte[size];
	            is.read(jsonBytes);
	            message=new String(jsonBytes,"UTF-8");
	        }catch (MalformedURLException e){
	              e.printStackTrace();
	        }catch (IOException e){
	              e.printStackTrace();
	        } 
	        return message;
	    }
	
}