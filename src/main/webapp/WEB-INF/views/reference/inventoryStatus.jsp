<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>입·출고현황</title>
</head>

<body>
    <div class="content" id="inventoryStatus" style="display:none;">
        <div class="cont_top" style="height: 100%;">
        <div class="header-content">
            <h2>
                <img src="/images/icon1.png" alt="아이콘">
                입·출고 현황
            </h2>
			<div class="button-group">
	            <input type="button" class="refresh-button" onclick="refreshStatusPage()">
	            <button class="check" id="testCheck" onclick="statusSearch()">조회</button>
	            <button class="insert" id="stickBtn">등록</button>
			    <button class="Excel" id="excelDownloadBnt" type="button">
    				<img src="/images/xlsx.png" alt="Excel Icon" style="vertical-align: middle; width:20px">
    				다운로드
    			</button>
	        </div>
		</div>
		
            <div class="inquiry">
                <table>
                    <tr>
                       <td class="tr_th">일자</td>
			            <td style="padding:0 10px;">
						    <div class="input-group date" style="display: inline-block; vertical-align: middle;">
						        <input type="text" class="form-control" id="startDate2" readonly style="background-color: #d3d3d3; color: black; font-weight: bold; z-index: auto;">
						        <span class="input-group-addon datepicker-icon" style="display: table-cell;">
						            <img src="/images/calendar.png" alt="아이콘" style="width: 10px;">
						        </span>
						    </div>
						    
						    <div style="display: inline-block; margin: 0 10px;">~</div>
						    
						    <div class="input-group date" style="display: inline-block; vertical-align: middle;">
						        <input type="text" class="form-control" id="endDate2" readonly style="background-color: #d3d3d3; color: black; font-weight: bold; z-index: auto;">
						        <span class="input-group-addon datepicker-icon" style="display: table-cell;">
						            <img src="/images/calendar.png" alt="아이콘" style="width: 10px;">
						        </span>
						    </div>
						 </td>


                        <td class="tr_th">품목 코드</td>
                        <td style="padding: 0 30px;">
                            <input type="text" class="statusCode">
                            <button id="check">검색</button>
                        </td>

                    </tr>


                    <tr>
                      <td class="tr_th">구분 상세</td>
                        <td style="padding: 0 30px;">
						    <label style="font-size: 13px"><input type="radio" name="status" value="" checked> 모두 보기</label>
                            <label style="font-size: 13px"><input type="radio" name="status" value="입고"> 입고</label>
						    <label style="font-size: 13px"><input type="radio" name="status" value="출고"> 출고</label>
                        </td>
                       
					  <td class="tr_th">품목 이름</td>
                        <td style="padding: 0 30px;">
                            <input type="text" class="statusName">
                        </td>
                        
                    </tr>
                </table>
            </div>
        </div>


        <div class="cont_area">
            <h2>
                <em><img src="/images/icon1.png" alt="아이콘"></em>
                입고·출고 목록
            </h2>

            <table style="width: 100%; border-color: #a49f9f;  border-collapse: collapse; display:block;">
                <thead>
			        <th style="width: 45px;">순번</th>
			        <th style="width: 132px;" class="sortable statusSortable" data-sort="품목코드">품목코드</th>
			        <th style="width: 236px;" class="sortable statusSortable" data-sort="품목명">품목명</th>
			        <th style="width: 109px;" class="sortable statusSortable" data-sort="일자">일자</th>
			        <th style="width: 80px;">상세</th>
			        <th style="width: 72px;" class="sortable statusSortable" data-sort="입·출고수량">수량</th>
			        <th style="width: 108px;" class="amount-cell">단가</th>
			        <th style="width: 120px;"class="amount-cell">총금액</th>
			    </thead>

                <tbody class="statusTbody statusScroll" id="statusTbody" varStatus="loop">
                    <c:forEach var="status" items="${status}">
                    	<tr>
                    		<td>${status['index']}</td>
							<td>${status['ITEM_ID']}</td>
							<td>${status['ITEM_NAME']}</td>
							<td>${status['STATUSDATE']}</td>
							<td class="statusIncdec ${status['INCDEC']}">${status['INCDEC']}</td>
							<td class="status-stock" data-status="${status['STOCK']}">${status['STOCK']} EA</td>
							<td>₩<fmt:formatNumber value="${status['PRICE']}" /></td>
							<td>₩<fmt:formatNumber value="${status['STOCK'] * status['PRICE']}" /></td>
                    	</tr>                    	
                    </c:forEach>
                 </tbody>
                 
   				<tbody class="total-row" style="background-color: #FFDCDC;">
				      <tr>
			            <td style="width: 604px; font-weight: bold; text-align: center;">총 금액 합계</td>
			            <td style="width: 303px; text-align: center;" id="statusTotalPrice">
			                 <span id="form-status-TotalPrice"></span>
			            </td>
       				 </tr>
				</tbody>
				
            </table>
           </div>           
        </div>
        
        <div id="statusModel" class="modal">
    	<div class="modal-content-status">
    		<span class="close" id="closeStatusModelBtn">&times;</span>
    		
    		<form name="statusForm" id="statusForm" method="multipart/form-data" style="width:100%; height:100%;">
    			<div>입고·출고 등록하기</div>
    			
	    			 <!-- 품목명 입력 -->
	            <div class="status-form-group">
	                <label for="statusCode">품목 코드</label>
	                <select class="form-control" id="statusCode" required>
	                	<option value=""></option>
	                </select>
	            </div>
	            
	            <!-- 입고 수량 -->
   	            <div class="status-form-group">
	                <label for="statusQuantity">입고 수량</label>
	                <input type="text" class="form-control" id="statusQuantity" required>
	            </div>
	
	            <!-- 품목가격 입력 -->
	            <div class="status-form-group">
	                <label for="statusPrices">입고 가격</label>
	                <input type="text" class="form-control" id="statusPrices" required>
	            </div>
	            	
	            <!-- 품목유형 선택 -->
	            <div class="status-form-group" style="padding-left: 1px;">
	                <label for="statusType">품목유형</label>
	                <select class="form-control" id="statusType" name="statusType" required>
	                    <option value="IT001">상품</option>
	                    <option value="IT003">비품</option>
	                    <option value="IT004">소모품</option>
	                </select>
	            </div>
	            
	            <!-- 비고란 -->
	            <div class="status-form-group">
	                <label for="remarks">비고란</label>
	                <input type="text" class="form-control" id="remarks" required>	            
	            </div>
	            
    			<button class="btn btn-outline-dark mt-auto" id="statusInsertBnt" type="button" onclick="statusInsert()">등록</button>
    			<button class="btn btn-outline-dark mt-auto" id="excelUploadBnt" type="button">
    				<img src="/images/xlsx.png" alt="Excel Icon" style="vertical-align: middle; width:20px">
    				업로드
    			</button>
    		</form>
    	</div>
    </div>
        
         <div id="ExcelUploadModel" class="modal">
   			 <div class="excel-modal-content">
		        <span class="close" id="closeExcelUploadModelBtn">&times;</span>
		        <h2>파일 업로드</h2>
		        <form id="excelUploadForm" name="excelUploadForm" method="post" enctype="multipart/form-data">
					<div id="upload_file">
		           		<input type="file" id="excelFile" name="excelFile"/>
		            </div>
		            
		            <div id="uploadBtn">
						<button type="button" onclick="upload()"><span>저장</span></button>
					</div>
		        </form>
   			 </div>
    	</div>
        
        <div id="ExcelDownloadModel" class="modal">
   			 <div class="excel-modal-content">
		        <span class="close" id="closeExcelDownloadlBtn">&times;</span>
		        <h2 style="padding: 25px">Excel 다운로드</h2>
		        <form id="excelDownloadForm" enctype="multipart/form-data">
					<button class="Excel" id="ExcelDownload">
					 	<img src="/images/xlsx.png" alt="Excel Icon" style="vertical-align: middle; width:20px"> 다운로드
					</button>
		            
		            <button class="Excel" id="ExcelFormDownload">
					 	<img src="/images/xlsx.png" alt="Excel Icon" style="vertical-align: middle; width:20px"> 양식받기
					</button>
		        </form>
   			 </div>
    	</div>
        
</body>
</html>