package com.hz.util.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DbUtils {

	 public static boolean checkExsit(JDBCUtilUser jdbc,String sql,int type) throws Exception {
	        boolean ret = false;
	        Connection con = jdbc.conn;
	        if(type==1){
	        	 con = jdbc.conn1;
	        }else if(type==2){
	        	con = jdbc.conn2;
	        }
	        try {
	            ret = checkExsit(sql, con);
	        } catch (Exception e) {
	            throw e;
	        } 
	        return ret;
	    }
	
	 public static boolean checkExsit(JDBCUtil jdbc,String sql) throws Exception {
	        boolean ret = false;
	        Connection con = jdbc.conn;
	        try {
	            ret = checkExsit(sql, con);
	        } catch (Exception e) {
	            throw e;
	        } 
	        return ret;
	    }

	    public static boolean checkExsit(String sql, Connection con) throws Exception {
	        boolean ret = false;
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        if (rs.next()) ret = true;
	        rs.close();
	        stmt.close();
	        return ret;
	    }
	
	    public static ArrayList getValues(JDBCUtilUser jdbc,String sql,int type) throws Exception {
	        ArrayList values = new ArrayList();
	        Connection con = jdbc.conn;
	        if(type==1){
	        	 con = jdbc.conn1;
	        }else if(type==2){
	        	con = jdbc.conn2;
	        }
	        try {
	            values = getValues(sql, con);
	        } catch (Exception e) {
	            throw e;
	        }
	        return values;
	    }
	    public static String getValue(JDBCUtilUser jdbc,String sql,int type) throws Exception {
	        String ret = null;
	        Connection con = jdbc.conn;
	        if(type==1){
	        	 con = jdbc.conn1;
	        }else if(type==2){
	        	con = jdbc.conn2;
	        }
	        try {
	            ret = getValue(sql, con);
	        } catch (Exception e) {
	            throw e;
	        } 
	        return ret;
	    } 
    public static String getValue(JDBCUtil jdbc,String sql) throws Exception {
        String ret = null;
        Connection con = jdbc.conn;
        try {
            ret = getValue(sql, con);
        } catch (Exception e) {
            throw e;
        } 
        return ret;
    }

    public static String getValue(String sql, Connection con) throws Exception {
        String ret = null;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) ret = rs.getString(1);
        rs.close();
        stmt.close();
        return ret;
    }

    public static ArrayList getValues(JDBCUtil jdbc,String sql) throws Exception {
        ArrayList values = new ArrayList();
        Connection con = jdbc.conn;
        try {
            values = getValues(sql, con);
        } catch (Exception e) {
            throw e;
        }
        return values;
    }

    public static ArrayList getValues(String sql, Connection con) throws Exception {
        ArrayList values = new ArrayList();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) values.add(rs.getString(1));
        rs.close();
        stmt.close();
        return values;
    }

    public static String[] getValueArray(JDBCUtil jdbc,String sql) throws Exception {
        ArrayList list = DbUtils.getValues(jdbc,sql);
        String ret[] = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = (String) list.get(i);
        }
        return ret;
    }

    public static String[] getValueArray(String sql, Connection con) throws Exception {
        ArrayList list = DbUtils.getValues(sql, con);
        String ret[] = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = (String) list.get(i);
        }
        return ret;
    }

    public static String[][] getResultSetArray(JDBCUtilUser jdbc,String sql,int type) throws Exception {
    	Connection con = jdbc.conn;
        if(type==1){
        	 con = jdbc.conn1;
        }else if(type==2){
        	con = jdbc.conn2;
        }
        String[][] ret = null;
        try {
            ret = getResultSetArray(sql, con);
        } catch (Exception e) {
            throw e;
        } 
        return ret;
    }

  
    public static String[][] getResultSetArray(JDBCUtil jdbc,String sql) throws Exception {
        Connection con = jdbc.conn;//
        String[][] ret = null;
        try {
            ret = getResultSetArray(sql, con);
        } catch (Exception e) {
            throw e;
        } 
        return ret;
    }
    
    public static int execute(JDBCUtilUser jdbc,String sql,int type) throws Exception {
        int ret = 0;
        Connection con = jdbc.conn;
        if(type==1){
        	 con = jdbc.conn1;
        }else if(type==2){
        	con = jdbc.conn2;
        }
        try {
            ret = execute(sql, con);
        } catch (Exception e) {
            throw e;
        } 
        return ret;
    }
    
    public static int execute(JDBCUtil jdbc,String sql) throws Exception {
        int ret = 0;
        Connection con = jdbc.conn;
        try {
            ret = execute(sql, con);
        } catch (Exception e) {
            throw e;
        } 
        return ret;
    }

    public static int execute(String sql, Connection con) throws Exception {
        int ret = 0;
        Statement stmt = con.createStatement();
        ret = stmt.executeUpdate(sql);
        stmt.close();
        return ret;
    }
    public static String[][] getResultSetArray(String sql, Connection con) throws Exception {
        ArrayList values = new ArrayList();
        String[][] ret = null;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        int cols = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            String[] row = new String[cols];
            for (int i = 0; i < cols; i++) {
                row[i] = rs.getString(i + 1);
            }
            values.add(row);
        }
        ret = new String[values.size()][cols];
        for (int i = 0; i < values.size(); i++) {
            String[] row = (String[]) (values.get(i));
            for (int j = 0; j < cols; j++) {
                ret[i][j] = row[j];
            }
        }
        rs.close();
        stmt.close();
        return ret;
    }

    public static ResultSet executeQuery(String sql, Connection con) throws Exception {
        ResultSet rs = con.createStatement().executeQuery(sql);
        return rs;
    }

}
