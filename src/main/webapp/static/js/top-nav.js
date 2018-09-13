// JavaScript Document
$(".nav").hover(function(){
	$(this).find(".nav-list").show();
	$(this).find(".bt-line").show();
},function(){
	setTimeout(function(){
		$(".nav").find(".nav-list").hide();
		$(this).find(".bt-line").hide();
	},50)
})

var urlstr = location.href;

  //alert((urlstr + '/').indexOf($(this).attr('href')));

  var urlstatus=false;

  $(".hd-nav-r dl>dd>a").each(function () {

    if ((urlstr + '/').indexOf($(this).attr('href')) > -1&&$(this).attr('href')!='') {
      $(this).addClass('active'); urlstatus = true;

    } else {

      $(this).removeClass('active');

    }

  });