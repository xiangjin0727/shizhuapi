/**
 * Created by baikaishui on 2016/5/24.
 */
$(document).ready(function(){
    var h=document.body.clientHeight||document.documentElement.clientHeight;
    var w=document.body.clientWidth||document.documentElement.clientWidth;
    var oHzCAll=document.getElementById('hzCAll');
    var oHzCallA=oHzCAll.getElementsByTagName('a');
    var startX=0;
    var startZ=0;
    var startY=0;
    var zindex=1
    $('.hzCc').css({'width':w,'height':h});
    $('.hzCc a').css({'width':w,'height':h});
    $('.hzImgBg').css({'width':w,'height':h});
    $('.SevenPupop').css({'width':w,'height':h});
    $('.SevenPupopBg').css({'width':w,'height':h});


    $('.SevenClose').bind('click',function(){
        $(this).parent().css('display','none');
    });
    $('.seven1left').bind('click',function(){

        $('.SevenPupop').eq(($(this).index()-2)).css('display','block');
        //event.preventDefault();
    })

    $('.EightPupop').css({'width':w,'height':h});
    $('.EightPupopBg').css({'width':w,'height':h});
    $('.EightClose').bind('click',function(){
        $(this).parent().css('display','none');
    });
    $('.eight1left').bind('click',function(){

        $('.EightPupop').eq(($(this).index()-2)).css('display','block');
        // event.preventDefault();
    });


    for(var i=0; i<oHzCallA.length;i++){
        oHzCallA[i].index=i;
        oHzCallA[i].addEventListener('touchstart',function(event){//手指放上
           // event.preventDefault();
            startX = event.touches[0].pageY;
        },false);
        oHzCallA[i].addEventListener('touchmove',function hzBannerTouch(evnert){//手指移动
           event.preventDefault();
            startZ = event.touches[0].pageY;

        },false);
        oHzCallA[i].addEventListener('touchend', function(ev){ // 离开屏幕
        //   document.title=startX+"="+startZ+'Y='+startY;
            if(startY!=startZ){
                if(startX-startZ>30 && startZ!=0){
                   // console.log("向上")
                    if(zindex==oHzCallA.length){
                        zindex=0;
                    }
                    zindex++;

                     window.location.href='#page'+zindex;

                }
                else if(startX-startZ<0 && startZ!=0){
                   // console.log("向下")
                    if(zindex==1){
                        zindex=2;
                    }
                    zindex--;
                    window.location.href='#page'+zindex;

                }
            }
            startY=startZ;

        },false);
    }

});
