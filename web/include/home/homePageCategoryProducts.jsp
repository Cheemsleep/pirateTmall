
<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-07
  Time: 下午 1:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<c:if test="${empty param.categoryCount}">
  <c:set var="categoryCount" scope="page" value="100" />
</c:if>

<c:if test="${!empty param.categoryCount}">
  <c:set var="categoryCountl" scope="page" value="${param.categoryCount}" />
</c:if>

<div class="homepageCategoryProducts">
  <c:forEach items="${cs}" var="c" varStatus="stc">
    <c:if test="${stc.count <= categoryCount}">
      <div class="left-mark"></div>
      <span class="categoryTitle">${c.name}</span>
      <br>
      <c:forEach items="${c.products}" var="p" varStatus="st">
        <c:if test="${st.count <= 5}">
          <div class="productItem">
            <a href="foreproduct?pid=${p.id}"><img width="100px" src="img/productSingle_middle/${p.firstProductImage.id}.jpg"></a>
            <a class="productItemDescLink" href="foreproduct?pid=${p.id}">
              <span class="productItemDesc">[热销]
                ${fn:substring(p.name, 0, 20)}
              </span>
            </a>
            <span class="productPrice">
              <fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2" />
            </span>
          </div>
        </c:if>
      </c:forEach>
      <div style="clear:both"></div>
    </c:if>
  </c:forEach>
  <img id="endpng" class="endpng" src="img/site/end.png">
</div>