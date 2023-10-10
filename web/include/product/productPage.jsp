<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-10
  Time: 下午 5:14
  To change this template use File | Settings | File Templates.

  productPage.jsp 又由3个页面组成
  1. imgAndInfo.jsp
  单个图片和基本信息
  2. productReview.jsp
  评价信息
  3. productDetail.jsp
  详情图片
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>

<title>盗版天猫官网</title>

<div class="categoryPictureInProductPageDiv">
  <img class="categoryPictureInProductPage" src="img/category/${p.category.id}.jpg">
</div>

<div class="productPageDiv">
  <%@include file="imageAndInfo.jsp"%>
  <%@include file="productReview.jsp"%>
  <%@include file="productDetail.jsp"%>
</div>