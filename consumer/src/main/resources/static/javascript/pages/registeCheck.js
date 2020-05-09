$(function () {
    var acctFlag = false;//注册信息完成标记
    var nameFlag = false;
    var pwdFlag = false;
    var pwd1Flag = false;
    $("#btn").attr("disabled", true);           //设置注册按钮不可用

    var $required = $("<strong style='color: red;'>*</strong>");//在必填项后加红色的“*”号
    $("#lacct").append($required);
    $required = $("<strong style='color: red;'>*</strong>");
    $("#lnickname").append($required);
    $required = $("<strong style='color: red;'>*</strong>");
    $("#pPwd").append($required);
    $required = $("<strong style='color: red;'>*</strong>");
    $("#pPwd1").append($required);


//为表单元素添加失去焦点事件
    $("form :input").blur(function () {
        $("#btn").attr("disabled", true);

        //验证账号必输
        if ($(this).is("#acct")) {
            $("#lacct").parent().find(".onError").remove();
            $("#lacct").parent().find(".onSuccess").remove();

            var acct = $.trim(this.value);
            if (acct === null || acct === '') {
                var errorMsg = " 账号必输！";
                $("#lacct").append("<span class='onError'>" + errorMsg + "</span>");
            }
            else {
                acctFlag = true;
            }

        }

        //验证昵称必输
        if ($(this).is("#nickname")) {
            $("#lnickname").parent().find(".onError").remove();
            $("#lnickname").parent().find(".onSuccess").remove();

            var nickname = $.trim(this.value);
            if (nickname === null || nickname === '') {
                var errorMsg = " 昵称必输！";
                $("#lnickname").append("<span class='onError'>" + errorMsg + "</span>");
            } else {
                nameFlag = true;
            }

        }

        //验证密码
        if ($(this).is("#password")) {
            $("#pPwd").parent().find(".onError").remove();
            $("#pPwd").parent().find(".onSuccess").remove();

            var pwd = $.trim(this.value);
            var regName = /[^[A-Za-z0-9]+$]/;
            if (pwd.length < 6 || regName.test(pwd)) {
                var errorMsg = " 密码由6位以上的数字,字母或符合组成！";
                $("#pPwd").append("<span class='onError'>" + errorMsg + "</span>");
            }
            else {
                var okMsg = " 输入正确";
                $("#pPwd").append("<span class='onSuccess'>" + okMsg + "</span>");
                pwdFlag = true;
            }

        }

        //二次验证密码
        if ($(this).is("#password1")) {
            if (pwdFlag) {
                $("#pPwd1").parent().find(".onError").remove();
                $("#pPwd1").parent().find(".onSuccess").remove();

                var pwd = $("#password").val();
                var pwd1 = $.trim(this.value);
                if (pwd == pwd1) {
                    var okMsg = " 输入正确";
                    $("#pPwd1").append("<span class='onSuccess'>" + okMsg + "</span>");
                    pwd1Flag = true;
                } else {
                    var errorMsg = " 两次密码输入不相同！";
                    $("#pPwd1").append("<span class='onError'>" + errorMsg + "</span>");
                }

            }
        }

        if (acctFlag && nameFlag && pwdFlag && pwd1Flag) {        //若所有信息填写正确，将注册按钮设置为可用状态
            $("#btn").attr("disabled", false);
        }
    });
});