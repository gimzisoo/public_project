<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal-content">
	<span class="close" id="closeModalBtn">&times;</span>

	<ul class="tab">
		<li class="tab__item active">회원정보</li>
		<li class="tab__item">주문내역</li>
	</ul>
	<div class="tab__content-wrapper">
		<div id="tab1" class="tab__content active">
			<table class="table-fill">
				<tbody class="table-hover">
					<tr>
						<td class="text-left">좌석번호</td>
						<td class="text-left" id="userSeatNo">
						</td>
					</tr>
					<tr>
						<td class="text-left">회원아이디</td>
						<td class="text-left" id="userId">
						</td>
					</tr>
					<tr>
						<td class="text-left">시작시간</td>
						<td class="text-left" id="loginTime">
						</td>
					</tr>
					<tr>
						<td class="text-left">잔여시간</td>
						<td class="text-left" id="remainingTime">
						</td>
					</tr>
					<tr>
						<td class="text-left">회원가입일</td>
						<td class="text-left" id="regDate">
						</td>
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
				<tbody id="userOrder" class="table-hover">
				</tbody>
			</table>
		</div>
	</div>
</div>