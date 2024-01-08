<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>재고관리</title>
</head>

<body>
    <div class="content" id="item" style="display:block;">
        <div class="cont_top" style="height: 100%;">
        <div class="header-content">
            <h2>
                <img src="/images/icon1.png" alt="아이콘">
                재고 관리
            </h2>
			<div class="button-group">
				<input type="button" class="refresh-button" onclick="refreshStockPage()">
	            <button class="check" id="itemCheck" onclick="searchData()">조회</button>
	            <button class="insert" id="stockBtn">등록</button>
	            <button class="Excel" id="Excel">
					 <img src="/images/xlsx.png" alt="Excel Icon" style="vertical-align: middle; width:20px"> 다운로드
				</button>
	        </div>
		</div>
		
            <div class="inquiry">
                <table>
                    <tr>
                       <td class="tr_th">입고 일자</td>
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


                        <td class="tr_th">품목 이름</td>
                        <td style="padding: 0 30px;">
                            <input type="text" class="itemName">
                        </td>

                    </tr>


                    <tr>
                        <td class="tr_th">품목 유형</td>
                        <td style="padding: 0 30px;">
                            <select class="itemSelect">                            	
                                <option value="">모두보기</option>
                                <option value="상품">상품</option>
                                <option value="비품">비품</option>
                                <option value="소모품">소모품</option>
                            </select>
                        </td>


                        <td class="tr_th">재고 상태</td>
                        <td style="padding: 0 30px;">
                            <select class="itemSituation">
                                <option value="">모두보기</option>
                                <option value="1">양호</option>
                                <option value="2">부족</option>
                                <option value="3">품절</option>
                            </select>
                        </td>                       
                    </tr>
                </table>
            </div>
        </div>

        <div class="cont_area">
            <h2>
                <em><img src="/images/icon1.png" alt="아이콘"></em>
                재고 목록
            </h2>        
            <table style="width: 100%;border-color: #a49f9f; border-collapse:collapse; display:block;">
                <thead>
			        <th style="width: 45px;">순번</th>
			        <th style="width: 135px;" class="sortable itemSortable" data-sort="품목코드">품목코드</th>
			        <th style="width: 236px;" class="sortable itemSortable" data-sort="품목명">품목명</th>
			        <th style="width: 71px;">품목유형</th>
			        <th style="width: 109px;" class="sortable itemSortable" data-sort="입고일">입고일</th>
			        <th style="width: 90px;" class="sortable itemSortable" data-sort="현재재고">현재재고</th>
			        <th style="width: 128px;" class="amount-cell">입고단가</th>
			        <th style="width: 90px;">재고상태</th>
			    </thead>
                <tbody class="itemTbody itemScroll" id="itemTbody" varStatus="loop">
                	<c:forEach var="item" items="${stock}">
						    <tr>
						        <td>${item['index']}</td>
						        <td>${item['ITEMID']}</td>
						        <td>${item['ITEMNAME']}</td>
						        <td>${item['TYPE']}</td>
						        <td>${item['STOREDATE'] == null ? '-' : item['STOREDATE']}</td>
						        <td class="stock-data" data-stock="${item['STOCK']}">${item['STOCK'] == null ? '-' : item['STOCK']} EA</td>
						        <td>₩<fmt:formatNumber value="${item['PRICE']}" /></td>
					<td class="itemStyle ${item['STOCKSTATUS']}" id="stockStatus">${item['STOCKSTATUS']}</td>
						   </tr>     
                	</c:forEach>
                </tbody>
				<tbody class="total-row" style="background-color: #FFDCDC;">
				      <tr>
			            <td style="width: 417px; font-weight: bold; text-align: center;">총 입고금액</td>
			            <td style="width: 489px; text-align: center;" id="totalPrice">
			                <span id="formattedTotalPrice"></span>
			            </td>
       				 </tr>
				</tbody>
            </table>
        </div>

    </div>
    
    <div id="stockModel" class="modal">
    	<div class="modal-content">
    		<span class="close" id="closeModelBtn">&times;</span>
    		
    		<form name="stockForm" id="stockForm" method="multipart/form-data" style="width:100%; height:100%;">
    			<div>등록하기</div>
    			
	    			 <!-- 품목명 입력 -->
	            <div class="form-group">
	                <label for="itemName">품목명</label>
	                <input type="text" class="form-control" id="itemNames" required>
	            </div>
	
	            <!-- 품목가격 입력 -->
	            <div class="form-group">
	                <label for="itemPrice">품목가격</label>
	                <input type="text" class="form-control" id="itemPrices" required>
	            </div>
	
	            <!-- 품목유형 선택 -->
	            <div class="form-group" style="padding-left: 1px;">
	                <label for="itemType">품목유형</label>
	                <select class="form-control" id="itemType" name="itemType" required>
	                    <option value="type1">상품</option>
	                    <option value="type2">비품</option>
	                    <option value="type2">소모품</option>
	                </select>
	            </div>
    			<button class="btn btn-outline-dark mt-auto" id="insertBnt" type="button">등록</button>
    		</form>
    	</div>
    </div>
    
</body>
</html>