//$(document).ready(function(){
//	$("#indexlogin").click(function(){
//		var loginname  = $("#loginname").val();
//		var password  = $("#password").val();
//		//cookexam();
//		$.post('user/login',{mobile:loginname,password:password},
//		function(data){
//			//alert(data.message)
//			if(data.success==false){
//				$("#dis").show(); 
//			}else{
//				window.location.href="index";
//			}
//		},'json');
//	})
//});
//
//$(document).ready(function() {
//	if ($("#rememberMe").prop("checked")==true) {
//		alert('ssssss');
//		$("#rememberMe").prop("checked", true);
//		$("#loginname").val($.cookie("loginname"));
//		$("#password").val($.cookie("password"));
//	}
//}); 
//
//function cookexam(){
//	if ($("#rememberMe").prop("checked")==true) {
//		$.cookie("rememberMe", "true", { expires: 7 }); // 存储一个带7天期限的 cookie
//		$.cookie("loginname", loginname, { expires: 7 }); // 存储一个带7天期限的 cookie
//		$.cookie("password", password, { expires: 7 }); // 存储一个带7天期限的 cookie
//		}else{
//		  alert('sss44sss');
//		$.cookie("rememberMe", "false", { expires: -1 });
//		$.cookie("loginname", '', { expires: -1 });
//		$.cookie("password", '', { expires: -1 });
//	}
//}
$(document).ready(function() {
	if ($.cookie("rmbUser") == "true") {
		$("#rememberMe").prop("checked", true);
		$("#loginname").val($.cookie("username"));
		$("#password").val($.cookie("password"));
	}
    $("#indexlogin1").click(function() {
    	var flag = true;
    	var username = $("#loginname").val();
    	var password = $("#password").val();
    	if (username == ""&&password=="") {
    		flag = false;
    		$("#dis").text("请输入用户名和密码");
    		$("#dis").show();
    	}else if (username == ""&&password != "") {
    		flag = false;
    		$("#dis").text("请输入用户名");
    		$("#dis").show();
    	}else if (username != ""&&password == "") {
    		flag = false;
    		$("#dis").text("请输入密码");
    		$("#dis").show();
    	}
		if (flag) {
			login();
		}
	});
});

// 记住用户名密码
function save() {
	if ($("#rememberMe").prop("checked")) {
		var username = $("#loginname").val();
		var password = $("#password").val();
		$.cookie("rmbUser", "true", {
			expires : 7
		}); // 存储一个带7天期限的cookie
		$.cookie("username", username, {
			expires : 7
		});
		$.cookie("password", password, {
			expires : 7
		});
	} else {
		$.cookie("rmbUser", "false", {
			expire : -1
		});
		$.cookie("username", "", {
			expires : -1
		});
		$.cookie("password", "", {
			expires : -1
		});
	}
};


function login() {
	$.ajax({
		type : "post",
		url : "user/login",
		data : {mobile : $("#loginname").val(),	password : $("#password").val()},
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$.cookie("username", $("#loginname").val(), {expires : 7});
				//addCookie("username", $("#loginname").val(), 0);
				save();
				//location.href = "/index.jsp";
				window.location.href="index";
			} else {
				$("#dis").text("用户名或密码错误，请重新登录！");
				$("#dis").show();
				return false;
			}

		}
	});
}