package com.hz.core.framework.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class JedisCache {
	
	private static String ip;   
    private static String port;
    private static String password;
    
    static {   
        Properties prop = new Properties();   
        InputStream in = RedisCacheDao.class.getResourceAsStream("/redis.properties");
        try {   
            prop.load(in);   
            ip = prop.getProperty("redis.ip").trim();   
            port = prop.getProperty("redis.port").trim();
            password = prop.getProperty("redis.password").trim();
        } catch (IOException e) {   
            e.printStackTrace(); 
        }   
    }
    
    public static Jedis  getJedis(){
    	//连接redis
    	Jedis  redis = new Jedis (ip,Integer.parseInt(port)); 
        redis.auth("hzcf.com");//验证密码
        return redis;
    }
    
    
    public static void main(String[] args){
		// TODO Auto-generated method stub
    	Set<String> keys  = JedisCache.getJedis().keys("*");
		Iterator<String> it=keys.iterator() ;
        while(it.hasNext()){
            String key = it.next();
            System.out.println(key);
        }
	}

}
