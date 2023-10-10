<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-16
  Time: 下午 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="estDate" value="${estDate}" />

<div class="payedDiv">
    <div class="payedTextDiv">
        <img src="img/site/paySuccess.png">
        <span>付款成功</span>
    </div>
    <div class="payedAddressInfo">
        <ul>
            <li>收货地址: ${o.address} ${o.receiver} ${o.mobile}</li>
            <li>实付: <span class="payedInfoPrice">
                ￥<fmt:formatNumber type="number" value="${param.total}" minFractionDigits="2" />
                </span>
            </li>
            <li>预计送达时间 <fmt:formatDate value="${estDate}" pattern="yyyy-MM-dd"/></li>
        </ul>

        <div class="paedCheckLinkDiv">
            您可以
            <a class="payedCheckLink" href="forebought">查看已经买到的宝贝</a>
            <a class="payedCheckLink" href="forebought">查看交易详情</a>
        </div>
    </div>

    <div class="payedSeperateLine"></div>

    <div class="warningDiv">
        <img src="img/site/warning.png">
        <b>安全提醒: </b>下单后, <span class="redColor boldWord">不会发货, 收到任何有关消息都是骗子发的!</span>  盗版天猫不存在系统升级，订单异常问题，谨防诈骗!
    </div>
</div>