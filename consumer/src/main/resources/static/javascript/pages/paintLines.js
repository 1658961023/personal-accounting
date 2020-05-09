$(function () {
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#test', //指定元素
            type: 'month',
            value: new Date(),
            btns: ['now','confirm'],
            done: function (value){
                drawLine(income_line,value,0)
            }
        });
        //执行一个laydate实例
        laydate.render({
            elem: '#test2', //指定元素
            type: 'month',
            value: new Date(),
            btns: ['now','confirm'],
            done: function (value){
                drawLine(outcome_line,value,1)
            }
        });
    });
    var income_line = echarts.init(document.getElementById("income_line"),'light');
    var outcome_line = echarts.init(document.getElementById("outcome_line"),'light');
    drawLine(income_line, "", 0);
    drawLine(outcome_line, "", 1);
});

function drawLine(chart,month, budgetType) {
    $.ajax({
        url: "/getDataForChart",
        method: "get",
        data: {"month": month, "budgetType": budgetType,"chartType":"line"},
        success: function (data) {
            var option = null;
            if(data.length===0){
                chart.clear();
                option={
                    title: {
                        text: '暂无数据',
                        x: 'center',
                        y: 'center'
                    }
                }
            }else{
                chart.clear();
                var dates= [];
                var series=[];
                var legendData=[];
                var today;
                var days;
                if(month===null||month===''){
                    today = new Date(new Date().getFullYear(),new Date().getMonth()+1,new Date().getDate());
                    days = new Date(new Date().getFullYear(),new Date().getMonth()+1,0).getDate();
                }else {
                    today = new Date(month.substr(0,3),month.substr(5,2),new Date().getDate());
                    days = new Date(month.substr(0,3),month.substr(5,2),0).getDate();
                }

                var month2 = today.getMonth();
                month2 = month2 < 10 ? '0'+month2 : month2;
                var testStr = '';
                for(var i=0;i<data.length;i++){
                    if(testStr.indexOf(data[i].category)===-1) legendData.push(data[i].category);
                    testStr+=data[i].category;
                }
                for(var n=1;n<=days;n++){
                    dates.push(today.getFullYear()+'/'+month2+'/'+(n < 10 ? '0'+n : n));
                }
                for(var j=0;j<legendData.length;j++){
                    var datas= [];
                    for(var k=1;k<=days;k++){
                        for(var m=0;m<data.length;m++){
                            if(data[m].category===legendData[j] && data[m].date.substr(8,2)===(k < 10 ? '0'+k : ''+k)){
                                datas.push(data[m].amount);
                                break;
                            }
                            if(m===data.length-1) datas.push(0);
                        }
                    }
                    series.push({name:legendData[j],type:"line",data:datas});
                }

                option = {
                    title:{
                        text: (budgetType===0?month+"收入趋势":month+"支出趋势"),
                        left: 'center',
                        top: 20
                    },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: legendData
                },
                grid: {
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: dates
                },
                yAxis: {
                    type: 'value'
                },
                series: series
            };
        }
            chart.setOption(option);
        }
    });
}
