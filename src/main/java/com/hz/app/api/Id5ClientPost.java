package com.hz.app.api;


import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hz.app.api.id5.service.QueryValidatorServices;
import com.hz.app.api.id5.service.QueryValidatorServicesProxy;
import com.hz.app.api.util.Des2;
import com.hz.core.util.ClassLoaderUtil;


public class Id5ClientPost {

	//public String checkIdcard(String param) throws Exception {

		public boolean checkIdcard(String param) throws Exception{

			Properties prop = ClassLoaderUtil.getProperties("app_system.properties");
			String debug_type = prop.getProperty("debug_type");
			if("true".equals(debug_type))
				return true;

		//获得WebServices的代理对象
		QueryValidatorServicesProxy proxy = new QueryValidatorServicesProxy();
		proxy.setEndpoint("http://gboss.id5.cn/services/QueryValidatorServices");
		QueryValidatorServices service =  proxy.getQueryValidatorServices();
		//对调用的参数进行加密
		String key = "12345678";
//		String userName = Des2.encode(key, "hzhphService");
//		String password = Des2.encode(key, "hzhphService_13iIdF0~");
		String userName = Des2.encode(key, "hzhltService");
		String password = Des2.encode(key, "hzhltService_1Tovd!Kh");
		//System.setProperty("javax.net.ssl.trustStore", "keystore");
		//查询返回结果
		String resultXML = "";

		/*------身份信息核查比对-------*/
		String datasource = Des2.encode(key,"1A020201");

		//单条
		resultXML = service.querySingle(userName, password, datasource, Des2.encode(key, param));

		//批量
//		String params = "王茜,150202198302101248;" +
//						"吴晨晨,36252519821201061x;" +
//						"王鹏,110108197412255477";
//		resultXML = service.queryBatch(userName, password, datasource, Des2.encode(key, params));


		//解密
		resultXML = Des2.decodeValue(key, resultXML);

		/*resultXML ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
		+"<data>"+"<message>"
		+"<status>0</status>"
		+"<value>处理成功</value>"
		+"</message>"
		+"<policeCheckInfos>"
		+"<policeCheckInfo name=\"宋雪\" id=\"210219197101011246\">"
		+"<message>"
		+"<status>0</status>"
		+"<value>查询成功</value>"
		+"</message>"
		+"<name desc=\"姓名\">宋雪</name>"
		+"<identitycard desc=\"身份证号\">210219197101011246</identitycard>"
		+"<compStatus desc=\"比对状态\">3</compStatus>"
		+"<compResult desc=\"比对结果\">一致</compResult>"
		+"<no desc=\"唯一标识\" />"
		+"</policeCheckInfo>"
		+"</policeCheckInfos>"+ "</data>";*/

		return isExistNote( resultXML, "compStatus");


	}

	public static boolean isExistNote(String str,String nodeString ) throws UnsupportedEncodingException, DocumentException {

		boolean flag=false;
		if (StringUtils.isBlank(str)) {
			return false;
		}
		try {
			byte[] byteArray = str.getBytes("UTF-8");
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(byteArrayInputStream);
			List list = doc.selectNodes("//"+nodeString);
			//   "//"的意思表示在任意层级下发现nodeString，不加"//"只会在根节点"nodeString"
			if(list.size()!= 0){
				Element root = (Element)list.get(0);
				String compStatus= root.getText();
				if("3".equals(compStatus))
				 flag=true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  catch (DocumentException e) {
			e.printStackTrace();
		}
		return flag;
	}

	
}
