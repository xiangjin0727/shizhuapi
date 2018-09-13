package com.hz.util.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.hz.core.util.PropertiesLoader;

public class JDBCUtilUser {

    public static final String driver = "com.mysql.jdbc.Driver";  
    public static final String url = "jdbc:mysql://10.20.200.220:3306/hzcf_bak?useUnicode=true&characterEncoding=utf-8";  
    public static final String user = "admin";  
    public static final String password = "123456";  
    public static final String url1 = "jdbc:mysql://10.20.200.220:3306/hzcf01?useUnicode=true&characterEncoding=utf-8";  
    
    public static final String url2 = "jdbc:mysql://10.20.200.220:3306/hzcf?useUnicode=true&characterEncoding=utf-8";  
  
    public Connection conn = null;  
    public Connection conn1 = null;  
    public Connection conn2 = null;  
    public PreparedStatement pst = null;  
    public PreparedStatement pst1 = null;  
    public PreparedStatement pst2 = null;  
  
    public JDBCUtilUser(String sql) {  
        try {  
            Class.forName(driver);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            conn1 = DriverManager.getConnection(url1, user, password);//获取连接  
            conn2 = DriverManager.getConnection(url2, user, password);//获取连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
            pst1 = conn1.prepareStatement(sql);//准备执行语句  
            pst2 = conn2.prepareStatement(sql);//准备执行语句  
        } catch (Exception e) {  
            System.out.println("获取JDBC连接异常:"+e.getMessage());
            e.printStackTrace();  
        }  
    } 
    
    public JDBCUtilUser() {  
        try {  
            Class.forName(driver);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接
            
        } catch (Exception e) {  
            System.out.println("获取JDBC连接异常:"+e.getMessage());
            e.printStackTrace();  
        }  
    }
    
    public JDBCUtilUser(String str,String str1) {  
        try {  
            Class.forName(driver);//指定连接类型  
            conn1 = DriverManager.getConnection(url1, user, password);//获取连接  
        } catch (Exception e) {  
            System.out.println("获取JDBC连接异常:"+e.getMessage());
            e.printStackTrace();  
        }  
    }
    public JDBCUtilUser(String str,String str1,String str3) {  
    	try {  
    		Class.forName(driver);//指定连接类型  
    		conn2 = DriverManager.getConnection(url2, user, password);//获取连接  
    	} catch (Exception e) {  
    		System.out.println("获取JDBC连接异常:"+e.getMessage());
    		e.printStackTrace();  
    	}  
    }
   

    /**
     * 释放连接
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void releaseDB(Connection connection,PreparedStatement statement, ResultSet resultSet) {  
        if (resultSet != null) {  
            try {  
                resultSet.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        if (statement != null) {  
            try {  
                statement.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        if (connection != null) {  
            try {  
                connection.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
}  