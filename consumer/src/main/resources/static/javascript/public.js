$(function(){
	//安全退出
	$('#JsSignOut').click(function(){
		layer.confirm('确定登出系统？', {
		  title:'系统提示',
		  btn: ['确定','取消']
		}, function(){
		  location.href = 'loginOut';
		});
	});
});
