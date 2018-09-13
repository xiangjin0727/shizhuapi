package com.hz.app.api;

/**
 * 0: 无需登陆，  1：必须登录
 * @author XJin
 *
 */
public enum ServletEnum {
	S0001("发送短信验证码","sms/doSms",0), // complete
	S0002("注册","user/register",0),     //complete
	S0003("忘记密码","user/forgetPassword",0), //complete
	S0004("修改密码","user/updatePassword",0),  //complete
	S0005("实名认证","user/authentication",1),  //complete
	S0006("登录","user/login",0),                //complete
	S0007("更换头像","user/changeTheAvatar",1),	
	S0008("我的收藏","userMyInformation/myCollection",1), //complete
	S0009("我的约看","userMyInformation/myAppointment",1), //complete
	S0010("我的预订","userMyInformation/myReservation",1), //complete
	S0011("我的订单-租赁订单","userMyInformation/myLeaseOrder",1), //complete
	S0012("我的订单-生活订单","userMyInformation/myLifeOrder",1),//complete  T
	S0013("我的评价-房屋评价","userMyInformation/myHousingEvaluation",1),//complete  T
	S0014("我的评价-保洁评价","userMyInformation/myCleaningEvaluation",1),//complete  T
	S0015("我的评价-维修评价 ","userMyInformation/myMaintenanceEvaluation",1),//complete  T
	S0016("我的评价-管家评价","userMyInformation/myHousekeeperEvaluation",1),
	S0017("我的会员","userMyInformation/myMembers",1), //complete  T
	S0018("获取积分兑换列表","userCoupon/getFractionalTransactionList",1), //complete
	S0019("积分兑换操作","userCoupon/convertibilityOperation",1),//complete 
	S0020("获取积分兑换记录","userCoupon/getConvertibilityRecord",1),//complete  T
	S0021("我的钱包","userMyInformation/myWallet",1),//complete  T
	S0022("我的优惠券","userMyInformation/myCoupon",1),//complete  T
	S0023("账单明细","order/getBillingDetails",1),//complete  T
	S0024("我的合同","userMyInformation/myContract",1),//complete  T
	S0025("待缴账单","order/getPendingBill",1),//complete  T
	S0026("获取我的管家列表","userMyInformation/myHousekeeperList",1),//complete  T
	S0027("智享维修-报修","order/doApplyForRepair",1),//complete  T
	S0028("投诉建议","evaluate/doComplaintProposal",1),//complete  T	
	S0029("退出登录","user/signOut",0), //complete
	S0030("修改昵称","user/updateNickname",1),  //complete
	S0031("获取个人信息","userMyInformation/getPersonalInformation",1),//complete  T
	S0032("更换绑定手机号","user/updateMobile",1), //complete
	S0033("获取房源列表 ","house/searchHouseList",0),//complete
	S0034("获取房源详情","house/getRoomDetails",0),//complete  T
	S0035("获取房源付款方式","house/getAccessToHousePayment",1),
	
	S0039("智能看房","payment/intelligentHouseLoad",1),//complete  T
	S0040("智能看房-支付","payment/intelligentHousePay",1),//complete  T
	S0041("立即签约","payment/contractImmediatelyLoad",1),//complete  T
	S0042("立即签约-支付","payment/contractImmediatelyPay",1),//complete  T
	
	S0044("投诉建议列表","evaluate/doComplaintProposalList",1),//complete  T	
	S0045("房屋评价列表","userMyInformation/housingEvaluationList",1),//complete  T
	S0046("保洁评价列表","userMyInformation/cleanlinessEvaluationList",1),//complete  T
	S0047("维修评价列表","userMyInformation/maintenanceEvaluationList",1),//complete  T	
	S0048("修改用户信息","user/updateUser",1), //complete
	S0049("获取信用纬度分值（信用总分=各维度信用分之和）","user/getCreditDimension",1),//complete  T
	S0050("获取看房违约记录","house/getHouseDefaultRecords",1),//complete  T
	S0051("获取租住违约记录","house/getRecordOfBreachOfContract",1),//complete  T
	S0052("获取生日特权房租抵用券","userCoupon/getBirthdayVoucher",1),//complete  T
	S0053("退押金(看房)","payment/ReturnTheDepositMoney",1),//complete  T
	S0054("获取合同详情","userMyInformation/myContractDetail",1),//complete  T
	S0055("获取合同下房租账单列表","order/getBillList",1),//complete  T
	S0056("获取账单详情","order/getDetailsOfTheBill",1),//complete  T
	S0057("支付账单金额","payment/doPaymentOfBill",1),//complete  T
	//S0058("代缴账单列表","order/getBillList_surrender",1),//complete  T
	S0059("房源详情，收藏房源 ","house/doCollectionOfHouses",1),//complete  T
	S0060("房源详情，取消收藏房源 ","house/doCancelCollectionOfHouses",1),//complete  T
	S0061("取消自主看房（取消约看） ","house/doCancelSeeHouse",1),//complete  T
	S0063("首页图片获取 ","house/banner",0),//complete  T

	S0064("自主看房看房时间列表 ","house/getAvailableTime",1),//complete  T
	
	S0065("房源详情中的付款方式 ","payment/getPaymentMethodList",0),//complete  T
	
	S0067("完成看房接口 ","house/finishTheHouse",1),//complete  T
	S0068("可维修项目 ","evaluate/getRepairableProject",0);//complete  T

	
	
	private String name;
	private String url;
	private int type;
	
	private ServletEnum(String name,String url,int type){
		this.name = name;
		this.url = url;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
