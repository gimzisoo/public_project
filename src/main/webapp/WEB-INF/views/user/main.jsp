<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %><!-- 태크라이브러리 -->

<sec:authorize access="isAuthenticated()">
<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 화면</title>
</head>
<link rel="stylesheet" href="/css/user.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="/plugins/jquery/jquery.min.js"></script>
<link rel="stylesheet" href="/css/clientChat.css">
<script type="text/javascript" src="/mqtt/mqtt.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<body>
<%-- <input type="hidden" id="userInfo" value="${map.rs}"> --%>
   <div class="body-wrap">
      <div class="cont-top-wrap">
         <div class="cont-header">
            <span></span>
            <p>PUBLIC</p>
         </div>
         <div class="seat-wrap">   
            <h4>
               NO.
               <span id="seatNum">${principal.user.seatNo }</span>
            </h4>
            <div class="seat-btn">
               <ul>
                  <li><a href="javascript:void(0);">자리이동</a></li>
                  <li id="logoutBtn"><a href="javascript:void(0);">사용 종료</a></li>
               </ul>
            </div>
         </div>
         <div class="info-wrap">
            <em id="userId">${principal.user.userId }</em> 님
         </div>
         <div class="time-wrap">
            <p id="remainingTime">0</p>
         </div>
         <div class="nav-btn-wrap">
            <ul>
               <li id="orderBtn22"><a href="javascript:void(0);" onClick="navBtn(this);"></a>주문</li>
               <li id ="rechargeBtn"><a href="javascript:void(0);" onClick="navBtn(this);"></a>충전</li>
               <li id ="chattingBtn"><a href="javascript:void(0);" onClick="navBtn(this);"></a>채팅</li>
            </ul>
         </div>
      </div>
      <div class="cont-bot-wrap">
         <!-- 채팅 영역 -->
         <jsp:include page="userChat.jsp"></jsp:include>
         <!-- 장바구니 영역 -->
         <jsp:include page="userCart.jsp"></jsp:include>
         <!-- 시간충전 영역 -->
         <jsp:include page="userCharge.jsp"></jsp:include>
	     <div class="cont-modal-wrap" style="display:none;">
	         <jsp:include page="userFood.jsp"></jsp:include>
	     </div>
      	 <jsp:include page="userPayment.jsp"></jsp:include>
   </div>
</body>
<script type="text/javascript" src="/mqtt/mqttClient.js"></script>
<script type="text/javascript" src="/js/user.js"></script>
</html>