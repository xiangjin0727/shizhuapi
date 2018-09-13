package com.hz.app.index.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hz.app.index.service.BaseService;
import com.hz.app.index.service.QueryService;
import com.hz.util.common.DbUtils;

@Service
public class QueryServiceImpl extends BaseService implements QueryService{

	@Override
	public HashMap<String, Object> getZjMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select id,name,favorite,moneyLow,moneyHigh,type,scale,validity,remark,comId,userId,createtime from t_zjzc_zjinfo where id='"+map.get("id")+"'";
		return getMapForDb(sql);
	}
	
	@Override
	public HashMap<String, Object> getZcMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select id,name,projectName,moneyLow,moneyHigh,type,scale,validity,remark,perTime,perMoney,monthMoney,dateStart,comId,userId,createtime,perMoneyHigh from t_zjzc_zcinfo where id='"+map.get("id")+"'";
		return getMapForDb(sql);
	}
	
	@Override
	public HashMap<String, Object> getOtherMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select id,name,type,validity,remark,comId,userId,createtime from t_zjzc_otherinfo where id='"+map.get("id")+"'";
		return getMapForDb(sql);
	}

	@Override
	public List<HashMap<String, Object>> getTjList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int    pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
		int    pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
		String sql = "select id,type from t_zjzc_tj "+" limit " + (pageIndex-1)*pageSize + " , "+pageSize;
		return getListForDb(sql);
	}
	
	
	@Override
	public int insertKeyWords(Map<String, Object> map) {
		// TODO Auto-generated method stub
		try {
			String keyWords = String.valueOf(map.get("keyword"));
			if(DbUtils.checkExsit(jdbc, "SELECT 1  FROM `t_zjzc_keywords`  WHERE keyword='"+keyWords+"'")){
				DbUtils.execute(jdbc, "update t_zjzc_keywords set cnum=cnum+1 where keyword='"+keyWords+"' ");
			}else{
				DbUtils.execute(jdbc, "insert into  t_zjzc_keywords(keyword) values('"+keyWords+"') ");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 1;
	}
	
	@Override
	public List<HashMap<String, Object>> getZjListByKey(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select id,name,favorite,moneyLow,moneyHigh,type,scale,validity,remark,comId,comName,userId,createtime from t_zjzc_zjinfo where flag = 1 and (name like '%"+map.get("keyword")+"%' or comName like '%"+map.get("keyword")+"%')";
		return getListForDb(sql);
	}
	
	@Override
	public List<HashMap<String, Object>> getZcListByKey(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select id,name,projectName,moneyLow,moneyHigh,type,scale,validity,remark,perTime,perMoney,monthMoney,dateStart,comId,comName,userId,createtime,perMoneyHigh from t_zjzc_zcinfo where flag=1 and (name like '%"+map.get("keyword")+"%' or projectName like  '%"+map.get("keyword")+"%' or comName like '%"+map.get("keyword")+"%')";
		return getListForDb(sql);
	}
	
	@Override
	public List<HashMap<String, Object>> getOtherListByKey(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql = "select id,name,type,validity,remark,comId,comName,userId,createtime from t_zjzc_otherinfo where flag=1 and ( name like '%"+map.get("keyword")+"%'  or comName like '%"+map.get("keyword")+"%')";
		return getListForDb(sql);
	}
	
	
	@Override
	public List<HashMap<String, Object>> getZjList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sqlTmp = " ";
		String userId = (String)map.get("userId");
		if(userId!=null){
			sqlTmp = " and  userId='"+userId+"' ";
		}
		String sql = "select id,name,favorite,moneyLow,moneyHigh,type,scale,validity,remark,comId,comName,userId,createtime from t_zjzc_zjinfo where flag = 1 "+sqlTmp;
		System.out.println("======>"+sql);
		return getListForDb(sql);
	}
	
	@Override
	public List<HashMap<String, Object>> getZcList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sqlTmp = " ";
		String userId = (String)map.get("userId");
		if(userId!=null){
			sqlTmp = " and   userId='"+userId+"' ";
		}
		String sql = "select id,name,projectName,moneyLow,moneyHigh,type,scale,validity,remark,perTime,perMoney,monthMoney,dateStart,comId,comName,userId,createtime,perMoneyHigh from t_zjzc_zcinfo where flag=1 "+sqlTmp;
		System.out.println("======>"+sql);
		return getListForDb(sql);
	}
	
	@Override
	public List<HashMap<String, Object>> getOtherList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sqlTmp = " ";
		String userId = (String)map.get("userId");
		if(userId!=null){
			sqlTmp = " and  userId='"+userId+"' ";
		}
		String sql = "select id,name,type,validity,remark,comId,comName,userId,createtime from t_zjzc_otherinfo where flag=1 "+sqlTmp;
		System.out.println("======>"+sql);
		return getListForDb(sql);
	}

	@Override
	public List<HashMap<String, Object>> getKeyWordsList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int    pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
		int    pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
		String sql = "select keyword from t_zjzc_keywords order by cnum desc limit " + (pageIndex-1)*pageSize + " , "+pageSize;
		return getListForDb(sql);
	}
	
	@Override
	public int countKeyWords(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int count = 0 ;
		String sql = "select id from t_zjzc_keywords ";
		List<HashMap<String,Object>> objectList = getListForDb(sql);
		if(objectList!=null&&objectList.size()>0){
			count = objectList.size();
		}
		return count;
	}
	
}
