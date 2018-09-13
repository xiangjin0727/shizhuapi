package com.hz.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author nieyanhui
 * 2015.8.3
 */
public class IsObjectNullUtils {
	 /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj
     * @return
     */
    public static boolean is(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof Integer) {
            return (((Integer) obj) == 0);
        }
        if (obj instanceof String) {
            return ((String) obj).isEmpty();
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }

        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!is(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }
    
    public static boolean checkPwd(String pwd){
    	if(pwd.matches("[0-9A-Za-z\\,\\.\\;\\:\"\\'\\!\\@\\~\\#\\$\\%\\^\\&\\*\\(\\[\\\\/\\?\\>\\<\\+\\-\\_\\)]*")){
  		  return true;
  		}
    	return false;
    }

}
