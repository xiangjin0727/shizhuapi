package com.hz.app.api;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by liuwei on 2015/7/23.
 */
public class InvestPlan {
    /**
     * 生成回款计划
     *
     */
    public List<Map<String, Object>> produceInvestPlan(Date startTime,BigDecimal rate,BigDecimal investAmount,int closeTime,int repayStyle) {
        Map<String, Object> invBillplanParams = new HashMap<String, Object>();
        List<Map<String, Object>> invBillplanList = new ArrayList<Map<String, Object>>();
        Date sDate;
        //invBillplanParams.put("investId", pageParams.get("investId"));
        int m=closeTime;
        Calendar cd = Calendar.getInstance();
        cd.setTime(startTime);
        int day=startTime.getDate();
        int day2=cd.getActualMaximum(Calendar.DAY_OF_MONTH)-cd.get(Calendar.DAY_OF_MONTH)+1;
        int capiMonth;
        BigDecimal sinte;//应收利息
        BigDecimal stcapi ;//应收本金
        // 月利通系列
       if(repayStyle==2 ) {
            //1到19号的下个月1号还款，19号以后的下个月还款
            if(day>1&&day<=19)
                    m++;
            for (int index = 1; index <=m; index++) {
                invBillplanParams = new HashMap<String, Object>();
                invBillplanParams.put("billNum", index);
                if(day>=1&&day<=19) {
                    capiMonth=startTime.getMonth()+index;
                    if(capiMonth>12)
                        capiMonth=capiMonth-12;

                    if(index==m)//最后一个月
                    {
                        if(day==1)
                            sinte=rate.multiply(investAmount).divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP);
                        else {
                            int days=getLastDay(startTime,closeTime)-1;
                            sinte = rate.multiply(new BigDecimal(days)).multiply(investAmount).divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP);
                        }

                    }
                    else {

                        if(day==1||index!=1)
                            sinte=rate.multiply(investAmount).divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP);
                        else {
                            sinte = rate.multiply(new BigDecimal(day2)).multiply(investAmount).divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP);
                        }


                    }
                    cd.add(Calendar.MONTH, 1);//增加一个月
                    cd.set(Calendar.DATE, 1);
                }
                else {
                    capiMonth = startTime.getMonth() + index+1;
                    if (capiMonth > 12)
                        capiMonth = capiMonth - 12;

                    if (index == m)//最后一个月
                    {
                        if (day == 1)
                            sinte = rate.multiply(investAmount).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
                        else {
                            int days = getLastDay(startTime, closeTime)-1;
                            sinte = rate.multiply(new BigDecimal(days)).multiply(investAmount).divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP);
                        }

                    } else {

                        if(day==1||index!=1)

                            sinte = rate.multiply(investAmount).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
                        else {
                            sinte = rate.multiply(investAmount).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP).add(rate.multiply(new BigDecimal(day2)).multiply(investAmount).divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP));
                        }


                    }
                    if(index==1)
                        cd.add(Calendar.MONTH, 2);//第一次增加两个月
                    else
                        cd.add(Calendar.MONTH, 1);//增加1个月
                    cd.set(Calendar.DATE, 1);
                }
                sDate=cd.getTime();
                //利息月份
                invBillplanParams.put("capiMonth", capiMonth );
                // 应收日期
                invBillplanParams.put("sDate", sDate);

                invBillplanParams.put("sinte", sinte);

                if(index==m) {
                	invBillplanParams.put("stcapi", investAmount);
                } else {
                	invBillplanParams.put("stcapi", 0);
                }

                invBillplanList.add(invBillplanParams);
            }

        } else {

           invBillplanParams.put("billNum", 1);
           
           Calendar c = Calendar.getInstance();
           c.setTime(startTime);
           c.add(Calendar.MONTH,closeTime);
           c.add(Calendar.DATE,-1);
           sDate=c.getTime();
           sinte = rate.multiply(investAmount).multiply(new BigDecimal(closeTime)).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
           // 应收日期
           invBillplanParams.put("sDate", sDate);
           // 应收利息
           invBillplanParams.put("sinte", sinte);
           	// 应收本金
           invBillplanParams.put("stcapi", investAmount);
           // 添加默认值
           invBillplanParams.put("capiMonth", null);

           invBillplanList.add(invBillplanParams);
        }
        for(int i=0;i<invBillplanList.size();i++)
        {
            System.out.println("billNum:"+invBillplanList.get(i).get("billNum"));
            System.out.println("capiMonth:"+invBillplanList.get(i).get("capiMonth"));
            System.out.println("sDate:"+invBillplanList.get(i).get("sDate"));
            System.out.println("sinte:"+invBillplanList.get(i).get("sinte"));
            System.out.println("stcapi:"+invBillplanList.get(i).get("stcapi"));
        }
        return invBillplanList;
    }

    private  int getLastDay(Date startTime,int month)
    {
        Calendar cd = Calendar.getInstance();
        cd.setTime(startTime);
        cd.add(Calendar.MONTH,month);
        return cd.getTime().getDate();
    }

    /*@Test
    public void testBatchInsertInvestBillplans() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date startTime=sdf.parse("2015-07-18");
        BigDecimal rate=new BigDecimal("0.12");
        BigDecimal investAmount=new BigDecimal("10000");
        int closeTime=12;
        int repayStyle=2;
        batchInsertInvestBillplans(startTime, rate,investAmount, closeTime,repayStyle);


    }*/

}
