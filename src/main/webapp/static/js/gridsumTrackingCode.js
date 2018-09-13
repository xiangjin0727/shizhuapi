if (typeof(LoadedFlag)=='undefined') {
	var LoadedFlag=1;
//<!-- Gridsum tracking code begin. -->  
	var _gsq = _gsq || [];
	    (function () {
	        var s = document.createElement('script');
	        s.type = 'text/javascript';
	        s.async = true;
	        s.src = (location.protocol == 'https:' ? 'https://ssl.' : 'http://static.') + 'gridsumdissector.com/js/Clients/GWD-002678-27912E/gs.js';
	        var    firstScript = document.getElementsByTagName('script')[0];
	        firstScript.parentNode.insertBefore(s, firstScript);
	    })();     
    
    
    function hideRealMobile(strMobile){
    	return strMobile.substr(0,3)+"****"+strMobile.substr(7);
    }
    function getDateTime(){
    	var d = new Date();
    	var year = d.getFullYear()+"";
    	var month = d.getMonth() + 1;
    	var date = d.getDate();
    	var day = d.getDay();
    	var Hours = d.getHours();
    	var Minutes = d.getMinutes();
    	var Seconds = d.getSeconds();
    	var c = year;
    	c = month<10 ? c + "0" + month : c + month;
    	c = date<10 ? c + "0" + date : c + date;
    	c = Hours<10 ? c + "0" + Hours : c + Hours;
    	c = Minutes<10 ? c + "0" + Minutes : c + Minutes;
    	c = Seconds<10 ? c + "0" + Seconds : c + Seconds;
    	return c;
    }
    function RndNum(){
    	return Math.floor(Math.random() * 999); ;
    }
    function getTimeAndRandom(){
    	return getDateTime()+RndNum();
    }

//<!--Gridsum tracking code end. -->
}