<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-06
  Time: 下午 10:58
  To change this template use File | Settings | File Templates.
  categoryMenu.jsp 显示左侧的竖状分类导航
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<div class="categoryMenu">
    <c:forEach items="${cs}" var="c">
        <div cid="${c.id}" class="eachCategory">
            <span class="glyphicon glyphicon-link"></span>
            <a href="forecategory?cid=${c.id}">
                ${c.name}
            </a>
        </div>
    </c:forEach>
</div>
