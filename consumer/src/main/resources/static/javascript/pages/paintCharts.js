$(function () {
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#test', //指定元素
            type: 'month',
            done: function (value){
                drawPie(income_pie,value,0)
            }
        });
        //执行一个laydate实例
        laydate.render({
            elem: '#test2', //指定元素
            type: 'month',
            done: function (value){
                drawPie(outcome_pie,value,1)
            }
        });
    });
    var income_pie = echarts.init(document.getElementById("income_pie"),'light');
    var outcome_pie = echarts.init(document.getElementById("outcome_pie"),'light');
    var income_line = echarts.init(document.getElementById("income_line"),'light');
    var outcome_line = echarts.init(document.getElementById("outcome_line"),'light');
    drawPie(income_pie, "", 0);
    drawPie(outcome_pie, "", 1);
    drawLine(income_line, "", 0);
    drawLine(outcome_line, "", 1);
});



function drawPie(chart, month, budgetType) {
    $.ajax({
        url: "/getDataForChart",
        method: "get",
        data: {"month": month, "budgetType": budgetType,"chartType":"pie"},
        success: function (data) {
            var datas = [];
            var legendData = [];
            for (var i = 0; i < data.length; i++) {
                legendData.push(data[i].category);
                datas.push({name: data[i].category, value: data[i].amount})
            }
            var option=null;
            if(data.length===0){
                chart.clear();
                option = {
                    title: {
                        text: '暂无数据',
                        x: 'center',
                        y: 'center'
                    }
                }
            }else{
                chart.clear();
                option = {
                    title:{
                        text:""
                    },
                    toolbox:{
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{b}: {c}￥ ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 10,
                        data: legendData
                    },
                    series: [
                        {
                            type: 'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                label: {
                                    show: true,
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            },
                            labelLine: {
                                show: false
                            },
                            data: datas
                        }
                    ]
                };
                }
            chart.setOption(option);
        }
    });
}

function drawLine(chart,month, budgetType) {
    $.ajax({
        url: "/getDataForChart",
        method: "get",
        data: {"month": month, "budgetType": budgetType,"chartType":"line"},
        success: function (data) {
            var dates= [];
            var series=[];
            var legendData=[];
            var today = new Date(2020,4,15);
            var days = new Date(2020,4,0).getDate();
            var month2 = today.getMonth();
            month2 = month2 < 10 ? '0'+month2 : month2;
            var testStr = '';
            for(var i=0;i<data.length;i++){
                if(testStr.indexOf(data[i].category)===-1) legendData.push(data[i].category);
                testStr+=data[i].category;
            }
            for(var n=1;n<days;n++){
                dates.push(today.getFullYear()+'/'+month2+'/'+(n < 10 ? '0'+n : n));
            }
            for(var j=0;j<legendData.length;j++){
                var datas= [];
                for(var k=1;k<days;k++){
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

            var option = {
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
            chart.setOption(option);
        }
    });
}
