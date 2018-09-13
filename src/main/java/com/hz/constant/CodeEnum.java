package com.hz.constant;

/**
 * 返回客户端的编码
 * 
 * @author nieyanhui
 *
 */
public enum CodeEnum {
	/******************* nieyanhui *************************/
	/**
	 * 成功
	 */
	Success("0000", "成功"), 
	crm_check("8883", "联系人只能是汉字和字母且长度为2至10"), 
	mobile_error("8884", "邀请码格式不正确"), 
	invitationCode_error("8885", "输入的邀请码错误"),
	identifyCodeIsError("8887", "输入的图片验证码错误"),
	identifyCodeIsInvalid("8888", "您的图片验证码已失效,请重新获取"),
	YgNo_Exist("8889", "员工编号已经存在"),
	Sign_Processing("8890", "签章进行中"),
	identifyH5CodeIsError("8891", "请输入正确的图形验证码"), 
	ValidateH5code_error("8891",  "输入4位有效的验证码"),
	YGBH_CODE("8892","员工编号格式不正确"),
	
	time_short("8891","短信发送太频繁,请稍候再试"),
	
	qx_time("2000","访问次数已经超限，需要升级会员"),
	
	sms_sendSucess("8800","短信发送成功"),

	check_phone("8886", "手机号格式不正确"), CHECK_NAME("0000", "输入姓名格式不正确"),
	/**
	 * 失败
	 */
	Error_Num("0000", "余额不足"),

	/**
	 * 系统升级中
	 */
	Error_update("0000", "系统升级中"), 
	isUpdate("1111", "系统升级中"),

	Fail("0000", "网络超时，请稍后重试"), 
	replaySubmit("7777", "请勿重复提交"),
	/**
	 * 失败
	 */

	IdCard_Nomatch("0000", "认证失败,请稍后重试"),
	/**
	 * 失败
	 */
	Fail_CreateFuyou("0000", "富友已经开通，不用重复开通"),
	/**
	 * 失败
	 */
	Fail_UnCreateFuyou("0000", "信息输入错误,请检查输入信息是否正确"),
	/**
	 * 用户名存在
	 */
	Exist_Name("0078", "用户名已存在,请重新输入"),
	
	
	UPLoginError("1004", "登录的用户名或密码不正确"),

	/**
	 * 已满额,请出借其他理财产品
	 */
	Yet_Used("0091", "该理财产品已满额,请出借其他理财产品"),

	/**
	 * 恭喜您，出借请求已成功受理，请耐心等待
	 */
	Invest_YetHandle("0001", "恭喜您，出借请求已成功受理，请耐心等待"),

	/**
	 * 失败
	 */
	Fail_CreateCrm("0000", "crm开通失败"),
	/**
	 * 失败
	 */
	Fail_Investment("0000", "出借金额不能大于剩余可出借金额"),

	Fail_Amount("0000", "剩余可出借金额不可小于100元"),
	/**
	 * 失败
	 */
	Fail_PwdError("0000", "密码必须为数字和小写字母混排"),
	/**
	 * 失败
	 */
	Fail_OpenedCrm("0000", "crm已经开通,不用重复开通"),
	/**
	 * 失败
	 */
	Fail_IdCard("0000", "开通失败"),
	/**
	 * 失败
	 */
	Fail_ErrTradePwd("0000", "交易密码错误"),
	/*
	 * 登录超时
	 */
	LoginTimeOut("1002", "登录超时"),

	/**
	 * 联系程序管理员
	 */
	sorryUser("1001", "请联系管理员"),
	/**
	 * 开通富友失败
	 */
	Fail_openFY("0000", "输入信息有误，请检查输入信息是否正确"),
	/**
	 * 新旧密码不能相同
	 */
	OldEqualNewPwd("1002", "新旧密码不能相同"),

	/**
	 * 修改登录密码失败
	 */
	UpLoginPwdFail("1005", "修改登录密码失败"),
	/**
	 * 原密码不正确
	 */
	OldFailPwd("1004", "原密码不正确"),

	/**
	 * 请输入6到到16位的交易密码
	 */
	AccountPwdFailLong("1005", "请输入6到到16位的交易密码"),

	/**
	 * 用户不存在 请重新登录
	 */
	UserLoginAgain("1006", "用户不存在 请重新登录"),

	/**
	 * 手机验证码有误
	 */
	Validatecode_error("1007", "手机验证码错误"),

	/**
	 * 此手机号已被注册
	 */
	Reg_existuser("1008", "此手机号已被注册"),

	/**
	 * 注册失败
	 */
	Reg_failure("1009", "注册失败"),

	/**
	 * userToken为空
	 */
	User_Token_Null("1010", "userToken为空"),
	
	
	CzLoginPwdFail("1003", "重置登录密码失败"),

	/**
	 * 获取用户信息失败,请重新登录
	 */
	User_Null("1011", "获取用户信息失败,请重新登录"),

	/**
	 * 交易密码错误
	 */
	Trade_Pwd_Error("1012", "交易密码错误"),

	/**
	 * 余额不足 无法投标
	 */
	Funds_Not_Full("1013", "余额不足 无法投标"),

	/**
	 * 出借失败,请稍后重试!
	 */
	Invest_Fail("1014", "出借失败,请稍后重试"),

	/**
	 * 生成出借失败
	 */
	Generate_Invest_Fail("1015", "生成出借失败"),

	/**
	 * 资金冻结失败
	 */
	Amount_Freeze_Fail("1016", "资金冻结失败"),

	/**
	 * 没有绑定银行卡
	 */
	Bind_User_Bank("1017", "请先绑定银行卡"),

	/**
	 * 出借额度超限
	 */
	Invest_Amount_ToBig("1018", "出借额度超限"),

	/**
	 * 未开通富友
	 */
	No_Have_FuYou("1019", "请先开通富友"),
	
	/**
	 * 已开通富友
	 */
	Yes_Have_FuYou("1014", "富友已开通，不能重复开通"),

	/**
	 * 邮箱已经存在
	 */
	User_Email_Exist("1020", "邮箱已经存在"),

	/**
	 * 交易密码和登录密码不能相同
	 */
	TradePwd_Equal_LoginPwd("1021", "交易密码和登录密码不能相同"),

	/**
	 * 理财产品不存在
	 */
	Finance_Pro_Not_Exist("1022", "理财产品不存在"),

	/**
	 * 没有开通账户信息Account
	 */
	User_No_Account("1023", "没有开通账户信息"),

	/**
	 * 成功预约该产品
	 */
	Finance_Pro_Success_Sail("0001", "恭喜您，您的出借请求已成功受理，请耐心等待"),

	/**
	 * 恭喜您，出借成功
	 */
	Finance_Pro_Success_Invest("0001", "恭喜您，您的出借请求已成功受理，请耐心等待"),

	/**
	 * 登录密码格式错误
	 */
	Login_Pwd_Format_Error("1024", "登录密码格式错误,只允许字母、数字、常用英文标点或者三者任意组合"),

	/**
	 * 邮箱格式不正确,请重新输入!
	 */
	Email_Format_Error("1025", "邮箱格式不正确,请重新输入!"),

	/**
	 * 验证码发送失败,请重新输入邮箱号!
	 */
	Email_Send_Error("1026", "验证码发送失败,请重新输入邮箱并检查邮箱是否正确!"),

	/**
	 * 邮箱验证码有误
	 */
	Validate_Email_Code_Error("1027", "邮箱验证码错误"),

	/**
	 * 身份证已认证
	 */
	IdCard_Have("50", "身份证已认证"),
	/**
	 * 手机号不存在
	 */

	/**
	 * 数据格式不正确
	 */
	YQ_CODE("1055", "推荐码错误，请重新输入"),
	/**
	 * 手机号不存在
	 */

	/**
	 * 修改用户名
	 */
	Update_Name("1028", "修改用户名失败"),

	/******************* nieyanhui *************************/

	// ---------------add by luwei-----
	/**
	 * 登录用户不存在
	 */
	User_Not_Exist("7001", "登录用户名不存在"),

	Password_Error("7002", "密码错误"),
	User_Locked("7003", "用户锁定"),

	// ---------------add by luwei-----

	Phone_Not_Exist("1010", "手机号不存在"),

	// ---------------add by xuchenglin-----
	User_isNotNewHand("7005", "您已进行过出借，不能参与新手专享计划"), 
	User_NewHand_Exception("7006", "登录用户名判断是否新手异常"),
	Borrow_isNotLogin("7007", "请用出借用户登录"),
	Coupon_isNotUserd("7008","优惠卷已被占用"),
	Operating_Frequency("0000","您的操作过于频繁，请稍后重试");

	private String index;
	private String name;

	CodeEnum(String index, String name) {
		this.index = index;
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	// 构造函数，枚举类型只能为私有
	private CodeEnum(String index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return String.valueOf(this.index);
	}
}
