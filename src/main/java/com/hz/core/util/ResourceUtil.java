package com.hz.core.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.util.StringUtils;


/**
 * 类Properties.java的实现描述：属性文件读取工具类
 * 
 */
public class ResourceUtil {
    private static ResourceBundle bundle;

    private static ResourceBundle instance() {
        return instance("messageResource");
    }

    private static ResourceBundle instance(String fileName) {
        return ResourceBundle.getBundle(fileName, new Locale("zh", "CN"));
    }

    /**
     * 根据key获取value值
     * 
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        if (null == bundle) {
            bundle = instance();
        }

        if (!StringUtils.isEmpty(key)) {
            return bundle.getString(key);
        }
        return null;
    }
    
    public static void main(String[] args) {
        String property = ResourceUtil.getProperty("hello");
        System.out.println(property);
    }
}
