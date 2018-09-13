package com.hz.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ByteUtils {

	/**
	 * 执行对象序列化为byte数组
	 * @param obj Object对象
	 * @return
	 * @throws IOException 
	 */
	public static byte[] transObj2Byte(Object obj) {
		
		if( !(obj instanceof Serializable ) ){
		
			return null;
		}
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try{
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			
			return baos.toByteArray();
		}catch(Exception ex){
			return null;
		}finally{
		
			if ( null != baos ){
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
			
			if( null != oos ){
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	/**
	 * byte数组序列化为对象
	 * @param byteArray
	 * @return
	 */
	public static Object transByte2Obj(byte [] byteArray) {
		
		if( null == byteArray || byteArray.length == 0 ){
			return null;
		}
		ObjectInputStream ois = null;
		
		try{
			ois = new ObjectInputStream(new ByteArrayInputStream(byteArray));
			
			return ois.readObject();
		}catch(Exception ex){
			return null;
		} finally{
			
			if( null != ois ){
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}	
	
}
