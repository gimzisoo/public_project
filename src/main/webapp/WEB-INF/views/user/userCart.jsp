<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="bot wrap_cart">
    <div class="addCart empty">
    	<span class="empty_cart"></span>
    	<ul>
    	</ul>
    </div>
    <span class="scroll"></span>
    <p class="menu-total-price">Total <em>1,000</em> 원</p>
    <div class="cart-btn-grp"> <!-- id= "orderBtn" -->
		<a href="javascript:void(0);" class="orderMenu" onClick="orderBtn();">주문하기</a>
		<a href="javascript:void(0);" onClick="removeCartAll();">RESET</a>
	</div>
</div>