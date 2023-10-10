<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-07-26
  Time: 下午 1:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<div class="navigatorDiv">
    <nav class="navbar navbar-default navbar-fixed-top navbar-inverse">
        <img style="margin-left: 10px;margin-right: 0px" class="pull-left" src="img/site/tmallbuy.png" height="45px">
        <a class="navbar-brand" href="#nowhere">模仿天猫后台</a>
        <a class="navbar-brand" href="admin_category_list">分类管理</a>  <!-- 传参给地址栏后在Servlet中反射调用相应方法 -->
        <a class="navbar-brand" href="admin_user_list">用户管理</a>
        <a class="navbar-brand" href="admin_order_list">订单管理</a>
    </nav>
</div>