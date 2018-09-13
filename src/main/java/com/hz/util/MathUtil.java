package com.hz.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathUtil {
	public static double getTwoMathDou(double num){
		BigDecimal   b   =   new   BigDecimal(num);  
		double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		return f1;
	}
	
	
	public static String getFourMath(double num){
		DecimalFormat  df   = new DecimalFormat("######0.0000");  
		df.setMaximumFractionDigits(4);
		df.setGroupingSize(0);
		df.setRoundingMode(RoundingMode.FLOOR);
		return df.format(num);
	}
	
	public static String getTwoMath(double num){
		if((Object)num==null){
			num = 0.00;
		}
		DecimalFormat  df   = new DecimalFormat("######0.00");  
		df.setMaximumFractionDigits(2);
		df.setGroupingSize(0);
		df.setRoundingMode(RoundingMode.FLOOR);
		return df.format(num);
	}
	public static void main(String[] args) {
		System.out.println(getTwoMathDou(2660.09));
		System.out.println(String.valueOf(Math.round(Double.parseDouble(MathUtil.getTwoMath(12.99))))+".00");
        		
		
//		String twoMath = getTwoMath(0.001);
//		 BigDecimal b1 = new BigDecimal(twoMath);
//		 BigDecimal b2 = new BigDecimal(Double.toString(100d));
//		 System.out.println("twoMath="+twoMath);
//		System.out.println(b1.multiply(b2).setScale(2,BigDecimal.ROUND_DOWN));
//		System.out.println("String="+String.valueOf(b1.multiply(b2).setScale(2,BigDecimal.ROUND_DOWN)));
//		System.out.println(getTwoMathDou(0.008856));
//		double twoMathDou = getTwoMathDou(10000*(12.5/100/12)*18);
//		String d = String.valueOf(twoMathDou);
//		System.out.println(d.substring(0, d.indexOf(".")));
//		System.out.println(getTwoMathDou(10000*(12.5/100/12)*18));
		
		double incomeInfo=MathUtil.getTwoMathDou(192.87);
		System.out.println(incomeInfo);
		java.text.DecimalFormat format = new java.text.DecimalFormat("###,###,###,###,###");
		String incomeInfoStr=String.valueOf(incomeInfo).substring(0, String.valueOf(incomeInfo).indexOf("."));
		System.out.println("+++="+format.format(Double.parseDouble(incomeInfoStr)));;
	}
}
