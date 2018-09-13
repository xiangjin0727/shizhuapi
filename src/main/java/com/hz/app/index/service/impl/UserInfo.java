package com.hz.app.index.service.impl;



import com.hz.core.util.UUIDUtil;
import com.hz.util.common.DbUtils;
import com.hz.util.common.JDBCUtilUser;

public class UserInfo  {

	private static JDBCUtilUser  jdbc = new JDBCUtilUser();
	private static JDBCUtilUser  jdbc1 = new JDBCUtilUser(null,null);
	private static JDBCUtilUser  jdbc2 = new JDBCUtilUser(null,null,null);
	
	public static void main(String[] args) {
		//insertUserJk();
		//System.out.println("sabc".indexOf("1"));
		//getInfo();
		getInfoForDb();
	}
	
	
	
	
	public static void getInfoForDb() {
		  try {
			String sql = "SELECT id,mobile,realname,sex,birthday,customerid  FROM `t_user`";  
			String[][]  comFull = DbUtils.getResultSetArray(jdbc,sql,0 );
			
			for (int i = 0; i < comFull.length; i++) {
				String  id = comFull[i][0];
				String  mobile = comFull[i][1].trim();
				String  realname = comFull[i][2].trim();
				String  sex = comFull[i][3].trim();
				String  birthday = comFull[i][4].trim();
				String  customerid = comFull[i][5].trim();
				System.out.println(i+"========="+mobile);
				if(DbUtils.checkExsit(jdbc2, "select 1 from t_user where mobile='"+mobile+"'",2)){//存在
					String[][] regsourceId = DbUtils.getResultSetArray(jdbc2,"select regSource,id from t_user where mobile='"+mobile+"' ",2);
					if(regsourceId!=null&&regsourceId.length>0){
						String regsource = regsourceId[0][0];
						String userId = regsourceId[0][1];
						if(regsource!=null&&"HZD".equals(regsource)){//存在汇中贷，更新
							String sql21= "delete from t_user where  mobile='"+mobile+"'";
							String sql22= "delete from t_user_detail where  userid='"+userId+"'";
							String sql23= "delete from t_authentication where  userid='"+userId+"'";
							String sql24= "delete from t_fd_account where  userid='"+userId+"'";
							DbUtils.execute(jdbc2, sql21,2);
							DbUtils.execute(jdbc2, sql22,2);
							DbUtils.execute(jdbc2, sql23,2);
							DbUtils.execute(jdbc2, sql24,2);

							String  id1 = DbUtils.getValue(jdbc1, "select id from t_user where mobile='"+mobile+"'",1);
							String sql11= "delete from t_user where  mobile='"+mobile+"'";
							String sql12= "delete from t_user_detail where  userid='"+id1+"'";
							String sql13= "delete from t_authentication where  userid='"+id1+"'";
							String sql14= "delete from t_fd_account where  userid='"+id1+"'";
							DbUtils.execute(jdbc1, sql11,1);
							DbUtils.execute(jdbc1, sql12,1);
							DbUtils.execute(jdbc1, sql13,1);
							DbUtils.execute(jdbc1, sql14,1);
							
							
						   String  idcard = DbUtils.getValue(jdbc, "select idcard from t_authentication where userid='"+id+"'",0);
						   String  accountId = UUIDUtil.genUUIDString();
						   
						   String sql1 = "insert into t_user(id,username,realname,sex,birthday,regdate,mobile,password,customerid,usertype,channelId) "
						   		+ " values('"+id+"','"+mobile+"','"+realname+"','"+sex+"','"+birthday+"',now(),'"+mobile+"','C2D5CB29AFCDB6A1080169D3609AB87CBA581F77247249A09107AFCE','"+customerid+"','2','HZD_DR')";
						   String sql2 = "insert into t_user_detail(userid) values('"+id+"')";
						   String sql3 = "insert into t_authentication(userid,idcard,idcardstatus,mobile,mobilestatus) "
						   		+ " values('"+id+"','"+idcard+"','1','"+mobile+"','1')";
						   String sql4 = "insert into t_fd_account(accountid,userid) values('"+accountId+"','"+id+"')";
						   System.out.println(sql1);
						   DbUtils.execute(jdbc2, sql1,2);
						   DbUtils.execute(jdbc2, sql2,2);
						   DbUtils.execute(jdbc2, sql3,2);
						   DbUtils.execute(jdbc2, sql4,2);
						   DbUtils.execute(jdbc1, sql1,1);
						   DbUtils.execute(jdbc1, sql2,1);
						   DbUtils.execute(jdbc1, sql3,1);
						   DbUtils.execute(jdbc1, sql4,1);
							
							
						}
					}
					
				}else{//不存在直接插入
				   String  idcard = DbUtils.getValue(jdbc, "select idcard from t_authentication where userid='"+id+"'",0);
				   String  accountId = UUIDUtil.genUUIDString();
				   
				   String sql1 = "insert into t_user(id,username,realname,sex,birthday,regdate,mobile,password,customerid,usertype,channelId) "
				   		+ " values('"+id+"','"+mobile+"','"+realname+"','"+sex+"','"+birthday+"',now(),'"+mobile+"','C2D5CB29AFCDB6A1080169D3609AB87CBA581F77247249A09107AFCE','"+customerid+"','2','dr')";
				   String sql2 = "insert into t_user_detail(userid) values('"+id+"')";
				   String sql3 = "insert into t_authentication(userid,idcard,idcardstatus,mobile,mobilestatus) "
				   		+ " values('"+id+"','"+idcard+"','1','"+mobile+"','1')";
				   String sql4 = "insert into t_fd_account(accountid,userid) values('"+accountId+"','"+id+"')";
				   DbUtils.execute(jdbc2, sql1,2);
				   DbUtils.execute(jdbc2, sql2,2);
				   DbUtils.execute(jdbc2, sql3,2);
				   DbUtils.execute(jdbc2, sql4,2);
				   DbUtils.execute(jdbc1, sql1,1);
				   DbUtils.execute(jdbc1, sql2,1);
				   DbUtils.execute(jdbc1, sql3,1);
				   DbUtils.execute(jdbc1, sql4,1);
				}
				
			}

		  } catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public static void getInfo() {
//		  try {
//			String sql = "SELECT id,mobile  FROM `t_user`";  
//			String[][]  comFull = DbUtils.getResultSetArray(jdbc,sql );
//			
//			for (int i = 0; i < comFull.length; i++) {
//				String  id = comFull[i][0];
//				String  mobile = comFull[i][1];
//				String sql1 = "update t_authentication  set mobile='"+mobile+"',mobilestatus='1' where  userid='"+id+"'";
//				DbUtils.execute(jdbc, sql1);
//				System.out.println(i);
//				
//			}
//
//		  } catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//	
//	
//	
//	public static void insertUser() {
//		  try {
//			String sql = "SELECT `平台会员编号`,`会员姓名`,`证件号码`,`手机号`  FROM `a_cj`";  
//			String[][]  comFull = DbUtils.getResultSetArray(jdbc,sql );
//			
//			for (int i = 0; i < comFull.length; i++) {
//				String  id = UUIDUtil.genUUIDString();
//				String  userType = "2";
//				String  password="C2D5CB29AFCDB6A1080169D3609AB87CBA581F77247249A09107AFCE";
//				String  customerId = comFull[i][0];
//				String  realname = comFull[i][1];
//				String  idcard = comFull[i][2];
//				String  mobile = comFull[i][3];
//				String  ac_id = UUIDUtil.genUUIDString();
//				String sql1 = "insert into t_user(id,mobile,realname,userType,password,customerId)"
//						+ "values('"+id+"','"+mobile+"','"+realname+"','"+userType+"','"+password+"','"+customerId+"')";
//				String sql2 = "insert into t_user_detail(userid) values('"+id+"')";
//				String sql3 = "insert into t_fd_account(userid,accountId) values('"+id+"','"+ac_id+"')";
//				String sql4 = "insert into t_authentication(userid,idcard) values('"+id+"','"+idcard+"')";
//				String sql5 = "insert into t_fd_bank_card(userid,cardNo,accountId) values('"+id+"','"+idcard+"')";
//				System.out.println(sql1);
//				DbUtils.execute(jdbc, sql1);
//				DbUtils.execute(jdbc, sql2);
//				DbUtils.execute(jdbc, sql3);
//				DbUtils.execute(jdbc, sql4);
//			}
//
//		  } catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//	
//	
//	
//	public static void insertUserJk() {
//	  try {
//		String sql = "SELECT `平台会员编号`,`会员姓名`,`身份证号`,`手机号`  FROM `a_cj_jk`";  
//		String[][]  comFull = DbUtils.getResultSetArray(jdbc,sql );
//		
//		for (int i = 0; i < comFull.length; i++) {
//			String  id = UUIDUtil.genUUIDString();
//			String  userType = "2";
//			String  password="C2D5CB29AFCDB6A1080169D3609AB87CBA581F77247249A09107AFCE";
//			String  customerId = comFull[i][0];
//			String  realname = comFull[i][1];
//			String  idcard = comFull[i][2];
//			String  mobile = comFull[i][3];
//			String sql1 = "insert into t_user(id,mobile,realname,userType,password,customerId)"
//					+ "values('"+id+"','"+mobile+"','"+realname+"','"+userType+"','"+password+"','"+customerId+"')";
//			String sql2 = "insert into t_user_detail(userid) values('"+id+"')";
//			String sql3 = "insert into t_fd_account(userid,accountId) values('"+id+"','"+UUIDUtil.genUUIDString()+"')";
//			String sql4 = "insert into t_authentication(userid,idcard) values('"+id+"','"+idcard+"')";
//			System.out.println(sql1);
//			DbUtils.execute(jdbc, sql1);
//			DbUtils.execute(jdbc, sql2);
//			DbUtils.execute(jdbc, sql3);
//			DbUtils.execute(jdbc, sql4);
//		}
//
//	  } catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
}
