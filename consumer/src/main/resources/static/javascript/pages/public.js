$(function () {
    $('#open_btn').click(function () {
        document.getElementById('fade').style.display = 'block';
        document.getElementById('succ-pop').style.display = 'block';
    });

    $('#cancel').click(function () {
        window.location.href=location.href
    });

    $('#export').click(function () {
        window.location.href='export'
    })
});
