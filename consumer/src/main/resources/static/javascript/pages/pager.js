$(function () {
    var url = location.href;
    var i = url.indexOf("&curPage");
    var curPage = $('#curPage').val();
    var count = $('#count').val();
    $('#last').click(function () {
        if(curPage==='1'){
            alert("当前为第一页！")
        }else {
            window.location.href=url.substr(0,i)+"&curPage="+ --curPage+"&pagesize=10";
        }
    });
    $('#next').click(function () {
        if(curPage===count){
            alert("当前为最后一页！")
        }else {
            window.location.href=url.substr(0,i)+"&curPage="+ ++curPage +"&pagesize=10";
        }
    });
    $('#recordSearch').click(function () {
        var budgetTypeSelect = $('#budgetType').val();
        var budgetType = budgetTypeSelect==='2'?'':budgetTypeSelect;
        var month = $('#month').val();
        window.location.href='allRecordsPage?budgetType='+budgetType+'&month='+month+'&curPage=1&pagesize=10'
    })
});