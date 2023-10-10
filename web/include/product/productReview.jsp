<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-10
  Time: 下午 9:57
  To change this template use File | Settings | File Templates.
  借助c:forEach遍历request中的reviews
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<div class="productReviewDiv">
  <div class="productReviewTopPart">
    <a href="#nowhere" class="productReviewTopPartSelectedLink">商品详情</a>
    <a href="#nowhere" class="selected">累计评价<span class="productReviewTopReviewLinkNumber">${p.reviewCount}</span></a>
  </div>

  <div class="productReviewContentPart">
    <c:forEach items="${reviews}" var="r">
      <div class="productReviewItem">
        <div class="productReviewItemContent">
          ${r.content}
        </div>
        <div class="productReviewItemDate"><fmt:formatDate value="${r.createDate}" pattern="yyyy-MM-dd" /></div>
      </div>

      <div class="productReviewItemUserInfo">
        ${r.user.anonymousName}<span class="userInfoGrayPart">(盗版天猫用户)</span>
      </div>

      <div style="clear: both"></div>
    </c:forEach>
  </div>
</div>
