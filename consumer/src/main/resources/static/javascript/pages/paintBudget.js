$(function () {
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#test', //指定元素
            type: 'month',
            value: new Date(),
            done: function (value) {
                drawPie(income_pie, value, 0)
            }
        });
        //执行一个laydate实例
        laydate.render({
            elem: '#test2', //指定元素
            type: 'month',
            value: new Date(),
            done: function (value) {
                drawPie(outcome_pie, value, 1)
            }
        });
    });
    var income_pie = echarts.init(document.getElementById("budget_pie"), 'light');
    var outcome_pie = echarts.init(document.getElementById("target_pie"), 'light');
    drawPie(income_pie, "", "budget");
    drawPie(outcome_pie, "", "target");
});


function drawPie(chart, month, budgetTarget) {
    $.ajax({
        url: "/getBudgetForChart",
        method: "get",
        data: {"month": month, "budgetTarget": budgetTarget},
        success: function (data) {
            var option = null;
            if (data.length === 0) {
                chart.clear();
                option = {
                    title: {
                        text: '暂无数据',
                        x: 'center',
                        y: 'center'
                    }
                }
            } else {
                chart.clear();
                var datas = [];
                var str;
                if (budgetTarget === 'budget') {
                    datas.push({
                        name: "剩余", value: data.budgetAmount - data.totalAmount, label: {
                            show: true
                        }
                    });
                    datas.push({
                        name: "花费", value: data.totalAmount, label: {
                            show: false
                        }
                    });
                    str='剩余预算: '+data.dAmount+'\n总预算:    '+data.budgetAmount+'\n已支出:    '+data.totalAmount;
                } else {
                    datas.push({
                        name: "达成", value: data.totalAmount, label: {
                            show: true
                        }
                    });
                    datas.push({
                        name: "未达成", value: data.targetAmount - data.totalAmount, label: {

                            show: false

                        }
                    });
                    str='总收入目标:    '+data.targetAmount+'\n已达成收入:    '+data.totalAmount;
                }
                option = {
                    graphic: {
                        type: "text",
                        left: "70%",
                        top: "20%",
                        style: {
                            text: str,
                            textAlign: "left",
                            fill: "#333438",
                            fontSize: 18,
                            fontWeight: 500
                        }
                    },
                    series: [
                        {
                            type: 'pie',
                            center: ['30%', '50%'],
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            hoverAnimation: false,
                            label: { //  饼图图形上的文本标签
                                show: true,
                                position: 'center',
                                color: 'red',
                                fontSize: 18,
                                fontWeight: 'bold',
                                formatter: budgetTarget === 'budget' ? (+data.totalAmount > +data.budgetAmount ? '已超支' : '{d}%\n{b}') : (+data.totalAmount >= +data.targetAmount ? '目标达成' : '{d}%\n{b}') // {b}:数据名； {c}：数据值； {d}：百分比，可以自定义显示内容，
                            },
                            color: ['#ea7e53', '#FFE7BA'],
                            data: datas
                        }
                    ]
                };
            }
            chart.setOption(option);
        }
    });
}
