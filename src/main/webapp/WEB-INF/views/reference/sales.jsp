<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매출내역</title>
</head>
<body>

    <div class="content" id="sales" style="display:block;">
        <div class="cont_top" style="height: 100%;">
        <div class="header-content">
            <h2>
                <img src="/images/icon1.png" alt="아이콘">
                매출 내역
            </h2>
			<div class="button-group">
	            <input type="button" class="refresh-button" onclick="refreshSalesPage()">
	            <button class="check" id="paymCheck" onclick="salesSearch()">조회</button>
	            <button class="Excel" id="excelDownload">
	            	<img src="/images/xlsx.png" alt="Excel Icon" style="vertical-align: middle; width:20px"> 다운로드
	            </button>
	        </div>
		</div>
		
            <div class="inquiry">
                <table>
                    <tr>
                       <td class="tr_th">매출 일자</td>
			            <td style="padding:0 10px;">
						    <div class="input-group date" style="display: inline-block; vertical-align: middle;">
						        <input type="text" class="form-control" id="startDate" readonly style="background-color: #d3d3d3; color: black; font-weight: bold;">
						        <span class="input-group-addon datepicker-icon" style="display: table-cell;">
						            <img src="/images/calendar.png" alt="아이콘" style="width: 10px;">
						        </span>
						    </div>
						    
						    <div style="display: inline-block; margin: 0 10px;">~</div>
						    
						    <div class="input-group date" style="display: inline-block; vertical-align: middle;">
						        <input type="text" class="form-control" id="endDate" readonly style="background-color: #d3d3d3; color: black; font-weight: bold;">
						        <span class="input-group-addon datepicker-icon" style="display: table-cell;">
						            <img src="/images/calendar.png" alt="아이콘" style="width: 10px;">
						        </span>
						    </div>
						 </td>


                        <td class="tr_th">매출 전표</td>
                        <td style="padding: 0 30px;">
                            <input type="text" class="paymName" id="salesCode"readonly style="background-color: #d3d3d3; outline: none;">
                            <button id="paymCodeFide">검색</button>
                        </td>

                    </tr>


                    <tr>
                        <td class="tr_th">주문 전표</td>
                        <td style="padding: 0 30px;">
                        	<input type="text" class="orderCode">                       	
                        </td>


                        <td class="tr_th">회원 이름</td>
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
                매출 목록
            </h2>            
         <form id="salesExcel" method="POST" action="/erp/salesExcel" >
            <table style="width: 100%;border-color: #a49f9f; border-collapse:collapse; display:block;">
                <thead>
                    <th style="width: 45px;">순번</th>
                    <th style="width: 130px;" class="sortable salesSortable" data-sort="매출전표">매출 코드</th>
                    <th style="width: 130px;" class="sortable salesSortable" data-sort="주문전표">주문 전표</th>
                    <th style="width: 125px;" class="sortable salesSortable" data-sort="매출일자">매출 일자</th>
                    <th style="width: 110px;" class="sortable salesSortable" data-sort="회원이름">회원 이름</th>
                    <th style="width: 98px;">구분 상태</th>
                    <th style="width: 121px;" class="amount-cell">매출액</th>
                    <th style="width: 143px;" class="amount-cell">순매출액</th>
                </thead>

                <tbody class="paymTbody paymScroll" id="paymTbody" varStatus="loop">
                   
                   	<c:forEach var="paym" items="${sales}">
                   		<tr>
                   			<td>${paym['index']}</td>
                   			<td>${paym['paymentId']}</td>
                   			<td>${paym['orderId']}</td>
                   			<td>${paym['paymentDate']}</td>
                   			<td>${paym['uname']}</td>
                   			<td class="paymType ${paym['type']}">${paym['type']}</td>
                   			<td>₩<fmt:formatNumber value="${paym['price']}" /></td>
                   			<td>₩<fmt:formatNumber value="${paym['netProfit']}" /></td>
                   		</tr>
                   	</c:forEach>
                 </tbody>
                 
   				<tbody class="total-row" style="background-color: #FFDCDC;">
				      <tr>
			            <td style="width: 543px; font-weight: bold; text-align: center;">순 매출 합계</td>

			            <td style="width: 364px; text-align: center;" id="netProfitTotalPrices">
			                <span id="formNetProfitTotalPrices"></span>
			            </td>
       				 </tr>
				</tbody> 
            </table>
        	 <input type="hidden" name="salesData" id="rowDataInput" />
		 </form>
        </div>
        
    </div>
   
   
   <div id="erpModal" class="modal" style="z-index:10; font-size:10px;">
  	 <div class="modal-content" style="width:700px; margin:-250px 0 0 -350px;">
	   <span class="close" id="closeModalBtn">&times;</span> 
	   
   <form name="searchSalesForm" id="searchSalesForm" method="multipart/form-data">
           <div class="header-content" style="margin-top: 20px;">
	           <h2>매출전표번호 조회</h2>
				<div class="button-group">
		            <button type="button" id="searchSalesBnt">조회</button>
		        </div>
		   </div>
	               <div class="inquiry" style="margin-top:10px">
                   <table>
                    <tr>
                       <td class="tr_th">매출 일자</td>
			            <td style="padding:0 10px; text-align: center;" colspan='3' >
						    <div class="input-group date" style="display: inline-block; vertical-align: middle;">
						        <input type="text" class="form-control" id="startDate3" name="startDate" readonly style="background-color: #d3d3d3; color: black; font-weight: bold;">
						        <span class="input-group-addon datepicker-icon" style="display: table-cell;">
						            <img src="/images/calendar.png" alt="아이콘" style="width: 10px;">
						        </span>
						    </div>
						    
						    <div style="display: inline-block; margin: 0 50px;">~</div>
						    
						    <div class="input-group date" style="display: inline-block; vertical-align: middle;">
						        <input type="text" class="form-control" id="endDate3" name="endDate" readonly style="background-color: #d3d3d3; color: black; font-weight: bold;">
						        <span class="input-group-addon datepicker-icon" style="display: table-cell;">
						            <img src="/images/calendar.png" alt="아이콘" style="width: 10px;">
						        </span>
						    </div>
						 </td>

                    </tr>
                    <tr>
                        <td class="tr_th">매출 유형</td>
                        <td style="padding: 0 30px;">
                            <select class="salesType" name="select" style="height:25px; margin-left:0px; width:200px;">
                                <option value="">전체</option>
                                <option value="PT001">PC이용</option>
                                <option value="PT002">메뉴주문</option>
                                <option value="PT003">기타</option>
                                <option value="PT004">주문취소</option>
                            </select>
                        </td>

                        <td class="tr_th">매출 전표</td>
                        <td style="padding: 0 30px;">
                            <input type="text" class="paymName" id="modalSalesCode" name="code" readonly style="background-color: #d3d3d3; outline: none;">
                        </td>

                    </tr>

                    <tr>
                        <td class="tr_th">주문 전표</td>
                        <td style="padding: 0 30px;">
                        	<input type="text" class="orderCode" id="modelCode" name="orderId">                       	
                        </td>

                        <td class="tr_th">회원 이름</td>
                        <td style="padding: 0 30px;">
                        	<input type="text" class="userName" id="modelName" name="unme">                       	
                        </td>
                    </tr>
                </table>
                
                </div>
           
          </form>     
        <div class="cont_area salesCode">
            <table  class="searchSalesCode" id="searchSalesCode" >
                <thead>
                    <th style="width: 45px;">순번</th>
                    <th style="width: 130px;" class="sortable salesSortable" data-sort="매출전표">매출 코드</th>
                    <th style="width: 130px;" class="sortable salesSortable" data-sort="주문전표">주문 전표</th>
                    <th style="width: 125px;" class="sortable salesSortable" data-sort="매출일자">매출 일자</th>
                    <th style="width: 110px;" class="sortable salesSortable" data-sort="회원이름">회원 이름</th>
                    <th style="width: 98px;">매출유형</th>
                    <th style="width: 121px;" class="amount-cell">매출액</th>
                </thead>
				
				<style>
				#tbody tr:hover {
			           background-color: #e6e6fa; /* 옅은 보라색 */
			           cursor: pointer;
			       }
				.cont_area.salesCode {
					height:50%;
					margin-top: 20px;
				}
				.searchSalesCode tr {
			       height: 40px;
				}
				
				.searchSalesCode {
				  display: block;
				  overflow: auto;
				  overflow-y:scroll; 
				  width: 100%;
				  height:100%;
				  border-color: #a49f9f; 
				  border-collapse:collapse;
				  display:block;  
				  table-layout: fixed;
				}
				
				.searchSalesCode::-webkit-scrollbar {
				  display: none;
				}
				.searchSalesCode thead {
				  position: sticky;
				  top: 0;
				  background-color: #fff; /* 배경색 설정 */
				  
				}
				
				</style>
				
               		<tr id="sales-item" style="display:none;">
               			<td class="rownum">순번</td>
               			<td class="paymentId">매출코드</td>
               			<td class="orderId">주문전표</td>
               			<td class="paymentDate">매출일자</td>
               			<td class="uname">회원이름</td>
               			<td class="type">매출유형</td>
               			<td class="netProfit">매출액</td>
               		</tr>
				
                <tbody id="tbody">

                 </tbody>
            </table>
            
            <button type="button" id="selectSalesCodeBnt" style="margin-top:10px">선택</button>
            
        </div>
		</div>
	</div>
</body>
</html>