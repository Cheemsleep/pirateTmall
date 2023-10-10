<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-07
  Time: 下午 1:20
  To change this template use File | Settings | File Templates.
  轮播部分
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" isELIgnored="false" %>


<div id="carousel-of-product" class="carousel-of-product carousel slide1" data-ride="carousel">
    <ol class="carousel-indicators">
        <li data-target="#carousel-of-product" data-slide-to="0" class="active"></li>
        <li data-target="#carousel-of-product" data-slide-to="1"></li>
        <li data-target="#carousel-of-product" data-slide-to="2"></li>
        <li data-target="#carousel-of-product" data-slide-to="3"></li>
    </ol>

    <div class="carousel-inner" role="listbox">
        <div class="item active">
            <img class="carousel carouselImage" src="img/lunbo/1.jpg">
        </div>
        <div class="item">
            <img class="carouselImage" src="img/lunbo/2.jpg">
        </div>
        <div class="item">
            <img class="carouselImage" src="img/lunbo/3.jpg">
        </div>
        <div class="item">
            <img class="carouselImage" src="img/lunbo/4.jpg">
        </div>
    </div>
</div>
