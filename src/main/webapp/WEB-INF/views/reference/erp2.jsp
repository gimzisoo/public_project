<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매출관리</title>
<link rel="stylesheet" href="css/reference.css">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- jquery -->
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script type='text/javascript' src='//code.jquery.com/jquery-1.8.3.js'></script>
<!-- 날자관련 링크 -->
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">

</head>

<body>
 <div class="header">
        <!-- ********** 상단 왼쪽 영역 시작 ********** -->
        <div class="hd hd_lft">
            <!-- 로고 영역 -->
            <div class="logo">
                <img src="/images/logo_w.png" alt="로고">
            </div>
            <!-- 화면 전화 버튼 영역 -->
            <div class="btn_toggle">
                <ul>
                    <li class="btn btn_pos">
                        <a href="/pos">POS</a>
                    </li>
                    <li class="btn btn_ERP on">
                        <a href="/erp">ERP</a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- ********** 상단 왼쪽 영역 끝 ********** -->

        <!-- ********** 상단 오른쪽 영역 시작 ********** -->
        <div class="hd hd_rgt">
            <div class="btn_grp">
                <ul>
                    <li class="on"><a href="javascript:void(0);">알림</a></li>
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
                    <a href="/erp" onclick="navOn(this);">재고관리</a>                 
                </li>
                <li class="on">
                	<em class="arrow"><img src="/images/sub_nav.png" alt="화살표"></em>
                    <a href="/erp2" onclick="navOn(this);">매출관리</a>
                    <ul class="sub_nav">
		                <li><a href="#">매출내역</a></li>
		                <li><a href="#">주문내역</a></li>
		            </ul>
                </li>
                <li>
                	<em class="arrow"><img src="/images/sub_nav.png" alt="화살표"></em>
                    <a href="javascript:void(0);" onclick="navOn(this);">회원관리</a>
                </li>
            </ul>
        </div>
        <!-- ########## 네비게이션 영역 끝 ########## -->
    </div>


    <div class="content">
        <div class="cont_top" style="height: 100%;">
        <div class="header-content">
            <h2>
                <em><img src="/images/icon1.png" alt="아이콘"></em>
                재고관리
            </h2>
			<div class="button-group">
	            <img id="refresh" src="/images/refresh.png" alt="새로고침" style="width:20px">
	            <button id="check">조회</button>
	            <button id="Excel">엑셀</button>
	            <button id="delete">삭제</button>
	        </div>
		</div>
		
            <div class="inquiry">
                <table>
                    <tr>
                       <td class="tr_th">매출일자</td>
			            <td style="padding:0 10px;">
						    <div class="input-group date" style="display: inline-block; ">
						        <input type="text" class="form-control" id="startDate" readonly>
						        <span class="input-group-addon datepicker-icon" style="display: table-cell;">
						            <img src="/images/calendar.png" alt="아이콘" style="width: 10px;">
						        </span>
						    </div>
						    
						    <div style="display: inline-block; margin: 0 10px;">~</div>
						    
						    <div class="input-group date" style="display: inline-block;">
						        <input type="text" class="form-control" id="endDate" readonly>
						        <span class="input-group-addon datepicker-icon" style="display: table-cell;">
						            <img src="/images/calendar.png" alt="아이콘" style="width: 10px;">
						        </span>
						    </div>
						 </td>


                        <td class="tr_th">매출전표코드</td>
                        <td style="padding: 0 30px;">
                            <input type="text" class="paymName">
                            <button id="check">조회</button>
                        </td>

                    </tr>


                    <tr>
                        <td class="tr_th">매출유형상세</td>
                        <td style="padding: 0 30px;">
                            <select class="itemSelect">
                                <option value="1">PC이용</option>
                                <option value="2">상품</option>
                                <option value="3">기타</option>
                            </select>
                        </td>


                        <td class="tr_th">회원이름</td>
                        <td style="padding: 0 30px;">
                        	<input type="text" class="userName">                       	
                        </td>
                    </tr>
                </table>
            </div>
        </div>


        <div class="cont_area">
            <h2>
                <em><img src="/images/icon1.png" alt="아이콘"></em>
                재고품목
            </h2>
            <table style="width: 100%;border-color: #a49f9f;">
                <thead>
                    <th style="width: 5%;">순번</th>
                    <th style="width: 20%;">매출전표코드</th>
                    <th style="width: 15%;">매출일자</th>
                    <th style="width: 15%;">매출유형</th>
                    <th style="width: 15%;">매출유형상세</th>
                    <th style="width: 15%;">회원이름</th>
                    <th style="width: 15%;" class="amount-cell">매출액(단위 원)</th>
                </thead>

                <tbody class="paymTbody">
                    <tr>
                        <td>1</td>
                        <td>PAYM000001</td>
                        <td>2023-11-01</td>
                        <td>매출</td>
                        <td>상품</td>
                        <td>유성오</td>
                        <td class="amount-cell">￦3,000</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>ITEM000002</td>
                        <td></td>
                        <td>비품</td>
                        <td>2020-03-01</td>
                        <td>51EA</td>
                        <td class="amount-cell">￦100,000</td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                    </tr>
					<tr>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                    </tr>
                    <tr>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                        <td>내용</td>
                    </tr>                                        
                 </tbody>
            </table>
        </div>
    </div>
</body>


<script>
function navOn(element) {
    const navLi = $(element).parent();
    $(".main_nav li.on").removeClass("on");
    $(navLi).addClass("on");
}
</script>


<script src="js/erpmain.js"></script>

</html>