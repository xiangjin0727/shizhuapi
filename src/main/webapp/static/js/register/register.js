/**
 * 注册用js
 */

var InterValObj; // timer变量，控制时间
var count = 30; // 间隔函数，1秒执行
var curCount;// 当前剩余秒数

$(document).ready(function() {
	$("#mobile").focus().select();
	$("#mobile").blur(function() {
		 if ($("#mobile").val()!=""&&!isMobile($("#mobile").val())) {
			$("#errorInfo").text('手机号格式不正确');
			$("#errorInfo").show();
		} else {
			$("#errorInfo").text('');
			$("#errorInfo").hide();
			var mobile  = $("#mobile").val();
			$.post(ctx+'/reg/existmobile',{mobile:mobile},
			function(data){
				if(data.success==false){
					$("#sendCode").attr("disabled", "disabled");
					$("#errorInfo").html(data.message);
					$("#errorInfo").show();
				}else{
					$("#sendCode").removeAttr("disabled");// 启用按钮
					//$("#sendCode").attr("enabled", "enabled");
					$("#errorInfo").hide();
				}
				},'json');
		}
	});


	$("#rePassword").blur(function() {
		var fir = $.trim($("#tbPassword").val());
		var re = $.trim($("#rePassword").val());
		if (compareString(fir, re)) {
			$("#error_pwd").hide();
			
		} else {
			$("#error_pwd").show();
		}
	});

	$("#sendCode").click(function() {
		if ($("#mobile").val()==""||!isMobile($("#mobile").val())) {
			$("#errorInfo").html('请输入手机号');
			$("#errorInfo").show();
		}else{
			curCount = count;
			// 设置button效果，开始计时
			$("#sendCode").attr("disabled", "disabled");
			$("#sendCode").val("重新发送(" + curCount + "秒)");
			InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次
			var mob = $("#mobile").val();
			if (isMobile($("#mobile").val())) {
				$.post(ctx+'/user/regsms', {
					mobile : mob
				}, function(data) {
					//alert(data);
					// alert(data.sms_send_success);
				}, 'json');
			} else {
				//alert("F");
			}
		}		
	});

	$("#register").click(function() {
			var mob = $("#mobile").val();
			var smsCode = $("#yzm1").val();
			if($("#mobile").val()==""){
				$("#errorInfo").text('手机号不能为空');
				$("#errorInfo").show();
			}else if(smsCode==""){
				$("#ysmerror").text('验证码不能为空');
				$("#ysmerror").show();
			}else{
				if($("#agreement").prop("checked")){
					if (isMobile($("#mobile").val())) {
						$.post(ctx + '/reg/validateSMSCode', {
							mobile : mob,smsCode:smsCode
						}, function(data) {
		                     if(data=='1'){
		                    	 $('#firstReg').attr("action", ctx+"/reg/second").submit();
		                     }else{
		                    	 $("#ysmerror").text('验证码不正确');
		         				 $("#ysmerror").show();
		                     }
						}, "json");
					} else {
						$("#mobile").focus().select();
						$("#errorInfo").show();
					}
				}else {
					$("#ysmerror").hide();
					alert("请同意《汇中网贷注册协议》");
				}
			}
	});

})

function isMobile(m) {
	var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[6-8]{1})||(17[0-0]{1})|(18[0-9]{1}))+\d{8})$/;
	if (!reg.test(m)) {
		return false;
	} else {
		return true;
	}
}

function compareString(str_1, str_2) {
	if (str_1 == str_2) {
		return true;
	} else {
		return false;
	}
}
function SetRemainTime() {
	if (curCount == 0) {
		window.clearInterval(InterValObj);// 停止计时器
		$("#sendCode").removeAttr("disabled");// 启用按钮
		$("#sendCode").val("重新发送验证码");
	} else {
		curCount--;
		$("#sendCode").val("重新发送(" + curCount + "秒)");
	}
}

$(document).ready(function() {
	$("#confirm").click(function() {
		var fir = $.trim($("#tbPassword").val());
		var re = $.trim($("#rePassword").val());
		if (fir == "") {
			$("#error_pwd").html("密码不能为空");
			$("#error_pwd").show();
			return;
		}
		if (compareString(fir, re)) {
			$("#error_pwd").hide();
		} else {
			$("#error_pwd").html("两次密码输入不一致");
			$("#error_pwd").show();
			return;
		}
		$("#error_pwd").text('');
		
		
		var flags = true;
		var userName = $("#userName").val();
		var reg = /^\w+$/;///[a-zA-Z]+(?=[0-9]+)|[0-9]+(?=[a-zA-Z]+)/g;  
		//alert(regUserName.test(userName));
		if (userName.length > 16) {
			$("#error_userName").html("用户名超长,不能超过16位");
			$("#error_userName").show();
			flags = false;
		}
		if(!reg.test(userName)){
			$("#error_userName").html("用户名由数字字母下划线组成,且介于4到16位之间");
			$("#error_userName").show();
			flags = false;
		}
		if(userName.length < 4){
			$("#error_userName").html("用户名不能为空且长度不能低于4位");
			$("#error_userName").show();
			flags = false;
	    }
		if(flags){
			$("#error_userName").hide();
			$.post(ctx+'/reg/existusername',{userName:userName},
					function(data){
						if(data.success==false){
							$("#error_userName").html(data.message);
							$("#error_userName").show();
						}else{
							$("#error_userName").hide();
							$('#regSecond').attr("action", ctx + "/reg/saveRegister").submit();
						}
						},'json');
		}
	});
});

$(document).ready(function() {
	$("#userName").focus().select();
	$("#userName").blur(function() {
		    var flags = true;
			var userName = $("#userName").val();
			var reg =  /^\w+$/;///[a-zA-Z]+(?=[0-9]+)|[0-9]+(?=[a-zA-Z]+)/g;  
			//alert(regUserName.test(userName));
			if (userName.length > 16) {
				$("#error_userName").html("用户名超长,不能超过16位");
				$("#error_userName").show();
				flags = false;
			}
			if(!reg.test(userName)){
				$("#error_userName").html("用户名由数字字母下划线组成,且介于4到16位之间");
				$("#error_userName").show();
				flags = false;
			}
			if(userName.length < 4){
				$("#error_userName").html("用户名长度不能为空且不能低于4位");
				$("#error_userName").show();
				flags = false;
		    }
			if(flags){
				$("#error_userName").hide();
				$.post(ctx+'/reg/existusername',{userName:userName},
						function(data){
							if(data.success==false){
								$("#error_userName").html(data.message);
								$("#error_userName").show();
							}else{
								$("#error_userName").hide();
							}
							},'json');
			}
		});
});
