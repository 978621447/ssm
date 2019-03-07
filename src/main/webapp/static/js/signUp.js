$(function () {
    //获取当前网址，如： http://localhost:8080/ssm-web/share/menu.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： ssm-web/share/menu.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPath = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/ssm-web
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    //服务器前缀
    window.contextPath = localhostPath + projectName;

    function signUp() {
        $.ajax({
            //几个参数需要注意一下
            type: "POST",
            dataType: "json",//预期服务器返回的数据类型
            url: window.contextPath + "/user/signUp" ,//url
            data: $('#signUpForm').serialize(),
            success: function (result) {
                if(result.code == 0) {
                    alert("注册成功");
                    window.location.href = window.contextPath + "/route/index";
                } else {
                    alert(result.msg);
                }
            },
            error : function() {
                alert("系统异常，请稍后重试！");
            }
        });
    }
    $("#signUpSubmit").on("click", function () {
        signUp();
    });
});