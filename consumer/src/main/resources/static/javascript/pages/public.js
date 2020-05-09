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
        var budget = $('#budget').val();
        if (budget == null||+budget<0) {
            alert("请输入金额且金额大于等于0")
        } else {
            window.location.href = 'editBudget?budgetAmount=' + budget + '&dateType=1';
        }
    });

    $('#btn_targetEdit').click(function () {
        var target = $('#target').val();
            if (target == null||+target<0) {
            alert("请输入金额且金额大于等于0！")
        } else {
            window.location.href = 'editTarget?targetAmount=' + target + '&dateType=1';
        }
    });
});
