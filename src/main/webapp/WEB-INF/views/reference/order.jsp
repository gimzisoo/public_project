<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문관리</title>
</head>
<body>

    <div class="content" id="order" style="display:none;">
        <div class="cont_top" style="height: 100%;">
        <div class="header-content">
            <h2>
                <img src="/images/icon1.png" alt="아이콘">
                주문 내역
            </h2>
			<div class="button-group">
	            <input type="button" class="refresh-button" onclick="refreshOrderPage()">
	            <button class="check" id="paymCheck" onclick="orderSearch()">조회</button>
	            <button class="Excel" id="Excel">
	            	<img src="/images/xlsx.png" alt="Excel Icon" style="vertical-align: middle; width:20px"> 다운로드
	            </button>
	        </div>
		</div>
		
            <div class="inquiry">
                <table>
                    <tr>
                       <td class="tr_th">주문 일자</td>
			            <td style="padding:0 10px;">
						    <div class="input-group date" style="display: inline-block; vertical-align: middle;">
						        <input type="text" class="form-control" id="startDate2" readonly style="background-color: #d3d3d3; color: black; font-weight: bold;">
						        <span class="input-group-addon datepicker-icon" style="display: table-cell;">
						            <img src="/images/calendar.png" alt="아이콘" style="width: 10px;">
						        </span>
						    </div>
						    
						    <div style="display: inline-block; margin: 0 10px;">~</div>
						    
						    <div class="input-group date" style="display: inline-block; vertical-align: middle;">
						        <input type="text" class="form-control" id="endDate2" readonly style="background-color: #d3d3d3; color: black; font-weight: bold;">
						        <span class="input-group-addon datepicker-icon" style="display: table-cell;">
						            <img src="/images/calendar.png" alt="아이콘" style="width: 10px;">
						        </span>
						    </div>
						 </td>


                        <td class="tr_th">주문 전표</td>
	                        <td class="changePadding" style="padding: 0 30px;">
	                            <input type="text" class="orderCodes" readonly style="background-color: #d3d3d3; outline: none;">
	                            <button id="orderCodeFide">검색</button>
	                        </td>

                    </tr>


                    <tr>
                        <td class="tr_th">결재 방법</td>
                        <td style="padding: 0 30px;">
                            <select class="orderSelect">
                                <option value="">모두보기</option>
                                <option value="현금">현금</option>
                                <option value="카드">카드</option>
                                <option value="간편결제">간편결제</option>

                            </select>
                        </td>


                        <td class="tr_th">상태 구분</td>
                        <td  class="changePadding"style="padding: 0 30px;">
						    <label style="font-size: 13px"><input type="radio" name="orderOption" value="" checked> 모두 보기</label>
                            <label style="font-size: 13px"><input type="radio" name="orderOption" value="PC이용"> PC이용</label>
						    <label style="font-size: 13px"><input type="radio" name="orderOption" value="메뉴주문"> 메뉴주문</label>
                        </td>
                    </tr>
                </table>
            </div>
        </div>


        <div class="cont_area">
            <h2>
                <em><img src="/images/icon1.png" alt="아이콘"></em>
                주문 목록
            </h2>            
            <table style="width: 100%;border-color: #a49f9f; border-collapse:collapse; display:block;">
                <thead>
                    <th style="width: 45px;">순번</th>
                    <th style="width: 140px;" class="sortable orderSortable" data-sort="주문전표">주문 전표</th>
                    <th style="width: 140px;" class="sortable orderSortable" data-sort="일자">주문일자</th>
                    <th style="width: 125px;">구분</th>
                    <th style="width: 85px;"class="sortable orderSortable" data-sort="건수">건수</th>
                    <th style="width: 140px;" class="amount-cell">총 금액</th>
                    <th style="width: 110px;">결제 방법</th>
                    <th style="width: 118px;" >결재 상태</th>
                </thead>

                <tbody class="orderTbody orderScroll" id="orderTbody" varStatus="loop">
                    
                    <c:forEach var="order" items="${order}">
                    <tr>
                    	<td>${order['index']}</td>
                    	<td onclick="orderDetail('${order['orderId']}')" class="clickable-cell">${order['orderId']}</td>
                    	<td>${order['orderDate']}</td>
                    	<td>${order['type']}</td>
                    	<td class="order-data" data-order="${order['quantity']}">${order['quantity']} EA</td>
                    	<td>₩<fmt:formatNumber value="${order['price']}" /></td>
                    	<td>${order['paymentMethod']}</td>
                    	<td class="orderStatus ${order['paymentStatus']}">${order['paymentStatus']}</td>
                    </tr>
                    </c:forEach>
                 </tbody>                 
                <tbody class="total-row" style="background-color: #FFDCDC;">
				      <tr>
			            <td style="width: 452px; font-weight: bold; text-align: center;">합계</td>
			            <td style="width: 454px; text-align: center;" id="orderTotalPrice">
			                <span id="formOrderTotalPrice"></span>
			            </td>
       				 </tr>
				</tbody>
            </table>
        </div>

		  <div class="detail" style="display:none;">
	        <span class="orderDetail"></span>
	        
		        <div class="cont_area orderDetail">
		            <h2>
		                <em><img src="/images/icon1.png" alt="아이콘"></em>
		                상세
		            </h2>            
		            <table style="width: 100%;border-color: #a49f9f; border-collapse:collapse; display:block;">
		                <thead>
		                    <th style="width: 45px;">순번</th> 
		                    <th style="width: 130px;">주문 전표</th>
		                    <th style="width: 125px;">주문일자</th>
		                    <th style="width: 100px;">주문자</th>
		                    <th style="width: 170px;">품목명</th>
		                    <th style="width: 78px;">수량</th>
		                    <th style="width: 110px;">단가</th>
		                    <th style="width: 128px;">총 금액</th>
		                </thead>
		
		                <tbody class="orderDetailTbody orderDetailScroll" id="orderDetailTbody" varStatus="loop">
		                  	<c:forEach var="detail" items="${orderView}">
			                    <tr>
			                    	<td>${detail['index']}</td>
			                    	<td>${detail['orderId']}</td>
			                    	<td>${detail['orderDate']}</td>
			                    	<td>${detail['uname']}</td>
			                    	<td>${detail['itemName']}</td>
			                    	<td>${detail['quantity']}</td>
			                    	<td>₩<fmt:formatNumber value="${detail['paymentPrice']}" /></td>			                    
			                    	<td>₩<fmt:formatNumber value="${detail['price']}" /></td>			                    
			                    </tr>
		                    </c:forEach>
		                 </tbody>
		            </table>
		        </div>
	        </div>

    </div>
    
</body>
</html>