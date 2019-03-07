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
    //
    function popBox() {
        var popBox = document.getElementById("popBox");
        var popLayer = document.getElementById("popLayer");
        popBox.style.display = "block";
        popLayer.style.display = "block";
    }
    /*点击关闭按钮*/
    function closeBox() {
        var popBox = document.getElementById("popBox");
        var popLayer = document.getElementById("popLayer");
        popBox.style.display = "none";
        popLayer.style.display = "none";
    }
//
    function sendAjax() {
        $.ajax({
            type: 'POST',
            url: contextPath + "/test/map",
            dataType: "json",
            cache: false,
            async: false,
            success: function (data) {
                console.log("data》》》》" + data);
                $("#span2").html(data.key);
            }
        });
    }

    function login() {
        $.ajax({
            //几个参数需要注意一下
            type: "POST",
            dataType: "json",//预期服务器返回的数据类型
            url: window.contextPath + "/user/login" ,//url
            data: $('#loginForm').serialize(),
            success: function (result) {
                if(result.code == 0) {
                    alert("登录成功");
                    location.reload(true);
                } else {
                    alert(result.msg);
                }
            },
            error : function() {
                alert("系统异常，请稍后重试！");
            }
        });
    }
    $("#login").on("click", function () {
        popBox();
    });
    $("#closeLogin").on("click", function () {
        closeBox();
    });
    $("#loginSubmit").on("click", function () {
        login();
    });
    $("#signUp").on("click", function () {
        window.location.href = window.contextPath + "/route/signUp";
    });
});