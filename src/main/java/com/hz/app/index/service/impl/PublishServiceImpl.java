package com.hz.app.index.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hz.app.index.service.BaseService;
import com.hz.app.index.service.PublishService;
import com.hz.core.util.UUIDUtil;
import com.hz.util.common.DbUtils;

@Service
public class PublishServiceImpl extends BaseService implements PublishService{

	@Override
	public int insertZj(Map<String, Object> map) {
		// TODO Auto-generated method stub
	  try {
		//String  id = UUIDUtil.genUUIDString();
		String  id = (String)map.get("id");
		String  name = (String)map.get("name");
		String  userId = (String)map.get("userId");
		String  favorite = (String)map.get("favorite");
		String  moneyLow = (String)map.get("moneyLow");
		String  moneyHigh = (String)map.get("moneyHigh");
		String  type = (String)map.get("type");
		String  scale = (String)map.get("scale");
		String  validity = (String)map.get("validity");
		String  remark = (String)map.get("remark");
		String[][]  comFull = DbUtils.getResultSetArray(jdbc, "select id,firmFull from  t_zjzc_companyinfo where userid='"+userId+"' order by createtime desc limit 0,1");
		String comId = comFull[0][0];
		String comName = comFull[0][1];
		String sql = "insert  into  t_zjzc_zjinfo(userId,`id`,`name`,`favorite`,`moneyLow`,`moneyHigh`,`type`,`scale`,`validity`,`remark`,comId,comname) "
				+ "values('"+userId+"','"+id+"','"+name+"','"+favorite+"','"+moneyLow+"','"+moneyHigh+"','"+type+"','"+scale+"','"+validity+"','"+remark+"','"+comId+"','"+comName+"')";
		
			return DbUtils.execute(jdbc, sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int insertZc(Map<String, Object> map) {
	  try {
		// TODO Auto-generated method stub
		//String  id = UUIDUtil.genUUIDString();
		String  name = (String)map.get("name");
		String  id = (String)map.get("id");
		String  userId = (String)map.get("userId");
		String  projectName = (String)map.get("projectName");
		String  moneyLow = (String)map.get("moneyLow");
		String  moneyHigh = (String)map.get("moneyHigh");
		String  type = (String)map.get("type");
		String  scale = (String)map.get("scale");
		String  validity = (String)map.get("validity");
		String  remark = (String)map.get("remark");
		String  perTime = (String)map.get("perTime");
		String  perMoney = (String)map.get("perMoney");
		String  monthMoney = (String)map.get("monthMoney");
		String  dateStart = (String)map.get("dateStart");
		String  perMoneyHigh = (String)map.get("perMoneyHigh");
		String[][]  comFull = DbUtils.getResultSetArray(jdbc, "select id,firmFull from  t_zjzc_companyinfo where userid='"+userId+"' order by createtime desc limit 0,1");
		String comId = comFull[0][0];
		String comName = comFull[0][1];
		String sql = "insert  into  t_zjzc_zcinfo(userId,id,`name`,`projectName`,`moneyLow`,`moneyHigh`,`type`,`scale`,`validity`,`remark`,`perTime`,`perMoney`,`monthMoney`,`dateStart`,comId,comName,perMoneyHigh) "
				+ "values('"+userId+"','"+id+"','"+name+"','"+projectName+"','"+moneyLow+"','"+moneyHigh+"','"+type+"','"+scale+"','"+validity+"','"+remark+"','"+perTime+"','"+perMoney+"','"+monthMoney+"','"+dateStart+"','"+comId+"','"+comName+"','"+perMoneyHigh+"')";
			return DbUtils.execute(jdbc, sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int insertOther(Map<String, Object> map) {
		// TODO Auto-generated method stub
		try {
		//String  id = UUIDUtil.genUUIDString();
		String  id = (String)map.get("id");
		String  name = (String)map.get("name");
		String  userId = (String)map.get("userId");
		String  type = (String)map.get("type");
		String  validity = (String)map.get("validity");
		String  remark = (String)map.get("remark");
		String[][]  comFull = DbUtils.getResultSetArray(jdbc, "select id,firmFull from  t_zjzc_companyinfo where userid='"+userId+"' order by createtime desc limit 0,1");
		String comId = comFull[0][0];
		String comName = comFull[0][1];
		String sql = "insert  into  t_zjzc_otherinfo(userid,id,`name`,`type`,`validity`,`remark`,comId,comName) "
				+ "values('"+userId+"','"+id+"','"+name+"','"+type+"','"+validity+"','"+remark+"','"+comId+"','"+comName+"')";
			return DbUtils.execute(jdbc, sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int uploadCompanyInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String  id = UUIDUtil.genUUIDString();
		String  firmFull = (String)map.get("firmFull");
		String  userId = (String)map.get("userId");
		String  firm = (String)map.get("firm");
		String  area = (String)map.get("area");
		String  address = (String)map.get("address");
		String  name = (String)map.get("name");
		String  phone = (String)map.get("phone");
		String  wechat = (String)map.get("wechat");
		String sql = "insert  into  t_zjzc_companyinfo(userid,id,`firmFull`,`firm`,`area`,`address`,name,phone,wechat) "
				+ "values('"+userId+"','co"+id+"','"+firmFull+"','"+firm+"','"+area+"','"+address+"','"+name+"','"+phone+"','"+wechat+"')";
		int i = 0 ; 
		try {
			i = DbUtils.execute(jdbc, sql);
			String[][]  getCompanyInfo = DbUtils.getResultSetArray(jdbc, "select firm,area from t_zjzc_companyinfo where userid='"+userId+"' order by createtime desc limit 0,1");
			if(getCompanyInfo!=null&&getCompanyInfo.length==1){
				String firmInsert = getCompanyInfo[0][0];
				String areaInsert = getCompanyInfo[0][1];
				DbUtils.execute(jdbc,"update t_zjzc_udetail set firm='"+firmInsert+"', location='"+areaInsert+"' where userId='"+userId+"'");
			}
			return i;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int uploadComplain(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String  userId = (String)map.get("userId");
		String  productId = (String)map.get("id");
		String  info = (String)map.get("info");
		String sql = "insert  into  t_zjzc_complain(userid,`info`,`productId`) "
				+ "values('"+userId+"','"+info+"','"+productId+"')";
		try {
			return DbUtils.execute(jdbc, sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public HashMap<String, Object> getCompanyInfoByUserId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select  id,firmFull,firm,area,address,name,phone,wechat,userId  from   t_zjzc_companyinfo  where userid='"+map.get("userId")+"' order by  createtime desc limit 0,1";
		return getMapForDb(sql);
	}
	
	@Override
	public HashMap<String, Object> getCompanyInfoById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select  id,firmFull,firm,area,address,name,phone,wechat,userId  from   t_zjzc_companyinfo  where id='"+map.get("id")+"'";
		return getMapForDb(sql);
	}

}
