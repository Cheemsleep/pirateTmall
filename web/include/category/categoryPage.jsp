<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-11
  Time: 下午 6:09
  To change this template use File | Settings | File Templates.
  1. 显示当前分类图片

  <img src="img/category/${c.id}.jpg">


  2. 排序条 sortBar.jsp

  3. 产品列表 productsByCategory.jsp

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<title>盗版天猫-${c.name}</title>
<div id="category">
  <div class="categoryPageDiv">
    <img src="img/category/${c.id}.jpg">
    <%@include file="sortBar.jsp"%>
    <%@include file="productsByCategory.jsp"%>
  </div>
</div>

