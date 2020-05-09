$(function () {
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#test', //指定元素
            type: 'month',
            btns: ['now','confirm'],
            value: new Date(),
            done: function (value){
                drawPie(income_pie,value,0)
            }
        });
        //执行一个laydate实例
        laydate.render({
            elem: '#test2', //指定元素
            type: 'month',
            btns: ['now','confirm'],
            value: new Date(),
            done: function (value){
                drawPie(outcome_pie,value,1)
            }
        });
    });
    var income_pie = echarts.init(document.getElementById("income_pie"),'light');
    var outcome_pie = echarts.init(document.getElementById("outcome_pie"),'light');
    drawPie(income_pie, "", 0);
    drawPie(outcome_pie, "", 1);
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
                            text: (budgetType===0?month+"收入概况":month+"支出概况"),
                            left: 'center',
                            top: 20
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

