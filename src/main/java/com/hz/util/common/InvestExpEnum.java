package com.hz.util.common;

public enum InvestExpEnum {
	
	E0001("E0001", "查询用户信息异常!"),
	E0002("E0002", "获取用户的出借信息失败, 请稍后重试!"),
	E0003("E0003", "您已进行过出借，不能参与新手专享计划!"),
	E0004("E0004", "获取出借用户信息失败, 请联系汇中网客服人员!"),
	E0005("E0005", "账号被锁定不能进行出借,请联系汇中网客服人员!"),
	E0006("E0006", "出借金额少于最小限额，请重新输入！"),
	E0007("E0007", "出借金额超出最大限额，请重新输入！"),
	E0008("E0008", "不是100的整数倍，请重新输入出借金额！"),
	E0009("E0009", "获取用户资金账户信息失败,请联系汇中网客服人员!"),
	E0010("E0010", "获取用户绑定银行卡信息失败,请先绑定银行卡!"),
	E0011("E0011", "获取用户出借产品信息失败,请联系汇中网客服人员!"),
	E0012("E0012", "出借参数验证失败, 请稍后重试!"),
	E0013("E0013", "可用余额不足, 请先进行充值!"),
	E0014("E0014", "出借失败, 请稍后重试!"),
	
	
	E0015("E0015", "恭喜您，出借请求已成功受理，请耐心等待!"),
	E0016("E0016", "该项目剩余金额不足，请重试！"),
	
	E0017("E0017", "出借金额超过标的剩余金额,请出借其他理财产品!"),
	E0018("E0018", "校验出借结果成功！");
	
	private String code;
	private String info;

	private InvestExpEnum(String code, String info) {
		this.code = code;
		this.info = info;
	}

	public static final InvestExpEnum getFromKey(String code) {
		for (InvestExpEnum e : InvestExpEnum.values()) {
			if (e.code.equals(code)) {
				return e;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	


	
}
