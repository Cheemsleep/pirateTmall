<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-06
  Time: 下午 11:01
  To change this template use File | Settings | File Templates.
  productsAsideCategorys.jsp 进行了三层遍历
  1. 先取出每个分类
  2. 然后取出每个分类的productsByRow集合
  3. 根据productsByRow集合，取出每个产品，把产品的subTitle信息里的第一个单词取出来显示。

  JQuery代码解释：

  这个是用于随机挑选一个产品作为推荐产品，来进行高亮显示。后台设置某个产品是推荐产品，不过这里直接在前端按照20%的概率，随机挑选了一个产品。
  $("div.productsAsideCategorys div.row a").each(function(){
      var v = Math.round(Math.random() *6);
      if(v == 1)
          $(this).css("color","#87CEFA");
  });
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
  $(function () {
    $("div.productsAsideCategorys div.row a").each(function () {
      var v = Math.round(Math.random()*6);
      if (v == 1)
        $(this).css("color", "#87CEFA");
    });
  });
</script>

<c:forEach items="${cs}" var="c">
  <div cid="${c.id}" class="productsAsideCategorys">
    <c:forEach items="${c.productsByRow}" var="ps">
      <div class="row show1">
        <c:forEach items="${ps}" var="p">
          <c:if test="${!empty p.subTitle}">
            <a href="foreproduct?pid=${p.id}">
              <c:forEach items="${fn:split(p.subTitle, ' ')}" var="title" varStatus="st">
                <c:if test="${st.index == 0}">
                  ${title}
                </c:if>
              </c:forEach>
            </a>
          </c:if>
        </c:forEach>
        <div class="separator"></div>
      </div>
    </c:forEach>

  </div>
</c:forEach>