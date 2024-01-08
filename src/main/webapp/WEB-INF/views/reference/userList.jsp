<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원관리</title>
</head>
<link rel="stylesheet" href="/css/reference.css"> 
<body>
<div class="header">
        <!-- ********** 상단 왼쪽 영역 시작 ********** -->
        <div class="hd hd_lft">
            <!-- 로고 영역 -->
            <div class="logo">
                <a href="/admin"><img src="/images/logo_w.png" alt="로고"></a>
            </div>
            <!-- 화면 전화 버튼 영역 -->
            <div class="btn_toggle">
                <ul>
                    <li class="btn btn_pos">
                        <a href="/admin">POS</a>
                    </li>
                    <li class="btn btn_ERP on">
                        <a href="/erp/stock">ERP</a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- ********** 상단 왼쪽 영역 끝 ********** -->

        <!-- ********** 상단 오른쪽 영역 시작 ********** -->
        <div class="hd hd_rgt">
            <div class="btn_grp">
                <ul>
                    <li><a href="javascript:void(0);">알림</a></li>
                    <li><a href="javascript:void(0);">채팅</a></li>
                    <li><a href="javascript:void(0);">좌석</a></li>
                    <li><a href="javascript:void(0);">대시보드</a></li>
                    <li><a href="javascript:void(0);">설정</a></li>
                </ul>
            </div>
        </div>
        <!-- ********** 상단 오른쪽 영역 끝 ********** -->


        <!-- ########## 네비게이션 영역 시작 ########## -->
        <div class="nav">
            <ul class="main_nav">
                <li>
                    <em class="arrow"><img src="/images/sub_nav.png" alt="화살표"></em>
                    <a href="/erp/stock" onclick="navOn(this);">재고관리</a>
                   	<ul class="sub_nav">
		                <li><a href="javascript:void(0);" onclick="changeItemPage();">재고</a></li>
		                <li><a href="javascript:void(0);" onclick="changeInventoryStatusPage();">입·출고현황</a></li>
		            </ul>
                </li>
                <li>
                	<em class="arrow"><img src="/images/sub_nav.png" alt="화살표"></em>
                    <a href="/erp/sales" onclick="navOn(this);">매출관리</a>
                </li>
                <li class="on">
                	<em class="arrow"><img src="/images/sub_nav.png" alt="화살표"></em>
                    <a href="/erp/user" onclick="navOn(this);">회원관리</a>
                   	<ul class="sub_nav">
		                <li><a href="javascript:void(0);" onclick="changeItemPage();">회원목록</a></li>
		            </ul>
                </li>
            </ul>
        </div>
        <!-- ########## 네비게이션 영역 끝 ########## -->
    </div>
    

    <div class="content" id="item" style="display:block;">
        <div class="cont_top" style="height: 100%;">
        <div class="header-content">
            <h2>
                <img src="/images/icon1.png" alt="아이콘">
                회원 관리
            </h2>
			<div class="button-group">
				<input type="button" class="refresh-button" onclick="refreshStockPage()">
	            <button class="check" id="itemCheck" onclick="searchData()">조회</button>
	            <button class="insert" id="stockBtn">등록</button>
	        </div>
		</div>
		
            <div class="inquiry">
                <table>
                    <tr>
                       <td class="tr_th">가입 일자</td>
			            <td style="padding:0 10px;">
						    <div class="input-group date" style="display: inline-block; vertical-align: middle; ">
						        <input type="text" class="form-control" id="startDate" readonly style="background-color: #d3d3d3; color: black; font-weight: bold; z-index: auto;">
						        <span class="input-group-addon datepicker-icon" style="display: table-cell;">
						            <img src="/images/calendar.png" alt="아이콘" style="width: 10px;">
						        </span>
						    </div>
						    
						    <div style="display: inline-block; margin: 0 10px;">~</div>
						    
						    <div class="input-group date" style="display: inline-block; vertical-align: middle;">
						        <input type="text" class="form-control" id="endDate" readonly style="background-color: #d3d3d3; color: black; font-weight: bold; z-index: auto;">
						        <span class="input-group-addon datepicker-icon" style="display: table-cell;">
						            <img src="/images/calendar.png" alt="아이콘" style="width: 10px;">
						        </span>
						    </div>
						 </td>


                        <td class="tr_th">회원 이름</td>
                        <td style="padding: 0 30px;">
                            <input type="text" class="itemName">
                        </td>

                    </tr>


                    <tr>
                        <td class="tr_th">휴면 계정</td>
                        <td style="padding: 0 30px;">
                            <select class="itemSelect">                            	
                                <option value="">모두보기</option>
                                <option value="상품">사용중</option>
                                <option value="비품">휴면계정</option>

                            </select>
                        </td>


                        <td class="tr_th">잠김 상태</td>
                        <td style="padding: 0 30px;">
                            <select class="itemSituation">
                                <option value="">모두보기</option>
                                <option value="1">O</option>
                                <option value="2">X</option>
                            </select>
                        </td>                       
                    </tr>
                </table>
            </div>
        </div>

        <div class="cont_area">
            <h2>
                <em><img src="/images/icon1.png" alt="아이콘"></em>
                회원 목록
            </h2>        
            <table style="width: 100%;border-color: #a49f9f; border-collapse:collapse; display:block;">
                <thead>
			        <th style="width: 50px;">순번</th>
			        <th style="width: 135px;">회원아이디</th>
			        <th style="width: 120px;">회원이름</th>
			        <th style="width: 142px;">전화번호</th>
			        <th style="width: 115px;">휴면계정여부</th>
			        <th style="width: 80px;">잠금여부</th>
			        <th style="width: 130px;">SNS 로그인 여부</th>
			        <th style="width: 130px;">회원가입 일자</th>
			    </thead>
                <tbody class="userTbody userScroll" id="userTbody" varStatus="loop">
                	<c:forEach var="user" items="${userList}">
						    <tr>
						        <td>${user['index']}</td>
						        <td>${user['USER_ID']}</td>
						        <td>${user['UNAME']}</td>
						        <td>${user['PHONE_NUMBER']}</td>
						        <td class="${user['ACCOUNT_STATUS'] eq '휴면계정' ? 'dormant' : 'active'}">
						            <c:choose>
						                <c:when test="${user['ACCOUNT_STATUS'] eq '휴면계정'}">
						                    휴면계정
						                </c:when>
						                <c:otherwise>
						                    사용중
						                </c:otherwise>
						            </c:choose>
						        </td>
						        <td>
						            <c:choose>
						                <c:when test="${user['LOGIN_FAILURE'] eq 0}">
						                    <span style="color: blue;">X</span>
						                </c:when>
						                <c:when test="${user['LOGIN_FAILURE'] ge 5}">
						                    <span style="color: red;">O</span>
						                </c:when>
						                <c:otherwise>
						                    ${user['LOGIN_FAILURE']}
						                </c:otherwise>
						            </c:choose>
						        </td>
								<td style="color: ${user['OAUTH'] eq 'kakao' ? '#FEE500' : 'inherit'}">
								    <c:choose>
								        <c:when test="${user['OAUTH'] eq 'kakao'}">
								            kakao
								        </c:when>
								        <c:otherwise>
								            일반회원
								        </c:otherwise>
								    </c:choose>
								</td>
						        <td>${user['REG_DATE']}</td>
                	</c:forEach>
                </tbody>

            </table>
        </div>
    </div>

    
</body>
<jsp:include page="/WEB-INF/views/common/commonJS.jsp"></jsp:include>
</html>