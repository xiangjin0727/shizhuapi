package com.hz.app.index.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hz.app.index.service.BaseService;
import com.hz.app.index.service.ModelService;
import com.hz.app.index.vo.User;
import com.hz.core.util.UUIDUtil;
import com.hz.util.common.DbUtils;

@Service
public class ModelServiceImpl extends BaseService implements ModelService {

	
	@Override
	public User loginByUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		try {
			String[][] res = DbUtils.getResultSetArray(jdbc, "select id,mobile,password from t_user where mobile='"+map.get("mobile")+"' and password='"+map.get("password")+"'") ;
			if(res!=null && res.length>0){
				User user = new User();
				user.setId(res[0][0]);
				user.setMobile(res[0][1]);
				user.setPassword(res[0][2]);
				return user;
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public User wchatUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		try {
			String[][] res = DbUtils.getResultSetArray(jdbc, "select id,mobile,password from t_user where mobile='"+map.get("mobile")+"'") ;
			if(res!=null && res.length>0){
				User user = new User();
				user.setId(res[0][0]);
				user.setMobile(res[0][1]);
				user.setPassword(res[0][2]);
				return user;
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int register(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String  id = UUIDUtil.genUUIDString();
		String  mobile = (String)map.get("mobile");
		String  password = (String)map.get("password");
		String sql = "insert  into  t_user(id,mobile,password) values('"+id+"','"+mobile+"','"+password+"')";
		String sql2 = "insert into t_zjzc_udetail(userId,mobile) values('"+id+"','"+mobile+"')";
		int userInsertFlag = -1;
		try {
			if(DbUtils.checkExsit(jdbc, "select 1 from t_user where mobile='"+mobile+"'")){
				return 2;
			}
			userInsertFlag =  DbUtils.execute(jdbc, sql);
			int detailFlag =  DbUtils.execute(jdbc, sql2);
			if(userInsertFlag > 0 && detailFlag > 0){
				return 1;
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return userInsertFlag;
	}

	@Override
	public int resetPassword(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select 1 from t_user where mobile='"+map.get("mobile")+"'";
		System.out.println("====1===>>>"+sql);
		try {
			if(DbUtils.checkExsit(jdbc, sql)){
			sql = "update  t_user set password='"+map.get("newPassword")+"'  where  mobile='"+map.get("mobile")+"'";
				return DbUtils.execute(jdbc, sql);
			} 
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int updatePassword(Map<String, Object> map) {
		// TODO Auto-generated method stub
		try {
		String sql = "select 1 from t_user where mobile='"+map.get("mobile")+"' and password='"+map.get("password")+"'";
		System.out.println("====1===>>>"+sql);
		if(DbUtils.checkExsit(jdbc, sql)){
			sql = "update  t_user set password='"+map.get("newPassword")+"'  where  mobile='"+map.get("mobile")+"'";
			System.out.println(sql+"====2======");
			return DbUtils.execute(jdbc, sql);
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int setYscl(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "";
		try {
			if(DbUtils.checkExsit(jdbc, "select id from  t_zjzc_yscl where mobile='"+map.get("mobile")+"'")){
				sql = "update  t_zjzc_yscl set nickname='"+map.get("nickname")+"',name='"+map.get("name")+"',firm='"+map.get("firm")+"'  where  mobile='"+map.get("mobile")+"'";
			}else{
				sql = "insert into  t_zjzc_yscl (nickname,name,firm,mobile,id) values('"+map.get("nickname")+"','"+map.get("name")+"','"+map.get("firm")+"','"+map.get("mobile")+"','"+UUIDUtil.genUUIDString()+"')";
			}
			return DbUtils.execute(jdbc, sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int updateUserInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "";
		String type = (String)map.get("type");
		try {
			 if(!DbUtils.checkExsit(jdbc, "select 1 from t_zjzc_infoLog where mobile='"+map.get("mobile")+"' and infoid='"+map.get("id")+"'and type='"+type+"'  and TO_DAYS(createtime) = TO_DAYS(NOW())")){
				 if("0".equals(type)){
					 sql = "update  t_zjzc_udetail set numInfoYet=numInfoYet-1   where  mobile='"+map.get("mobile")+"'"; 
				 }
				 else if("1".equals(type)){
					 sql = "update  t_zjzc_udetail set numReportYet=numReportYet-1   where  mobile='"+map.get("mobile")+"'";
				 }else if("2".equals(type)){
					 sql = "update  t_zjzc_udetail set numpublishYet=numpublishYet-1   where  mobile='"+map.get("mobile")+"'";
				 }else if("3".equals(type)){
					 sql = "update  t_zjzc_udetail set numgroupYet=numgroupYet-1   where  mobile='"+map.get("mobile")+"'";
				 }
				 DbUtils.execute(jdbc, "INSERT INTO  t_zjzc_infoLog(infoid,mobile,type) VALUES('"+map.get("id")+"','"+map.get("mobile")+"','"+type+"')");
				 return DbUtils.execute(jdbc, sql);
			 }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int updateUserInfoNoDay(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "";
		String type = (String)map.get("type");
		try {
			 if(!DbUtils.checkExsit(jdbc, "select 1 from t_zjzc_infoLog where mobile='"+map.get("mobile")+"' and infoid='"+map.get("id")+"' and type='"+type+"'")){
				 if("0".equals(type)){
					 sql = "update  t_zjzc_udetail set numInfoYet=numInfoYet-1   where  mobile='"+map.get("mobile")+"'"; 
				 }else if("1".equals(type)){
					 sql = "update  t_zjzc_udetail set numReportYet=numReportYet-1   where  mobile='"+map.get("mobile")+"'";
				 }else if("2".equals(type)){
					 sql = "update  t_zjzc_udetail set numpublishYet=numpublishYet-1   where  mobile='"+map.get("mobile")+"'";
				 }else if("3".equals(type)){
					 sql = "update  t_zjzc_udetail set numgroupYet=numgroupYet-1   where  mobile='"+map.get("mobile")+"'";
				 }
				 DbUtils.execute(jdbc, "INSERT INTO  t_zjzc_infoLog(infoid,mobile,type) VALUES('"+map.get("id")+"','"+map.get("mobile")+"','"+type+"')");
				 return DbUtils.execute(jdbc, sql);
			 }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int getLogById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		try {
			if(DbUtils.checkExsit(jdbc, "select 1 from t_zjzc_infoLog where mobile='"+map.get("mobile")+"' and infoid='"+map.get("id")+"' and TO_DAYS(createtime) = TO_DAYS(NOW()) and type='"+map.get("type")+"'")){
				return 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int getLogByIdNoDay(Map<String, Object> map) {
		// TODO Auto-generated method stub
		try {
			if(DbUtils.checkExsit(jdbc, "select 1 from t_zjzc_infoLog where mobile='"+map.get("mobile")+"' and infoid='"+map.get("id")+"' and type='"+map.get("type")+"'")){
				return 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	
	@Override
	public int queryUserInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		try {
			return Integer.parseInt(DbUtils.getValue(jdbc, "SELECT count(1)  FROM `t_zjzc_infolog`  WHERE mobile='"+map.get("mobile")+"'  AND TO_DAYS(createtime) = TO_DAYS(NOW()) and type='"+map.get("type")+"'"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int queryUserInfoNoDay(Map<String, Object> map) {
		// TODO Auto-generated method stub
		try {
			return Integer.parseInt(DbUtils.getValue(jdbc, "SELECT count(1)  FROM `t_zjzc_infolog`  WHERE mobile='"+map.get("mobile")+"' and type='"+map.get("type")+"'"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public HashMap<String,Object> getYscl(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select nickname,name,firm from t_zjzc_yscl where mobile='"+map.get("mobile")+"'";
	    return getMapForDb(sql);
	}
	
	@Override
	public HashMap<String,Object> zjzcInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select id,name,type,rate,piece,area,amount,level,moneyRange,perTime,perMoney,browserCount,validity,firmName,projectInfo,contact,serviceTime,mobile,wechat from t_zjzc_info where id='1'";
		return getMapForDb(sql);
	}
	
	
	
	public static void main(String[] args) {
		try {
			String sql = "select id,username,    mobile,sex from t_user limit 0,1";
			System.out.println(getMapForDb(sql));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public HashMap<String, Object> hyreportInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select id,title,category,countDownload,detail,urlDownload from t_zjzc_report where id='"+map.get("id")+"'";
	    return getMapForDb(sql);
	}
	
	@Override
	public HashMap<String, Object> getChatInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select nickname,openid,avatarurl from t_zjzc_udetail where mobile='"+map.get("mobile")+"'";
		return getMapForDb(sql);
	}
	
	@Override
	public HashMap<String, Object> userInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select userId,avatarUrl,level,nickname,focus,firm,position,location from t_zjzc_udetail where userId='"+map.get("userId")+"'";
		return getMapForDb(sql);
	}
	
	@Override
	public HashMap<String, Object> userInfoOther(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select userId,avatarUrl,level,nickname,focus,firm,position,location from t_zjzc_udetail where userId='"+map.get("userId")+"'";
		return getMapForDb(sql);
	}

	@Override
	public HashMap<String, Object> messageInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select id,title,info,time,detail from t_zjzc_message where id='"+map.get("id")+"'";
		return getMapForDb(sql);
	}

	@Override
	public List<HashMap<String, Object>> qList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int    pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
		int    pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
		String sql = "select id,type,name,amount from t_zjzc_quan limit " + (pageIndex-1)*pageSize + " , "+pageSize;
		return getListForDb(sql);
	}
	
	@Override
	public int qListCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int count = 0 ;
		String sql = "select id,type,name,amount from t_zjzc_quan ";
		List<HashMap<String,Object>> objectList = getListForDb(sql);
		if(objectList!=null&&objectList.size()>0){
			count = objectList.size();
		}
		return count;
	}
	
	@Override
	public int hyreportCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int count = 0 ;
		String sql = "select id from t_zjzc_report ";
		List<HashMap<String,Object>> objectList = getListForDb(sql);
		if(objectList!=null&&objectList.size()>0){
			count = objectList.size();
		}
		return count;
	}
	
	@Override
	public List<HashMap<String, Object>> messageList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select id,title,info,time from t_zjzc_message ";
		return getListForDb(sql);
	}
	
	@Override
	public HashMap<String, Object> getUserInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select level,numInfo,numReport,numPublish,numGroup,numInfoYet,numReportYet,numPublishYet,numGroupYet  from  `t_zjzc_udetail` where   mobile= '"+map.get("mobile")+"'";
		return getMapForDb(sql);
	}

	@Override
	public List<HashMap<String, Object>> focusList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int    pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
		int    pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
		String sql = "SELECT f.touserid AS userId,u.avatarUrl,u.level,u.nickname,u.firm   FROM  t_zjzc_focus  f  LEFT JOIN  t_zjzc_udetail u  ON  f.touserid = u.userid where  f.userid='"+map.get("userId")+"' limit " + (pageIndex-1)*pageSize + " , "+pageSize;
		System.out.println(sql);
		List<HashMap<String,Object>> objectList = new ArrayList<HashMap<String,Object>>();
		try {
			String[][]  resDb = DbUtils.getResultSetArray(jdbc, sql);
			if(resDb!=null && resDb.length >0){
				for(int k = 0 ; k < resDb.length ; k ++){
					HashMap<String,Object> objectHashMap = new HashMap<String,Object>();
					objectHashMap.put("userId", (String)resDb[k][0]);
					objectHashMap.put("avatarUrl",resDb[k][1]==null?"":resDb[k][1]);
					objectHashMap.put("level", resDb[k][2]==null?"":resDb[k][2]);
					objectHashMap.put("nickname", resDb[k][3]==null?"":resDb[k][3]);
					objectHashMap.put("firm", resDb[k][4]==null?"":resDb[k][4]);
					objectList.add(objectHashMap);
				}
			}
			return  objectList;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return  objectList;
		}
	
	}

	@Override
	public List<HashMap<String, Object>> bannerList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select bannerType,imageUrl,goToUrl,title from t_zjzc_banner ";
		return getListForDb(sql);
	}
	
	@Override
	public List<HashMap<String, Object>> hyreportList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int    pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
		int    pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
		//String sql = "select id,type,name,amount from t_zjzc_quan limit " + (pageIndex-1)*pageSize + " , "+pageSize;
		String sql = "select id,title,category,countDownload,SUBSTRING(urldownload,LOCATE('.',urldownload)+1) as type,urldownload from t_zjzc_report  limit "+ (pageIndex-1)*pageSize + " , "+pageSize;
		System.out.println(sql);
		List<HashMap<String,Object>> objectList = new ArrayList<HashMap<String,Object>>();
		try {
			String[][]  resDb = DbUtils.getResultSetArray(jdbc, sql);
			if(resDb!=null && resDb.length >0){
				for(int k = 0 ; k < resDb.length ; k ++){
					HashMap<String,Object> objectHashMap = new HashMap<String,Object>();
					objectHashMap.put("id", (String)resDb[k][0]);
					objectHashMap.put("title",resDb[k][1]==null?"":resDb[k][1]);
					objectHashMap.put("category", resDb[k][2]==null?"":resDb[k][2]);
					objectHashMap.put("countDownload", resDb[k][3]==null?"":resDb[k][3]);
					objectHashMap.put("type", resDb[k][4]==null?"":resDb[k][4]);
					objectHashMap.put("urldownload", resDb[k][5]==null?"":"");
					objectList.add(objectHashMap);
				}
			  }
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return  objectList;
			}
		return  objectList;
	}
	
	
	@Override
	public List<HashMap<String, Object>> hyzxList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int    pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
		int    pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
		List<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
		//String sql = "select id,type,name,amount from t_zjzc_quan limit " + (pageIndex-1)*pageSize + " , "+pageSize;
		String sql = "select id,title,source,url,countView  from  t_zjzc_hyzx order by  createtime desc limit "+ (pageIndex-1)*pageSize + " , "+pageSize;
		System.out.println(sql);
		try {
			return  getListForDb(sql);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return  arrayList;
			}
		
	}
	
	@Override
	public List<HashMap<String, Object>> zjzcList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select id,name,type,rate,piece,area,amount,level from t_zjzc_info ";
		return getListForDb(sql);
	}

	@Override
	public int focus(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String action = (String)map.get("action");
		String sql = "";
		if("0".equals(action)){//关注
			sql = "insert into  t_zjzc_focus(userid,touserid) values('"+map.get("userId")+"','"+map.get("touserId")+"')";
		}else{
			sql = "delete  from t_zjzc_focus where  userid='"+map.get("userId")+"' and  touserId='"+map.get("touserId")+"'";
		}
		try {
			return DbUtils.execute(jdbc, sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int payResDb(Map<String, String> map) {
		// TODO Auto-generated method stub
		String sql = "";
		try {
		if(!DbUtils.checkExsit(jdbc, "select 1 from t_zjzc_pay  where  out_trade_no='"+map.get("out_trade_no")+"' and openid='"+map.get("openid")+"'")){
			 sql = "INSERT INTO  `t_zjzc_pay`(`is_subscribe`,`nonce_str`,`out_trade_no`,`transaction_id`,`sign`,`mch_id`,`total_fee`,`time_end`,`openid`,`bank_type`,`cash_fee`) "
			    		+ "VALUES('"+map.get("is_subscribe")+"','"+map.get("nonce_str")+"','"+map.get("out_trade_no")+"','"+map.get("transaction_id")+"','"+map.get("sign")+"','"+map.get("mch_id")+"','"+map.get("total_fee")+"','"+map.get("time_end")+"','"+map.get("openid")+"','"+map.get("bank_type")+"','"+map.get("cash_fee")+"')";
			 String level = map.get("level");
			 if("1".equals(level)){
				 DbUtils.execute(jdbc, "update t_zjzc_udetail set numinfo=20,numreport=5,numpublish=10,numgroup=2,numinfoyet=20,numreportyet=5,numpublishyet=10,numgroupyet=2,level=1 where  openid='"+map.get("openid")+"'");
				 String mobile=DbUtils.getValue(jdbc, "select mobile from t_zjzc_udetail where openid='"+map.get("openid")+"'");
				 DbUtils.execute(jdbc, "delete from t_zjzc_infolog where mobile='"+mobile+"'");
			 }
			 if("2".equals(level)){
				 DbUtils.execute(jdbc, "update t_zjzc_udetail set numinfo=100000,numreport=100000,numpublish=100000,numgroup=5,numinfoyet=100000,numreportyet=100000,numpublishyet=100000,numgroupyet=5,level=2 where  openid='"+map.get("openid")+"'");
				 String mobile=DbUtils.getValue(jdbc, "select mobile from t_zjzc_udetail where openid='"+map.get("openid")+"'");
				 DbUtils.execute(jdbc, "delete from t_zjzc_infolog where mobile='"+mobile+"'");
			 }
			 
			 return DbUtils.execute(jdbc, sql);
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean existFocus(Map<String, String> map) {
		// TODO Auto-generated method stub
		String sql = "select 1 from t_zjzc_focus where userid='"+map.get("myUserId")+"' and touserid='"+map.get("ToUserId")+"'";
		System.out.println("==执行SQL=====>"+sql);
		try {
			if(DbUtils.checkExsit(jdbc, sql)){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public String getOpenId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String openId = "";
		try {
			 openId = DbUtils.getValue(jdbc, "select openid from t_zjzc_udetail where  mobile='"+map.get("mobile")+"'");
		     if(openId==null){
		    	 return "";
		     }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
		return openId;
	}
	
	@Override
	public String getMobile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String mobile = "";
		try {
			mobile = DbUtils.getValue(jdbc, "select mobile from t_zjzc_udetail where  openid='"+map.get("openid")+"'");
			if(mobile==null){
				return "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
		return mobile;
	}
}
