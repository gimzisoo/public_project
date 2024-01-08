<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin</title>
</head>
<jsp:include page="/WEB-INF/views/common/commonCSS.jsp"></jsp:include>
<!-- 관리자 좌석현황 영역 CSS -->
<link rel="stylesheet" href="css/reference02.css"> 
<body>

	<div class="wrap">
        <!-- ########## 상단 헤더 영역 끝 ########## -->
        <div class="header">
            <!-- ********** 상단 왼쪽 영역 시작 ********** -->
            <div class="hd hd_lft">
                <!-- 로고 영역 -->
                <div class="logo">
                    <a href="/admin"><img src="/images/logo_p.png" alt="로고"></a>
                </div>
                <!-- 화면 전화 버튼 영역 -->
                <div class="btn_toggle">
                    <ul>
                        <li class="btn btn_pos on">
                            <a href="/admin">POS</a>
                        </li>
                        <li class="btn btn_ERP">
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
                        <li id="alertIcon"><a href="javascript:void(0);" onClick="showAlert()">알림</a></li>
						<li id="chatIcon"><a href="javascript:void(0);" onClick="showChat()">채팅</a></li>
                        <li><a href="javascript:void(0);" onclick="changeAdminPage();">좌석</a></li>
                        <li><a href="javascript:void(0);" onclick="changeAdminPage();">대시보드</a></li>
                        <li><a href="javascript:void(0);" onclick="addFoodPage();">설정</a></li>
                    </ul>
                </div>
            </div>
            <!-- ********** 상단 오른쪽 영역 끝 ********** -->
        </div>
        <!-- ########## 상단 헤더 영역 끝 ########## -->

		<!-- 좌석현황, 대시보드 출력되는 영역 -->
		<div class="wrap_cont">
			<jsp:include page="adminMain.jsp"></jsp:include>
			<jsp:include page="adminDashboard.jsp"></jsp:include>
			<jsp:include page="adminFood.jsp"></jsp:include>
		</div>

		<!-- 관리자 알림창 영역 -->
        <div class="wrap_alert" id = "orderList">
            <jsp:include page="adminAlert.jsp"></jsp:include>
        </div>
        
        <!-- 관리자 채팅창 영역 -->
        <div class="wrap_chat">
            <jsp:include page="adminChatting.jsp"></jsp:include>
        </div>
    </div>
    

    <!-- 모달 창 영역 -->
    <div id="infoModal" class="modal">
         <jsp:include page="adminModal.jsp"></jsp:include>
    </div>
</body>

<jsp:include page="/WEB-INF/views/common/commonJS.jsp"></jsp:include>

</html>