<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-08-13
  Time: 下午 8:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<div class="buyPageDiv">
  <form action="forecreateOrder" method="post">
    <div class="buyFlow">
      <img class="pull-left" src="img/site/simpleLogo.png">
      <img class="pull-right" src="img/site/buyflow.png">
      <div style="clear: both"></div>
    </div>
    <div class="address">
      <div class="addressTip">请填写收货地址</div>
      <div>
        <table class="addressTable">
          <tr>
            <td class="firstColumn">详细地址<span class="redStar">*</span></td>
            <td><textarea name="address" placeholder="请如实填写收货地址，具体到门牌号"></textarea></td>
          </tr>
          <tr>
            <td>邮政编码</td>
            <td><input name="post" placeholder="若不清楚邮政编码可以填写000000" type="text"></td>
          </tr>
          <tr>
            <td>收货人姓名<span class="redStar">*</span></td>
            <td><input name="receiver" placeholder="长度不超过25个字符" type="text"></td>
          </tr>
          <tr>
            <td>手机号码<span class="redStar">*</span></td>
            <td><input name="mobile" type="text" placeholder="请输入11位手机号"></td>
          </tr>
        </table>
      </div>
    </div>
    <div class="productList">
      <div class="productListTip">确认订单信息</div>
      <table class="productListTable">
        <thead>
          <tr>
            <th colspan="2" class="productListTableFirstColumn">
              <img class="tmallbuy" src="img/site/tmallbuy.png">
              <a class="marketLink" href="#nowhere">店铺：盗版天猫</a>
              <a class="wangwanglink" href="#nowhere"><span class="wangwangGif"></span></a>
            </th>
            <th>单价</th>
            <th>数量</th>
            <th>小计</th>
            <th>配送方式</th>
          </tr>
          <tr class="rowborder">
            <td colspan="2"></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
        </thead>
        <tbody class="productListTableTbody">
          <c:forEach items="${ois}" var="oi" varStatus="st">
            <tr class="orderItemTR">
              <td class="orderItemFirstTD"><img class="orderItemImg" src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg"></td>
              <td class="orderItemProductInfo">
                <a href="foreproduct?pid=${oi.product.id}" class="orderItemProductLink">
                  ${oi.product.name}
                </a>
                <img src="img/site/creditcard.png" title="信用卡支付support">
                <img src="img/site/7day.png" title="消费者保障服务-七天无理由退货">
                <img src="img/site/promise.png" title="承诺如实描述">
              </td>
              <td>
                <span class="orderItemProductPrice">￥<fmt:formatNumber type="number" value="${oi.product.promotePrice}" minFractionDigits="2"/></span>
              </td>
              <td>
                <span class="orderItemProductNumber">${oi.number}</span>
              </td>
              <td>
                <span class="orderItemUnitSum">￥<fmt:formatNumber type="number" value="${oi.number * oi.product.promotePrice}" minFractionDigits="2"/></span>
              </td>
              <c:if test="${st.count == 1}">
                <td rowspan="5" class="orderItemLastTD">
                  <label class="orderItemDeliveryLabel">
                    <input type="radio" value="" checked="checked">
                    普通配送
                  </label>
                  <select class="orderItemDeliverySelect" class="form-control">
                    <option>快递 (包邮)</option>
                  </select>
                </td>
              </c:if>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <div class="orderItemSumDiv">
        <div class="pull-left">
          <span class="leaveMessageText">给卖家留言:</span>
          <span>
            <img class="leaveMessageImg" src="img/site/leaveMessage.png">
          </span>
          <span class="leaveMessageTextareaSpan">
            <textarea name="userMessage" class="leaveMessageTextarea"></textarea>
            <div>
              <span>最多留言200字</span>
            </div>
          </span>
        </div>
        <span class="pull-right">店铺合计(包括运费): ￥<fmt:formatNumber type="number" value="${total}" minFractionDigits="2"/></span>
      </div>

    </div>
    <div class="orderItemTotalSumDiv">
      <div class="pull-right">
        <span>实付: </span>
        <span class="orderItemTotalSumSpan">￥<fmt:formatNumber type="number" value="${total}" minFractionDigits="2"/></span>
      </div>
    </div>
    <div class="submitOrderDiv">
      <button type="submit" class="submitOrderButton">提交订单</button>
    </div>
  </form>
</div>
