<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="bot chat-wrap">
   <div class="wrap_chat">
      <div class="wrap_chat_main">
         <ul id="chatList">
         </ul>
         <footer>
            <textarea placeholder="메세지를 입력해주세요." id="chatInputBox"></textarea>
            <a href="javascript:void(0);" id="send_chat_button">Send</a>
         </footer>
      </div>
   </div>
</div>