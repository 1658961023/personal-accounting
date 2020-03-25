$(function(){
	$('#entry').click(function(){
		if($('#adminName').val()===''){
			alert('请输入账号')
		}else if($('#adminPwd').val()===''){
			alert('请输入密码');
		}else{
			$('#loginForm').submit();
		}
	});
});
