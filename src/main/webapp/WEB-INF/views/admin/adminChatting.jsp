<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="wrap_chat_main">
	<ul id="chatList">
	</ul>
	<footer>
		<textarea placeholder="메세지를 입력해주세요."  id="chatInputBox"></textarea>
		<select id="seatSelector" style="margin-top:20px;width: 100px;height: 40px;text-indent: 10px;">
		  <option selected>좌석번호</option>
		</select>
		<a href="javascript:void(0);" id="send_chat_button">Send</a>
	</footer>
</div>