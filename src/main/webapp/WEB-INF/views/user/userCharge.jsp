<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="bot wrap_recharge">
	<div class="div-recharge">
	   <div class="charge-top">
	   		<h4>충 전 안 내</h4>
	   		<p>- 이용금액 1시간 당 1000원</p>
	   		<p>- 시간 충전은 1시간 단위로 가능합니다.</p>
	   		<p>- 충전 후 환불이 불가합니다.</p>
		   	<div class="check-box">
          		<input type="checkbox"> <em>전체 동의</em>
          	</div>
	   </div>
	   <p style="text-align:left;text-indent:50px; font-size:18px;font-weight:900;color:#440977;">시간 충전</p>
          <div class="charge_time">
          		
          		<span class="btn_charge minus"><a href="javascript:void(0);">-</a></span>
          		<p>
               	<em id="chargeTime">1</em>시간
          		</p>
              <span class="btn_charge plus"><a href="javascript:void(0);">+</a></span>
          </div>
          <p class="charge-total-price">Total <em>1,000</em> 원</p>
          
          <div class="charge-btn-grp"> <!-- id= "orderBtn" -->
			<a href="javascript:void(0);" class="orderCharge" onclick="rechargeBtn();">충전하기</a>
			<a href="javascript:void(0);" onClick="removeTimeAll();">RESET</a>
		</div>
	</div>
</div>