<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>erp</title>
</head>
<link rel="stylesheet" href="css/reference02.css">
<link rel="stylesheet" href="css/chat.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="/mqtt/mqtt.min.js"></script>

<body>
	<div class="wrap">
		<!-- ########## 상단 헤더 영역 끝 ########## -->
		<div class="header">
			<!-- ********** 상단 왼쪽 영역 시작 ********** -->
			<div class="hd hd_lft">
				<!-- 로고 영역 -->
				<div class="logo">
					<img src="/images/logo_p.png" alt="로고">
				</div>
				<!-- 화면 전화 버튼 영역 -->
				<div class="dtn_toggle">
					<ul>
						<li class="btn btn_pos on"><a href="/pos">POS</a></li>
						<li class="btn btn_ERP"><a href="#">ERP</a></li>
					</ul>
				</div>
			</div>
			<!-- ********** 상단 왼쪽 영역 끝 ********** -->

			<!-- ********** 상단 오른쪽 영역 시작 ********** -->
			<div class="hd hd_rgt">
				<div class="btn_grp">
					<ul>
						<li class="on"><a href="javascript:void(0);"
							onClick="showAlert()">알림</a></li>
						<li><a href="javascript:void(0);" onClick="showChat()">채팅</a></li>
						<li><a href="javascript:void(0);">좌석</a></li>
						<li><a href="javascript:void(0);">대시보드</a></li>
						<li><a href="javascript:void(0);">설정</a></li>
					</ul>
				</div>
			</div>
			<!-- ********** 상단 오른쪽 영역 끝 ********** -->
		</div>
		<!-- ########## 상단 헤더 영역 끝 ########## -->

		<div class="wrap_cont">
			<div class="cont_top">
				<ul>
					<li>
						<p>이용중인 좌석</p> <em>22</em>
					</li>

					<li>
						<p>잔여 좌석</p> <em>28</em>
					</li>
				</ul>
			</div>

			<div class="seat_grp">
				<div class="seat">
					<ul>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p>
						</li>
					</ul>
				</div>

				<div class="seat seat_end">
					<ul>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p>
						</li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
					</ul>
				</div>

				<div class="seat">
					<ul>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
					</ul>
				</div>

				<div class="seat seat_end">
					<ul>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
					</ul>
				</div>

				<div class="seat">
					<ul>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
					</ul>
				</div>

				<div class="seat seat_end">
					<ul>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
					</ul>
				</div>

				<div class="seat">
					<ul>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
					</ul>
				</div>

				<div class="seat seat_end">
					<ul>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
					</ul>
				</div>

				<div class="seat">
					<ul>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
						<li><a href="javascript:void(0);"></a>
							<p class="uid"></p>
							<p class="time"></p></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="wrap_alert" id="orderList">
			<button class="accordion">11번 좌석 주문내역 : 콜라 1개, 커피 1개</button>
				<div class="panel">
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
						do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
						enim ad minim veniam, quis nostrud exercitation ullamco laboris
						nisi ut aliquip ex ea commodo consequat.</p>
					<button>주문 확인</button>
				</div>
		</div>

		<div class="wrap_chat">
			<div class="wrap_chat_main">
				<ul id="chatList">
				</ul>
				<footer>
					<textarea placeholder="Type your message"  id="chatInputBox"></textarea>
					<select id="seatSelector">
					  <option selected>좌석번호</option>
					</select>
					<a href="javascript:void(0);" id="send_chat_button">Send</a>
				</footer>
			</div>
		</div>
		
	</div>
	<!-- 모달 창 영역 -->
	<div id="modal" class="modal">
		<div class="modal-content">
			<span class="close" id="closeModalBtn">&times;</span>

			<ul class="tab">
				<li class="tab__item active">회원정보</li>
				<li class="tab__item">주문내역</li>
			</ul>
			<div class="tab__content-wrapper">
				<div id="tab1" class="tab__content active">
					<table class="table-fill">
						<thead>
							<tr>
								<th class="text-left">좌석번호</th>
								<th class="text-left">데이터</th>
							</tr>
						</thead>
						<tbody class="table-hover">
							<tr>
								<td class="text-left">회원아이디</td>
								<td class="text-left">데이터</td>
							</tr>
							<tr>
								<td class="text-left">시작시간</td>
								<td class="text-left">데이터</td>
							</tr>
							<tr>
								<td class="text-left">잔여시간</td>
								<td class="text-left">데이터</td>
							</tr>
							<tr>
								<td class="text-left">사용시간</td>
								<td class="text-left">데이터</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="tab2" class="tab__content">
				<table class="table-fill">
						<thead>
							<tr>
								<th>판매시간</th>
								<th>품목 이름</th>
								<th>수량</th>
								<th>총금액</th>
							</tr>
						</thead>
						<tbody class="table-hover">
							<tr>
								<td class="text-left">2023-10-10</td>
								<td class="text-left">라면</td>
								<td class="text-left">3</td>
								<td class="text-left">15000</td>
							</tr>
							<tr>
								<td class="text-left">2023-10-10</td>
								<td class="text-left">라면</td>
								<td class="text-left">3</td>
								<td class="text-left">15000</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="js/reference.js"></script>
<script type="text/javascript" src="/mqtt/mqttAdmin.js"></script>
<script type="text/javascript" src="/js/pos.js"></script>

</html>