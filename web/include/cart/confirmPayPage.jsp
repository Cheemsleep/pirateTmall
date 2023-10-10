<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-19
  Time: 下午 5:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<div class="confirmPayPageDiv">
    <div class="confirmPayImageDiv">
        <img src="img/site/comformPayFlow.png">
        <div class="confirmPayTime1">
            <fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
        </div>
        <div class="confirmPayTime2">
            <fmt:formatDate value="${o.payDate}" pattern="yyyy-MM-dd HH:mm:ss" />
        </div>
        <div class="confirmPayTime3">
            <fmt:formatDate value="${o.deliveryDate}" pattern="yyyy-MM-dd HH:mm:ss" />
        </div>
    </div>
    <div class="confirmPayOrderInfoDiv">
        <div class="confirmPayOrderInfoText">我已收货，同意支付宝打款给卖家</div>
    </div>
    <div class="confirmPayOrderItemDiv">
        <div class="confirmPayOrderItemText">订单详情</div>
        <table class="confirmPayOrderItemTable">
            <thead>
                <th colspan="2">商品</th>
                <th width="120px">单价</th>
                <th width="120px">数量</th>
                <th width="120px">商品总价 </th>
                <th width="120px">运费</th>
            </thead>
            <c:forEach items="${o.orderItems}" var="oi">
                <tr>
                    <td><img width="50px" src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg"></td>
                    <td class="confirmPayOrderItemProductLink">
                        <a href="foreproduct?pid=${oi.product.id}">${oi.product.name}</a>
                    </td>
                    <td>￥<fmt:formatNumber type="number" value="${oi.product.orignalPrice}" minFractionDigits="2" /></td>
                    <td><fmt:formatNumber type="number" value="${oi.number}" minFractionDigits="2" /></td>
                    <td><span class="conformPayProductPrice">￥<fmt:formatNumber type="number" value="${oi.product.orignalPrice * oi.number}" minFractionDigits="2" /></span></td>
                    <td><span>快递费：0.00</span></td>
                </tr>
            </c:forEach>
        </table>
        <div class="confirmPayOrderItemText pull-right">
            实付： <span class="confirmPayOrderItemSumPrice">￥<fmt:formatNumber type="number" value="${o.total}" minFractionDigits="2" /></span>
        </div>
    </div>
    <div class="confirmPayOrderDetailDiv">
        <table class="confirmPayOrderDetailTable">
            <tr>
                <td>订单编号： </td>
                <td>${o.orderCode} <img width="32px" src="img/site/confirmOrderTmall.png"></td>
            </tr>
            <tr>
                 <td>卖家昵称： </td>
                <td>盗版天猫 <span class="confirmPayOrderDetailWangWangGif"></span></td>
            </tr>
            <tr>
                <td>收货信息： </td>
                <td>${o.address}, ${o.receiver}, ${o.mobile}, ${o.post}</td>
            </tr>
            <tr>
                <td>成交时间： </td>
                <td><fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
            </tr>
        </table>
    </div>
    <div class="confirmPayButtonDiv">
        <div class="confirmPayWarning">务必收到货后再点击确认收货, 否则可能钱货两空!</div>
        <a href="foreorderConfirmed?oid=${o.id}"><button class="confirmPayButton">确认收货</button></a>
    </div>
</div>
