<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-10
  Time: 下午 10:04
  To change this template use File | Settings | File Templates.
  productDetail.jsp做了两件事
  1. 显示属性值

  <c:forEach items="${pvs}" var="pv">
      <span>${pv.property.name}:  ${fn:substring(pv.value, 0, 10)} </span>
  </c:forEach>


  2. 显示详情图片

  <c:forEach items="${p.productDetailImages}" var="pi">
      <img src="img/productDetail/${pi.id}.jpg">
  </c:forEach>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>


<div class="productDetailDiv">
  <div class="productDetailTopPart">
    <a href="#nowhere" class="productDetailTopPartSelectedLink selected">商品详情</a>
    <a href="#nowhere" class="productDetailTopReviewLink">累计评价 <span class="productDetailTopReviewLinkNumber">${p.reviewCount}</span></a>
  </div>

  <div class="productParamterPart">
    <div class="productParamter">产品参数: </div>
    <div class="productParamterList">
      <c:forEach items="${pvs}" var="pv">
        <span>${pv.property.name}: ${fn:substring(pv.value, 0, 10)}</span>
      </c:forEach>
    </div>
    <div style="clear: both"></div>
  </div>

  <div class="productDetailImagesPart">
    <c:forEach items="${p.productDetailImages}" var="pi">
      <img src="img/productDetail/${pi.id}.jpg">
    </c:forEach>
  </div>
</div>