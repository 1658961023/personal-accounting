$(function () {
    $('#changePwd').click(function () {
        var rightPwd = $('#right_pwd').val();
        var oldPwd = $('#old_pwd').val();
        var newPwd = $('#new_pwd').val();
        var newPwd2 = $('#new_pwd2').val();
        var regName = /[^[A-Za-z0-9]+$]/;
        if (oldPwd !== rightPwd) {
            alert("原密码不正确");
        } else if (newPwd.length < 6 || regName.test(newPwd)) {
            alert("密码由6位以上的数字,字母或符合组成")
        } else if (newPwd !== newPwd2) {
            alert("两次密码不一致")
        } else if (oldPwd === newPwd) {
            alert("新密码和旧密码不能一样")
        } else {
            $('#pwdForm').submit();
        }
    })
});