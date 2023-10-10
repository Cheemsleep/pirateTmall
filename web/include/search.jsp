<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-06
  Time: 下午 5:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<a href="${contextPath}">
  <img id="logo" src="img/site/logo.gif" class="logo">
</a>
<%-- 这里会从request的属性"cs" 中获取到分类集合，并取第5个到第8个，一共4个来显示 --%>
<form action="foresearch" method="post">
    <div class="searchDiv">
      <input name="keyword" type="text" value="${param.keyword}" placeholder="时尚男鞋 墨镜">
      <button type="submit" class="searchButton">搜索</button>
      <div class="searchBelow">
        <c:forEach items="${cs}" var="c" varStatus="st">
          <c:if test="${st.count >= 5 and st.count <= 8}">
            <span>
              <a href="forecategory?cid=${c.id}">
                ${c.name}
              </a>
              <c:if test="${st.count != 8}">
                <span>|</span>
              </c:if>
            </span>
          </c:if>
        </c:forEach>
      </div>
    </div>
</form>

