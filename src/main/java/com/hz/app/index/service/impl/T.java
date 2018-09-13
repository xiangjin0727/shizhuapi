package com.hz.app.index.service.impl;

import org.springframework.stereotype.Service;

import com.hz.app.index.service.BaseService;
import com.hz.core.util.UUIDUtil;
import com.hz.util.common.DbUtils;

@Service
public class T extends BaseService {

	private static String sql_zj ="select "
			+ "`资源类型`,`公司名称`,`公司简称`,`城市`,`区域`,`详细地址`,`姓名`,`电话`,`微信号`,`项目名称`,`青睐资产`,`资金成本区间`,`资金类型`,`资金规模`,`信息有效期`,`其它需求`"
			+ "  from  z_zj";
	
	private  static String sql_zc ="select aabb,b,mm,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s  from  z_zc";
	
	private  static String sql_other ="select aabbcc,`公司名称`,`公司简称`,`城市`,`区域`,`详细地址`,`姓名`,`电话`,`微信号`,`项目名称`,`信息有效期`,`其它需求`"
			+ "  from  z_other";
	
	public static void insertZj() {
		// TODO Auto-generated method stub
	  try {
		
		String[][]  comFull = DbUtils.getResultSetArray(jdbc,sql_zj );
		System.out.println(sql_zj);

		for (int z = 0; z < comFull.length; z++) {
			
		String a = comFull[z][0];
		String b = comFull[z][1];
		String c = comFull[z][2];
		String d = comFull[z][3];
		String e = comFull[z][4];
		String f = comFull[z][5];
		String g = comFull[z][6];
		String h = comFull[z][7];
		String i = comFull[z][8];
		String j = comFull[z][9];
		String k = comFull[z][10];
		String l = comFull[z][11];
		String m = comFull[z][12];
		String n = comFull[z][13];
		String o = comFull[z][14];
		String p = comFull[z][15];
		String jzid = "zj"+UUIDUtil.genUUIDString();
		String gsid = "co"+UUIDUtil.genUUIDString();
		String sql1 = "insert  into  t_zjzc_companyinfo(userid,id,`firmFull`,`firm`,`area`,`address`,name,phone,wechat) "
				+ "values('8404481d971c46658d9887560d1a4cc1','"+gsid+"','"+b+"','"+c+"','"+d+e+"','"+f+"','"+g+"','"+h+"','"+i+"')";

		
		String sql2 = "insert  into  t_zjzc_zjinfo(userId,`id`,`name`,`favorite`,`moneyLow`,`moneyHigh`,`type`,`scale`,`validity`,`remark`,comId,comname) "
				+ "values('8404481d971c46658d9887560d1a4cc1','"+jzid+"','"+j+"','"+k+"','"+l+"','"+l+"','"+m+"','"+n+"','"+o+"','"+p+"','"+gsid+"','"+b+"')";
		System.out.println("===11111===>"+sql1);
		DbUtils.execute(jdbc, sql1);
		
		
		
		  System.out.println("===22222===>"+sql2);
		  DbUtils.execute(jdbc, sql2);
		}
	  } catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		insertZj();
		insertZc();
		insertOther();
	}
	
	public static void insertZc() {
		// TODO Auto-generated method stub
	  try {
		  System.out.println(sql_zc);
		String[][]  comFull = DbUtils.getResultSetArray(jdbc,sql_zc );
		

		for (int z = 0; z < comFull.length; z++) {
			
		String a = comFull[z][0];
		String b = comFull[z][1];
		String c = comFull[z][2];
		String d = comFull[z][3];
		String e = comFull[z][4];
		String f = comFull[z][5];
		String g = comFull[z][6];
		String h = comFull[z][7];
		String i = comFull[z][8];
		String j = comFull[z][9];
		String k = comFull[z][10];
		String l = comFull[z][11];
		String m = comFull[z][12];
		String n = comFull[z][13];
		String o = comFull[z][14];
		String p = comFull[z][15];
		String q = comFull[z][16];
		String r = comFull[z][17];
		String s = comFull[z][18];
		String jcid = "zc"+UUIDUtil.genUUIDString();
		String gsid = "co"+UUIDUtil.genUUIDString();
		
		String sql1 = "insert  into  t_zjzc_companyinfo(userid,id,`firmFull`,`firm`,`area`,`address`,name,phone,wechat) "
				+ "values('8404481d971c46658d9887560d1a4cc1','"+gsid+"','"+b+"','"+c+"','"+d+e+"','"+f+"','"+g+"','"+h+"','"+i+"')";
		String sql2 = "insert  into  t_zjzc_zcinfo(userId,id,`name`,`projectName`,`moneyLow`,`moneyHigh`,`type`,`scale`,`validity`,`remark`,`perTime`,`perMoney`,`monthMoney`,`dateStart`,comId,comName) "
				+ "values('8404481d971c46658d9887560d1a4cc1','"+jcid+"','"+j+"','"+k+"','"+l+"','"+l+"','"+m+"','"+q+"','"+r+"','"+s+"','6个月','"+n+"','"+o+"','"+p+"','"+gsid+"','"+b+"')";
		System.out.println("===11111===>"+sql1);
		DbUtils.execute(jdbc, sql1);
		
		
		
		  System.out.println("===22222===>"+sql2);
		  DbUtils.execute(jdbc, sql2);
		}
	  } catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public static void insertOther() {
		// TODO Auto-generated method stub
	  try {
		  System.out.println(sql_other);
		String[][]  comFull = DbUtils.getResultSetArray(jdbc,sql_other );

		for (int z = 0; z < comFull.length; z++) {
			
		String a = comFull[z][0];
		String b = comFull[z][1];
		String c = comFull[z][2];
		String d = comFull[z][3];
		String e = comFull[z][4];
		String f = comFull[z][5];
		String g = comFull[z][6];
		String h = comFull[z][7];
		String i = comFull[z][8];
		String j = comFull[z][9];
		String k = comFull[z][10];
		String l = comFull[z][11];
		String otid = "ot"+UUIDUtil.genUUIDString();
		String gsid = "co"+UUIDUtil.genUUIDString();
		
		String sql1 = "insert  into  t_zjzc_companyinfo(userid,id,`firmFull`,`firm`,`area`,`address`,name,phone,wechat) "
				+ "values('8404481d971c46658d9887560d1a4cc1','"+gsid+"','"+b+"','"+c+"','"+d+e+"','"+f+"','"+g+"','"+h+"','"+i+"')";

		String sql2 = "insert  into  t_zjzc_otherinfo(userid,id,`name`,`type`,`validity`,`remark`,comId,comName) "
				+ "values('8404481d971c46658d9887560d1a4cc1','"+otid+"','"+j+"','"+a+"','"+k+"','"+l+"','"+gsid+"','"+b+"')";
		DbUtils.execute(jdbc, sql1);
		System.out.println("===11111===>"+sql1);
		
		
		
		DbUtils.execute(jdbc, sql2);
		  System.out.println("===22222===>"+sql2);
		}
	  } catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
//	@Override
//	public int insertZc(Map<String, Object> map) {
//	  try {
//		// TODO Auto-generated method stub
//		String  id = UUIDUtil.genUUIDString();
//		String  name = (String)map.get("name");
//		String  userId = (String)map.get("userId");
//		String  projectName = (String)map.get("projectName");
//		String  moneyLow = (String)map.get("moneyLow");
//		String  moneyHigh = (String)map.get("moneyHigh");
//		String  type = (String)map.get("type");
//		String  scale = (String)map.get("scale");
//		String  validity = (String)map.get("validity");
//		String  remark = (String)map.get("remark");
//		String  perTime = (String)map.get("perTime");
//		String  perMoney = (String)map.get("perMoney");
//		String  monthMoney = (String)map.get("monthMoney");
//		String  dateStart = (String)map.get("dateStart");
//		String[][]  comFull = DbUtils.getResultSetArray(jdbc, "select id,firmFull from  t_zjzc_companyinfo where userid='"+userId+"' order by createtime desc limit 0,1");
//		String comId = comFull[z][0];
//		String comName = comFull[z][1];
//		String sql = "insert  into  t_zjzc_zcinfo(userId,id,`name`,`projectName`,`moneyLow`,`moneyHigh`,`type`,`scale`,`validity`,`remark`,`perTime`,`perMoney`,`monthMoney`,`dateStart`,comId,comName) "
//				+ "values('"+userId+"','zc"+id+"','"+name+"','"+projectName+"','"+moneyLow+"','"+moneyHigh+"','"+type+"','"+scale+"','"+validity+"','"+remark+"','"+perTime+"','"+perMoney+"','"+monthMoney+"','"+dateStart+"','"+comId+"','"+comName+"')";
//			return DbUtils.execute(jdbc, sql);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return 0;
//	}
//	
//	@Override
//	public int insertOther(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		try {
//		String  id = UUIDUtil.genUUIDString();
//		String  name = (String)map.get("name");
//		String  userId = (String)map.get("userId");
//		String  type = (String)map.get("type");
//		String  validity = (String)map.get("validity");
//		String  remark = (String)map.get("remark");
//		String[][]  comFull = DbUtils.getResultSetArray(jdbc, "select id,firmFull from  t_zjzc_companyinfo where userid='"+userId+"' order by createtime desc limit 0,1");
//		String comId = comFull[z][0];
//		String comName = comFull[z][1];
//		String sql = "insert  into  t_zjzc_otherinfo(userid,id,`name`,`type`,`validity`,`remark`,comId,comName) "
//				+ "values('"+userId+"','ot"+id+"','"+name+"','"+type+"','"+validity+"','"+remark+"','"+comId+"','"+comName+"')";
//			return DbUtils.execute(jdbc, sql);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return 0;
//	}
//	
//	@Override
//	public int uploadCompanyInfo(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		String  id = UUIDUtil.genUUIDString();
//		String  firmFull = (String)map.get("firmFull");
//		String  userId = (String)map.get("userId");
//		String  firm = (String)map.get("firm");
//		String  area = (String)map.get("area");
//		String  address = (String)map.get("address");
//		String  name = (String)map.get("name");
//		String  phone = (String)map.get("phone");
//		String  wechat = (String)map.get("wechat");
//		String sql = "insert  into  t_zjzc_companyinfo(userid,id,`firmFull`,`firm`,`area`,`address`,name,phone,wechat) "
//				+ "values('"+userId+"','co"+id+"','"+firmFull+"','"+firm+"','"+area+"','"+address+"','"+name+"','"+phone+"','"+wechat+"')";
//		int i = 0 ; 
//		try {
//			i = DbUtils.execute(jdbc, sql);
//			String[][]  getCompanyInfo = DbUtils.getResultSetArray(jdbc, "select firm,area from t_zjzc_companyinfo where userid='"+userId+"' order by createtime desc limit 0,1");
//			if(getCompanyInfo!=null&&getCompanyInfo.length==1){
//				String firmInsert = getCompanyInfo[0][0];
//				String areaInsert = getCompanyInfo[0][1];
//				DbUtils.execute(jdbc,"update t_zjzc_udetail set firm='"+firmInsert+"', location='"+areaInsert+"' where userId='"+userId+"'");
//			}
//			return i;
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return 0;
//	}
//	
//	@Override
//	public int uploadComplain(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		String  userId = (String)map.get("userId");
//		String  productId = (String)map.get("id");
//		String  info = (String)map.get("info");
//		String sql = "insert  into  t_zjzc_complain(userid,`info`,`productId`) "
//				+ "values('"+userId+"','"+info+"','"+productId+"')";
//		try {
//			return DbUtils.execute(jdbc, sql);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return 0;
//	}
//
//	@Override
//	public HashMap<String, Object> getCompanyInfoByUserId(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		String sql = "select  id,firmFull,firm,area,address,name,phone,wechat,userId  from   t_zjzc_companyinfo  where userid='"+map.get("userId")+"' order by  createtime desc limit 0,1";
//		return getMapForDb(sql);
//	}
//	
//	@Override
//	public HashMap<String, Object> getCompanyInfoById(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		String sql = "select  id,firmFull,firm,area,address,name,phone,wechat,userId  from   t_zjzc_companyinfo  where id='"+map.get("id")+"'";
//		return getMapForDb(sql);
//	}

}
