$(function () {
    var selector = $('select[name="category"]');
    $('input:radio[name="budgetType"]').click(function () {
        var checkValue = $('input:radio[name="budgetType"]:checked').val();
        if (checkValue === 0) {
            $.ajax({
                url: "/getCategoryByType",
                method: "get",
                data:{"budgetType":checkValue},
                success: function (data) {
                    selector.empty();
                    for (var i = 0; i < data.length; i++) {
                        selector.append('<option value="' + data[i].name + '">' + data[i].name + '</option>');
                    }
                }
            })
        }
        else {
            $.ajax({
                url: "/getCategoryByType",
                method: "get",
                data:{"budgetType":checkValue},
                success: function (data) {
                    selector.empty();
                    for (var i = 0; i < data.length; i++) {
                        selector.append('<option value="' + data[i].name + '">' + data[i].name + '</option>');
                    }
                }
            })
        }
    });
});