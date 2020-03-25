$(document).ready(function(){
    var nameFlag = false;//注册信息完成标记
    var ageFlag = false;
    var phoneFlag = false;
    var pwdFlag = false;
    var pwd1Flag = false;
    var accountFlag = false;
    $("#btn").attr("disabled", true);           //设置注册按钮不可用

    var $required = $("<strong class='high'>*</strong>");//在必填项后加红色的“*”号
    $("#pName").append($required);
    $required = $("<strong class='high'>*</strong>");
    $("#pAge").append($required);
    $required = $("<strong class='high'>*</strong>");
    $("#pPwd").append($required);
    $required = $("<strong class='high'>*</strong>");
    $("#pPwd1").append($required);
    $required = $("<strong class='high'>*</strong>");
    $("#pPhone").append($required);
    $required = $("<strong class='high'>*</strong>");
    $("#pAccount").append($required);


//为表单元素添加失去焦点事件
$("form :input").blur(function(){
    $("#btn").attr("disabled", true);
    //验证姓名
    if($(this).is("#name")){
        $("#pName").parent().find(".onError").remove();
        $("#pName").parent().find(".onSuccess").remove();

        var nameVal = $.trim(this.value); //原生js去空格方式：this.replace(/(^\s*)|(\s*$)/g, "")
        var regName = /[~#^$@%&!*()<>:;'"{}【】  ]/;
        if(nameVal == "" || nameVal.length < 2 || regName.test(nameVal)){
            var errorMsg = " 姓名非空，长度2位以上，不包含特殊字符！";

            $("#pName").append("<span class='onError'>" + errorMsg + "</span>");
        }
        else{
            var okMsg=" 输入正确";
            $("#pName").append("<span class='onSuccess'>" + okMsg + "</span>");
            nameFlag = true;
        }
    }

    //验证账号account是否唯一
    if($(this).is("#account")){
        $("#pAccount").parent().find(".onError").remove();
        $("#pAccount").parent().find(".onSuccess").remove();
        var account = $.trim(this.value);
        var ok = false;

        $.ajax({
            async: false,           //取消异步（即同步），异步会使下面的代码在接收结果之前运行
            type: "get",            //发送方式
            url: "/patientRegiste",//发送的地址
            data: {                 //发送的数据
                "account":account,
            },
            datatype: "json",       //接收的数据
            success: function (data) {
                // alert("返回的数据："+data);
                if(data == 0)
                    ok = true;
            },
            error: function () {
                alert("发送失败。。。");
            }
        });

        if(!ok){
            var errorMsg = " 账号已存在";

            $("#pAccount").append("<span class='onError'>" + errorMsg + "</span>");
        }
        else{
            var okMsg=" 输入正确";
            $("#pAccount").append("<span class='onSuccess'>" + okMsg + "</span>");
            accountFlag = true;
        }
    }

    //验证年龄
    if($(this).is("#age")){
        $("#pAge").parent().find(".onError").remove();
        $("#pAge").parent().find(".onSuccess").remove();

        var age = $.trim(this.value);
        if(age < 0 || age > 100){
            var errorMsg = " 请输入正确的年龄！";
            $("#pAge").append("<span class='onError'>" + errorMsg + "</span>");
        }
        else{
            var okMsg=" 输入正确";
            $("#pAge").append("<span class='onSuccess'>" + okMsg + "</span>");
            ageFlag = true;
        }
    }

    //验证手机号
    if($(this).is("#phone")){
        $("#pPhone").parent().find(".onError").remove();
        $("#pPhone").parent().find(".onSuccess").remove();

        var phone = $.trim(this.value);
        var regName = /^[1][3,4,5,7,8,9][0-9]{9}$/;
        if(phone.length != 11 || !regName.test(phone)){
            var errorMsg = " 请输入正确的手机号码！";
            $("#pPhone").append("<span class='onError'>" + errorMsg + "</span>");
        }
        else{
            var okMsg=" 输入正确";
            $("#pPhone").append("<span class='onSuccess'>" + okMsg + "</span>");
            phoneFlag = true;
        }
    }

    //验证密码
    if($(this).is("#password")){
        $("#pPwd").parent().find(".onError").remove();
        $("#pPwd").parent().find(".onSuccess").remove();

        var pwd = $.trim(this.value);
        var regName = /[^[A-Za-z0-9]+$]/;
        if(pwd.length < 6 || regName.test(pwd)){
            var errorMsg = " 密码由6位以上的数字和字母组成！";
            $("#pPwd").append("<span class='onError'>" + errorMsg + "</span>");
        }
        else{
            var okMsg=" 输入正确";
            $("#pPwd").append("<span class='onSuccess'>" + okMsg + "</span>");
            pwdFlag = true;
        }

    }

    //二次验证密码
    if($(this).is("#password1")) {
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

    if(nameFlag && ageFlag && pwdFlag && phoneFlag && pwd1Flag){        //若所有信息填写正确，将注册按钮设置为可用状态
        $("#btn").attr("disabled", false);
    }
});

});