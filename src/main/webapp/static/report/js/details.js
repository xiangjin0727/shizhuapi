/**
 * Created by Administrator on 2017/1/5 0005.
 */


    H5FullscreenPage.init({
        'type' : 1,
        'pageShow' : function(page){},
        'pageHide' : function(page){},
        'useShakeDevice' : {
            'speed' : 30,
            'callback' : function(page){}
        },
        'useParallax' : true,
        'useArrow' : true,
        'useAnimation' : true,
        'useMusic' : {
            'autoPlay': null,
            'loopPlay': false,
            'src': 'http://mat1.gtimg.com/news/2015/love/FadeAway.mp3'
        }
    });
//    $('.btn').on('click',function(){
//        $('.mask').show();
//    })

function thenceThen(timespan){
    var date1=new Date(timespan);
    var totalSecs=(new Date()-date1)/1000;
    var days=Math.floor(totalSecs/3600/24);
    var hours=Math.floor((totalSecs-days*24*3600)/3600);
    var mins=Math.floor((totalSecs-days*24*3600-hours*3600)/60);
    var secs=Math.floor((totalSecs-days*24*3600-hours*3600-mins*60));
    document.getElementById("thenceThen").innerHTML=days+"<span>天 </span>"+hours+"<span>时 </span>"+mins+"<span>分 </span>"+secs+"<span>秒</span>";
}
var clock;
window.onload=function(){
    clock=self.setInterval("thenceThen('2015/10/15')", 500);
}



var sexRatio = echarts.init(document.getElementById('main'));//男女生比例
// 指定图表的配置项和数据
//app.title = '环形图';男生女生比例
sexRatio1 = {
    backgroundColor : '#fff',
    textStyle:{
        color: '#666666',
        fontSize: 35
    },
    series: [
        {
            type:'pie',
            center:['18%','50%'],
            radius: ['90%', '80%'],
            label: {
                normal: {
                    position: 'center'
                }
            },
            data:[
                {value:114, name:'34%',itemStyle:{normal:{color:"#56a8e7"}}},
                {value:335, name:'男生',itemStyle:{normal:{color:'#f3f3f3'}}},
            ]
        },
        {
            type:'pie',
            center:['78%','50%'],
            radius: ['90%', '80%'],
            label: {
                normal: {
                    position: 'center'
                }
            },
            data:[
                {value:221, name:'66%',itemStyle:{normal:{color:"#f3f3f3"}}},
                {value:335, name:'女生',itemStyle:{normal:{color:'#f1705c'}}},
            ]
        }
    ]
};
sexRatio.setOption(sexRatio1);

