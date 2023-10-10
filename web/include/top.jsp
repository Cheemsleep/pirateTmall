<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-06
  Time: 下午 4:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="top">
  <a href="${contextPath}">
    <span style="color: #c40000; margin: 0px" class="glyphicon glyphicon-home redColor"></span>
    盗版天猫首页
  </a>

  <span>欢迎访问盗版天猫</span>

  <c:if test="${!empty user}">
      <a href="login.jsp">${user.name}</a>
      <a href="forelogout">退出登录</a>
  </c:if>

  <c:if test="${empty user}">
    <a href="login.jsp">登录后访问更多功能</a>
    <a href="register.jsp">没有账号？</a>
  </c:if>

  <span class="pull-right">
    <a href="forebought">我的订单</a>
    <a href="forecart">
      <span style="color: #c40000; margin: 0px" class="glyphicon glyphicon-shopping-cart redColor"></span>
      购物车中有<strong>${cartTotalItemNumber}</strong>件
    </a>
  </span>
</nav>