<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-08
  Time: 下午 2:25
  To change this template use File | Settings | File Templates.
  注册页面的主体功能，用于提交账号密码。 在提交之前会进行为空验证，以及密码是否一致验证。

    这段代码用于当账号提交到服务端，服务端判断当前账号已经存在的情况下，显示返回的错误提示 "用户名已经被使用,不能使用"

	<c:if test="${!empty msg}">
	$("span.errorMessage").html("${msg}");
	$("div.registerErrorMessageDiv").css("visibility","visible");
	</c:if>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        <c:if test="${!empty msg}">
            $("span.errorMessage").html("${msg}");
            $("div.registerErrorMessageDiv").css("visibility", "visible");
        </c:if>

         $(".registerForm").submit(function () {
             if (0 == $("#name").val().length) {
                 $("span.errorMessage").html("请输入用户名");
                 $("div.registerErrorMessageDiv").css("visibility", "visible");
                 return false;
             }
             if (0 == $("#password").val().length) {
                 $("span.errorMessage").html("请输入密码");
                 $("div.registerErrorMessageDiv").css("visibility", "visible");
                 return false;
             }
             if (0 == $("#repeatpassword").val().length) {
                 $("span.errorMessage").html("请在输入一次密码");
                 $("div.registerErrorMessageDiv").css("visibility", "visible");
                 return false;
             }
             if ($("#password").val() != $("#repeatpassword").val()) {
                 $("span.errorMessage").html("两次输入的密码不一致");
                 $("div.registerErrorMessageDiv").css("visibility", "visible");
                 return false;
             }
            return true;
         })
    })
</script>

<div class="registerDiv">
    <h1 style="color: #c40000">注册用户</h1>
</div>

<form method="post" action="foreregister" class="registerForm">
    <div class="registerDiv">
        <div class="registerErrorMessageDiv">
            <div class="alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                <span class="errorMessage"></span>
            </div>
        </div>

        <table class="registerTable" align="center">
            <tr>
                <td class="registerTip registerTableLeftTD">设置会员名</td>
                <td></td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">登录名</td>
                <td class="registerTableRightTD"><input id="name" name="name" placeholder="会员名无法更改，请慎重填写"></td>
            </tr>
            <tr>
                <td class="registerTip registerTableLeftTD">设置密码</td>
                <td class="registerTableRightTD">请妥善保管密码，防止落入他人手中</td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">密码</td>
                <td class="registerTableRightTD"><input id="password" name="password" type="password" placeholder="请输入密码"></td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">确认密码</td>
                <td class="registerTableRightTD"><input id="repeatpassword" type="password" placeholder="请再次输入密码"></td>
            </tr>

            <tr>
                <td colspan="2" class="registerButtonTD">
                    <button>提  交</button>
                </td>
            </tr>
        </table>
    </div>
</form>
