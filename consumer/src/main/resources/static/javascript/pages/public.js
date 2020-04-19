$(function () {
    $('#open_btn').click(function () {
        document.getElementById('fade').style.display = 'block';
        document.getElementById('succ-pop').style.display = 'block';
    });

    $('#cancel').click(function () {
        window.location.href = location.href
    });

    $('#export').click(function () {
        window.location.href = 'export'
    });

    $('#btn_budgetEdit').click(function () {
        var month = $('#test').val();
        var budget = $('#budget').val();
        if (month == null) {
            alert("请选择月份！");
        } else if (budget == null) {
            alert("请输入金额！")
        } else {
            window.location.href = 'editBudget?budgetAmount=' + budget + '&dateType=1'+'&month='+month;
        }
    });

    $('#btn_targetEdit').click(function () {
        var month = $('#test2').val();
        var target = $('#target').val();
        if (month == null) {
            alert("请选择月份！");
        } else if (target == null) {
            alert("请输入金额！")
        } else {
            window.location.href = 'editTarget?targetAmount=' + target + '&dateType=1'+'&month='+month;
        }
    });
});
