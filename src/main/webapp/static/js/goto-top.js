// JavaScript Document
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