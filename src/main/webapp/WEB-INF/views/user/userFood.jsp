<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal modal-food">
   <div class="menu-category">
      <ul>
         <li id="menu01" class="on"><a href="javascript:void(0);" onclick="showCategory(1);">밥</a></li>
         <li id="menu02"><a href="javascript:void(0);"  onclick="showCategory(2);">라면</a></li>
         <li id="menu03"><a href="javascript:void(0);"  onclick="showCategory(3);">음료</a></li>
         <li id="menu04"><a href="javascript:void(0);"  onclick="showCategory(4);">스낵</a></li>
      </ul>
   </div>
   <div class="cont-food">
      <!-- 밥 종류 리스트 -->
      <div class="food">
         <ul class="food-list">
         </ul>
      </div>
   </div>
</div>