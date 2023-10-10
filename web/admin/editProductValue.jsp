<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-04
  Time: 下午 3:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" import="java.util.*" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>更改商品属性值</title>

<script>
    $(function () {
        $("input.pvValue").keyup(function () {
            var value = $(this).val;
            var page = "admin_product_updatePropertyValue";
            var pvId = $(this).attr("pvid");
            var parentSpan = $(this).parent("span");
            parentSpan.css("boder", "1px solid yellow");
            $.post(
                page,
                {"value":value, "pvid":pvId},
                function (result) {
                    if ("success" == result)
                        parentSpan.css("boder", "1px solid green");
                    else
                        parentSpan.css("boder", "1px solid red");
                }
            );
        });
    });
</script>

<%--

1. 是监听输入框上的keyup事件
2. 获取输入框里的值
3. 获取输入框上的自定义属性pvid，这就是当前PropertyValue对应的id
4. 把边框的颜色修改为黄色，表示正在修改的意思
5. 借助JQuery的ajax函数 $.post，把id和值，提交到admin_product_updatePropertyValue
6. admin_product_updatePropertyValue导致ProductServlet的updatePropertyValue方法被调用
6.1 获取pvid
6.2 获取value
6.3 基于pvid和value,更新PropertyValue对象
6.4 返回"%success"
7. BaseBackServlet根据返回值"%success"，直接输出字符串"success" 到浏览器
8. 浏览器判断如果返回值是"success",那么就把边框设置为绿色，表示修改成功，否则设置为红色，表示修改失败

--%>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?cid=${p.category.id}">${p.category.name}</a></li>
        <li class="active">${p.name}</li>
        <li class="active">编辑产品属性</li>
    </ol>

    <div class="editPVDiv">
        <c:forEach items="${pvs}" var="pv">
            <div class="eachPV">
                <span class="pvName">${pv.property.name}</span>
                <span class="pvValue"><input class="pvValue" pvid="${pv.id}" type="text" value="${pv.value}"></span>
            </div>
        </c:forEach>
        <div style="clear: both"></div>
    </div>
</div>