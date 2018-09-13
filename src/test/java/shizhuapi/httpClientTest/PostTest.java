package shizhuapi.httpClientTest;


import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.xml.DOMConfigurator;

import com.hz.util.common.httpClient.HttpProxyClientPost;

public class PostTest {
	public static void main(String[] args) throws Exception {
		setup();
		
		Map<String, Object> retMap=new HttpProxyClientPost().post(s0007());
		
		System.out.println(retMap);
	}
	
	private static void setup() {
		ClassLoader c = Thread.currentThread().getContextClassLoader();
		URL url = c.getResource("log4j-config.xml");
		DOMConfigurator.configure(url);
	}
	
	private static Map<String, Object> s0001() {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", "S0001");
		
			Map<String,Object> data=new HashMap<String, Object>();
			data.put("mobile", "13344444100");
		
		map.put("data", data);
		return map;
	}
	
	private static Map<String, Object> s0002() {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", "S0002");
		
			Map<String,Object> data=new HashMap<String, Object>();
			//data.put("mobile", "value1");
		
		map.put("data", data);
		return map;
	}
	
	private static Map<String, Object> s0003() {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", "S0003");
		
			Map<String,Object> data=new HashMap<String, Object>();
			data.put("mobile", "13344444100");
			data.put("productId", "8759a5d995b94bb89ab6aa008f3ef293");
		
		map.put("data", data);
		return map;
	}
	
	private static Map<String, Object> s0004() {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", "S0004");
		
			Map<String,Object> data=new HashMap<String, Object>();
			data.put("mobile", "13344444100");
		
		map.put("data", data);
		return map;
	}
	
	private static Map<String, Object> s0005() {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", "S0005");
		
			Map<String,Object> data=new HashMap<String, Object>();
			data.put("page", "1");
			data.put("pageSize", "1");
			data.put("CloseTime", "3");
			data.put("productId", "8759a5d995b94bb89ab6aa008f3ef293");
		
		map.put("data", data);
		return map;
	}
	
	private static Map<String, Object> s0006() {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", "S0006");
		
			Map<String,Object> data=new HashMap<String, Object>();
			data.put("page", "1");
			data.put("pageSize", "1");
			data.put("productId", "8759a5d995b94bb89ab6aa008f3ef293");
		
		map.put("data", data);
		return map;
	}
	
	private static Map<String, Object> s0007() {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", "S0003");
		
			Map<String,Object> data=new HashMap<String, Object>();
			data.put("mobile", "13344444100");
			data.put("productId", "8759a5d995b94bb89ab6aa008f3ef293");
		
		map.put("data", data);
		return map;
	}
}
