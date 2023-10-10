<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-09
  Time: 下午 1:49
  To change this template use File | Settings | File Templates.
  注册业务页面，用于向服务器提交账号和密码
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        <c:if test="${!empty msg}">
        $("span.errorMessage").html("${msg}");
        $("div.loginErrorMessageDiv").show();
        </c:if>

        $("form.loginForm").submit(function () {
            if (0 == $("#name").val().length || 0 == $("#password").val().length) {
                $("span.loginErrorMessageDiv").html("账号或密码为空");
                $("div.loginErrorMessageDiv").show();
                return false;
            }
            return true;
        });

        $("form.loginForm input").keyup(function () {
            $("div.loginErrorMessageDiv").hide();
        });

        var left = window.innerWidth/2 + 162;
        $("div.loginSmallDiv").css("left", left);
    });
</script>

<div id="loginDiv" style="position: relative">
    <div class="simpleLogo">
        <a href="http://localhost:8080/tmall"><img src="img/site/simpleLogo.png"></a>
    </div>

    <img id="loginBackgroundImg" class="loginBackgroundImg" src="img/site/loginBackground.png">

    <form class="loginForm" action="forelogin" method="post">
        <div id="loginSmallDiv" class="loginSmallDiv">
            <div class="loginErrorMessageDiv">
                <div class="alert alert-danger">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                    <span class="errorMessage"></span>
                </div>
            </div>

            <div class="login_acount_text">账号登陆</div>
            <div class="loginInput">
                <span class="loginInputIcon">
                    <span class="glyphicon glyphicon-user"></span>
                </span>
                <input id="name" name="name" placeholder="手机/用户名/邮箱" type="text">
            </div>

            <div class="loginInput">
                <span class="loginInputIcon">
                    <span class="glyphicon glyphicon-lock"></span>
                </span>
                <input id="password" name="password" type="password" placeholder="密码">
            </div>

            <span class="text-danger">请输入盗版天猫的账号密码，不要输入真实的 </span> <br> <br>

            <div>
                <a class="notImplementLink" href="#nowhere">忘记密码</a>
                <a href="register.jsp" class="pull-right">注册账号</a>
            </div>
            <div style="margin-top: 20px">
                <button class="btn btn-block redButton" type="submit">登录</button>
            </div>
        </div>
    </form>
</div>