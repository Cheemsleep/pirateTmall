<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-12
  Time: 下午 2:06
  To change this template use File | Settings | File Templates.
  productsBySearch.jsp 显示结果：
  1. 遍历ps，把每个产品的图片，价格，标题等信息显示出来
  2. 如果ps为空，则显示 "没有满足条件的产品"
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<div class="searchProducts">
  <c:forEach items="${ps}" var="p" >
    <div class="productUnit" price="${p.promotePrice}">
      <a href="foreproduct?pid=${p.id}">
        <img class="productImage" src="img/productSingle/${p.firstProductImage.id}.jpg">
      </a>
      <span class="productPrice">￥<fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2" /></span>
      <a class="productLink" href="foreproduct?pid=${p.id}">
        ${fn:substring(p.name, 0, 50)}
      </a>

      <a class="tmallLink" href="foreproduct?pid=${p.id}">盗版天猫专卖</a>
      <div class="productInfo">
        <span class="monthDeal">月成交 <span class="productDealNumber">${p.saleCount}单</span></span>
        <span class="monthDeal">评价 <span class="productDealNumber">${p.reviewCount}</span></span>
        <span class="wangwang"><img src="img/site/wangwang.png"></span>
      </div>
    </div>
  </c:forEach>
  <c:if test="${empty ps}">
    <div class="noMatch">搜索的产品未上架</div>
  </c:if>
  <div style="clear: both"></div>
</div>

