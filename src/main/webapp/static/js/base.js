// JavaScript Document
$(function (){
	//账户中心左侧 nav
	$(".c-navlist li>a").click(function(){

		if($(this).next("div").is(":visible"))
		{
			$(this).next("div").slideUp(300);
			$(this).css({"color":"#585858"}).removeClass('active').parent().css({"background":"#f7f7f7"});
		}else{
			$(".c-navlist li>a").css({"color":"#585858"}).removeClass('active').parent().css({"background":"#f7f7f7"});
			$( ".c-navlist div").slideUp(300);
			$(this).next("div").slideDown(300);
			$(this).css({"color":"#fff"}).addClass('active').parent().css({"background":"#57c4f3","color":"#fff"});
		}
	});

	$(".c-navlist li>div>a").click(function(){
		$(this).css({"background":"#f7f7f7","color":"#189ed8"}).siblings().css({"background":"#ffffff","color":"#585858"}).parent().parent().siblings().find("div").find("a").css({"background":"#fff","color":"#585858"})
	});

	//温馨提示
	$('.bank-c-btn').click(function(){
		$('.bank-change').hide();
		$('.bank-change-cont').show();
	})
	$(document).ready(function(){
		$('.wx-ts').css('left',$(window).width()/2-448/2);
	})
	$('.commit-btn').click(function(){
		$('.zhe-bg').show();
		$('.wx-ts').show();
	})
	$('.wx-ts-canle').click(function(){
		$('.zhe-bg').hide();
		$('.wx-ts').hide();
	})

});

//关于我们 左侧js
$(".us-navlist li>a").click(function(){
	if($(this).next("div").is(":visible"))
		{	
			$(this).next("div").slideUp(300);
			$(this).css({"color":"#000"}).parent().css({"background":"#fff"});
	     }else{ 
		    $(this).next("div").slideDown(300);
		    $(this).css({"background":"url(../images/us-navbg.png) left no-repeat","color":"#000"});
		}
	})
	
	$(".us-navlist li>div>a").click(function(){
		$(this).css({"background":"#f7f7f7","color":"#189ed8"}).siblings().css({"color":"#989898"}).parent().prev('a').addClass('active')
	 })
	
	$(".us-navlist li a").click(function(){
		$(this).css({"background":"url(../images/us-navbg.png) left center no-repeat","color":"#189ed8"})	;
	})
	
  var urlstatus=false;

  $(".us-navlist li>div>a").each(function () {

    if ((urlstr + '/').indexOf($(this).attr('href')) > -1&&$(this).attr('href')!='') {
      $(this).parent("div").show();
       $(this).parent().prev('a').addClass('active');
      $(this).addClass('active'); urlstatus = true;

    } else {

      $(this).removeClass('active');

    }

  });

//账户中心 tabs
$(document).ready(function(){

	$('.charge-list').addClass('active');
	$('.charge-list2').click(function(){
		$(this).addClass('active');
		$('.charge-list').removeClass('active');
		$('.charge-cont').hide();
		$('.charge-cont2').show();
	})
	$('.charge-list').click(function(){
		$(this).addClass('active');
		$('.charge-list2').removeClass('active');
		$('.charge-cont2').hide();
		$('.charge-cont').show();
	})
})



//登录框验证
$(document).ready(function(){
	$('.lo-bar-icon2').val()


	$('.lo-bar-icon1').focus(function(){
		$('.ts-cont').hide();
	})
	$('.lo-bar-icon2').focus(function(){
		$('.ts-cont').hide();
	})
})
//登录框验证结束





//nav
$(".nav").hover(function(){
	$(this).find(".nav-list").show();
	$(this).find(".bt-line").show();
},function(){
	setTimeout(function(){
		$(".nav").find(".nav-list").hide();
		$(this).find(".bt-line").hide();
	},50)
})
//nav结束






//客服
$(document).ready(function(){
	$(".kefu").css('top','$(window).height()/2-48/2');
	$(".kefu-qq").css('top','$(window).height()/2');
	//点击
	$(".kefu").click(function(){
		$(".kefu-qq").toggle(300);
		$(".kefu").fadeOut(100);
	})
})
//客服结束






//placehoder 兼容
$(function(){
	if(!placeholderSupport()){   // 判断浏览器是否支持 placeholder
		$('[placeholder]').focus(function() {
			var input = $(this);
			if (input.val() == input.attr('placeholder')) {
				input.val('');
				input.removeClass('placeholder');
			}
		}).blur(function() {
			var input = $(this);
			if (input.val() == '' || input.val() == input.attr('placeholder')) {
				input.addClass('placeholder');
				input.val(input.attr('placeholder'));
			}
		}).blur();
	};
})
function placeholderSupport() {
	return 'placeholder' in document.createElement('input');
}






//返回顶部
function goTopEx() {
	var obj = document.getElementById("zhi-top");
	function getScrollTop() {
		return document.documentElement.scrollTop + document.body.scrollTop;
	}
	function setScrollTop(value) {
		if (document.documentElement.scrollTop) {
			document.documentElement.scrollTop = value;
		} else {
			document.body.scrollTop = value;
		}
	}
	window.onscroll = function() {
		getScrollTop() > 0 ? obj.style.display = "": obj.style.display = "none";
	}
	obj.onclick = function() {
		var goTop = setInterval(scrollMove, 10);
		function scrollMove() {
			setScrollTop(getScrollTop() / 1.1);
			if (getScrollTop() < 1) clearInterval(goTop);
		}
	}
}



//页面100%高度显示
$(document).ready(function(){
	$('.midle-box').height($(window).height()-424);
})

//登录页面 验证
$(document).ready(function(){
	$('.login-btn').click(function(){
		//$('.ts-cont1').show();
	})
})


//发送验证码
var wait=5;
$("#btn").attr("disabled",false);

function time(o) {
	if (wait == 0) {
		o.removeAttribute("disabled");
		o.value="获取验证码";
		o.style.backgroundColor = "#fff";
		wait = 5;
	} else {
		o.setAttribute("disabled", true);
		o.value="重新发送(" + wait + ")";
		o.style.backgroundColor = "#f6f5f5";
		wait--;
		setTimeout(function() {
				time(o)
			},
			1000);

	}
}







