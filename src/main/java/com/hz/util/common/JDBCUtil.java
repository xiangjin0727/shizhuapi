package com.hz.util.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.hz.core.util.PropertiesLoader;

public class JDBCUtil {

    private static final PropertiesLoader property = new PropertiesLoader("config.properties");
    public static final String type = property.getProperty("jdbc.type");  
    public static final String driver = property.getProperty("jdbc.driver");  
    public static final String url = property.getProperty("jdbc.url");  
    public static final String user = property.getProperty("jdbc.username");  
    public static final String password = property.getProperty("jdbc.password");  
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
  
    public JDBCUtil(String sql) {  
        try {  
            Class.forName(driver);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
        } catch (Exception e) {  
            System.out.println("获取JDBC连接异常:"+e.getMessage());
            e.printStackTrace();  
        }  
    } 
    
    public JDBCUtil() {  
        try {  
            Class.forName(driver);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
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