<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <div class="modal modal-payment">
	<div class="modal-paymentList">
		<span onclick="closePayment();"></span>
		<h3>결제 방식</h3>
		<ul>
			<li><a href="javascript:void(0);" onClick="payment(1);">카드결제</a></li>
			<li><a href="javascript:void(0);" onClick="payment(2);">현금결제</a></li>
			<li><a href="javascript:void(0);" onClick="payment(3);">간편결제</a></li>
		</ul>
	</div>
	<div class="modal-order">
		<p>총 금액 <em id="total-price"></em>원 </br>결제하시겠습니까?</p>
		<div class="order-btn-list">
			<ul>
				<li class="menu-order"><a href="javascript:void(0);" onclick="order()">결제하기</a></li>
				<li class="recharge-order"><a href="javascript:void(0);" onclick="recharge()">결제하기</a></li>
				<li><a href="javascript:void(0);" onclick="cancle()">취소</a></li>
			</ul>
		</div>
	</div>
</div>