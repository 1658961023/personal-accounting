$(function () {
   $('#record').click(function () {
       var amount = $('#amount').val();
       var category = $('#category').val();
       var date = $('#date').val();
       if(isNaN(amount)){
           alert("金额必须为数字")
       }else if(amount===''||+amount<0){
           alert("金额必输且大于零")
       }else if(category===null||category===''){
           alert("分类必选")
       }else if(date===null||date===''){
           alert("日期必输")
       }else {
           $('#recordForm').submit();
       }
   });
   $('#excelBtn').click(function () {
       var filename = $('#file').val();
       if(filename===''||filename===null){
           alert("未选择任何文件！")
       }else {
           $('#excelForm').submit();
       }
   })
});