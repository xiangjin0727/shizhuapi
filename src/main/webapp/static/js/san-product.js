// JavaScript Document

//散标搜索切换
$(document).ready(function(){
								$("#all").addClass("active");//文档默认的给定元素高亮
								$("#zb,#all,#sh,#hk,#yhq").click(function(){//选择4个要执行点击时间的元素
									$(".san-p-t li").removeClass("active");//先移除border下所有span的now
									$(this).addClass("active");//再为点击的元素(this)增加now
								});
								$("#all").addClass("active");//文档默认的给定元素高亮
								$("#month3,#all,#month6,#month12").click(function(){//选择4个要执行点击时间的元素
									$(".san-p-t2 li").removeClass("active");//先移除border下所有span的now
									$(this).addClass("active");//再为点击的元素(this)增加now
								});
								$("#all").addClass("active");//文档默认的给定元素高亮
								$("#dy,#all,#xy").click(function(){//选择4个要执行点击时间的元素
									$(".san-p-t3 li").removeClass("active");//先移除border下所有span的now
									$(this).addClass("active");//再为点击的元素(this)增加now
								});
							});