package com.hz.util.common;

import com.hz.core.util.StringUtil;

/***
 * @Description:状态数值转换
 * @author: lvwenchao
 * @version :1.0
 * @Note :
 * @<b>ProjectName:</b> hzdjr-mobile
 * @<b>PackageName:</b> com.hz.hzdjr.mobile.smart.emnue
 * @<b>ClassName:</b> StatusValue
 * @<b>Date:</b> 2017/8/15
 */
public enum StatusValue {
    //预约产品状态
    sv0("","",0),
    sv1("1","预约中",0),
    sv2("2","已取消",0),
    sv3("3","已加入",0),
    //出借方式
    lv0("","",1),
    PTTZ("PTTZ","普通投资",1),
    BXFT("BXFT","本息复投",1),
    BJFT("BJFT","本金复投",1),
    //优惠券类型
    cv0("","",2),
    fullDown("fullDown","满减劵",2),
    cash("cash","现金券",2),
    interest("interest","增益券",2),
    cashBack("cashBack","返现券",2),
    //其他
    dv0("","",3);

    private String status;

    private String value;

    private Integer type;

    public static StatusValue findStatusValue(String status){
        for (StatusValue s:StatusValue.values()) {
            if(StringUtil.requals(s.getStatus(),status))
            return s;
        }
        return null;
    }

    private StatusValue (String status,String value,Integer type){
        this.status=status;
        this.value=value;
        this.type=type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
