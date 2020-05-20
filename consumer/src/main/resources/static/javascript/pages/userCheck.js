$(function () {
    $('#saveInfoBtn').click(function () {
        var sex = $('input:radio[name="sex"]:checked').val();
        var nickname = $('#nickname').val();
        var phone = $('#phone').val();
        var email = $('#email').val();
        var emailPattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

        if (sex===null || sex===undefined){
            alert("性别必选")
        }else if (nickname===null || nickname===''){
            alert("昵称必输")
        }else if (phone!=='' && !(/^1[3|4|5|8][0-9]\d{4,8}$/.test(phone))){
            alert("手机号格式不正确")
        }else if (email!=='' && !emailPattern.test(email)){
            alert("邮箱格式不正确")
        }else {
            $('#userInfoForm').submit();
        }
    })
});