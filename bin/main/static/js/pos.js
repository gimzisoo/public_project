function formatDatenTime(dateTimeString) {
	const dateTime = new Date(dateTimeString);
	const year = dateTime.getFullYear();
	const month = String(dateTime.getMonth() + 1).padStart(2, '0');
	const day = String(dateTime.getDate()).padStart(2, '0');
	const hours = String(dateTime.getHours()).padStart(2, '0');
	const minutes = String(dateTime.getMinutes()).padStart(2, '0');
	const seconds = String(dateTime.getSeconds()).padStart(2, '0');
	return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

function ajaxResponse(method, url, params) {
	return new Promise(function(resolve, reject) {
		$.ajax({
			url: url,
			method: method,
			data: JSON.stringify(params),
			contentType: "application/json",
			success: function(response) {
				resolve(response);
			},
			error: function(error) {
				reject(error);
			},
		});
	});
}
const countSeat = () => {
	$('.cont_top').find('em').first().text($("[data-seatNo].on").length);
	$('.cont_top').find('em').last().text(50 - $("[data-seatNo].on").length);
}
$('.wrap_cont').on('click', 'li[data-seatNo].on', function(e) {
	const clickedUserId = $(e.currentTarget).find('.uid').text();
	const params = {
		userId: clickedUserId,
	}
	console.log(params);
	ajaxResponse("POST", "/user/getUser", params)
		.then(function(response) {
			const userInfo = response.result;
			console.log(userInfo);
			$('#userSeatNo').text(userInfo.seatNo);
			$('#userId').text(userInfo.userId);
			$('#loginTime').text(formatDatenTime(userInfo.loginTime));
			$('#remainingTime').text(formatTime(userInfo.remainingTime));
			$('#regDate').text(formatDatenTime(userInfo.regDate));
		});
	ajaxResponse("POST", "/order/listById", params)
		.then(function(response) {
			$.each(response.result, function(index, order){
				$('#userOrder').append(
					`<tr>
						<td class="text-left">${order.paymentDate}</td>
						<td class="text-left">${order.itemName}</td>
						<td class="text-left">${order.quantity}</td>
						<td class="text-left">${parseInt(order.quantity) * parseInt(order.sellingPrice)}</td>
					</tr>`
				);
			})
		});
	infoModal.css('display', 'block');
});//좌석 클릭시 사용자정보 띄우기

$(document).ready(function() {
	ajaxResponse("GET", "/admin/loggedInUserList")
		.then(function(response) {
			loggedInUserList = response.result;
			if (loggedInUserList != null) {
				$.each(loggedInUserList, function(index, user) {
					var remainingTime = user.remainingTime;
					var seat = $(`li[data-seatNo=${user.seatNo}]`);
					if (remainingTime > 0) {
						var now = new Date().getTime();
						var loginTime = new Date(user.loginTime).getTime();
						var durationTime = now - loginTime;
						remainingTime = remainingTime - Math.floor(durationTime / 1000);
						if (!seat.hasClass('on')) {
							seat.addClass("on");
							seat.find("p").first().text(user.userId);
							updateCountdown(seat.find("p").last(), remainingTime, user.seatNo, user.loginTime);
						}
					}
				});
			}
			addOption();
			countSeat();
			//closeInfoModal();
		});//로그인 유저 가져와서 좌석 띄우기, 모달창


	ajaxResponse("GET", "/chat/list")
		.then(function(response) {
			console.log(response.result)
			$.each(response.result, function(index, chat) {
				if (chat.receiver === 'admin') {
					$("#chatList").append(
						`<li class="you">
						<div class="entete">
							
							<h2>${chat.seatNo}번 좌석(${chat.sender})</h2>
							</div>
							<div class="message-wrap">
								<div class="triangle"></div>
								<div class="message">${chat.message}</div>
								<p style="margin-top:3px;font-size: 14px;font-weight: 200;color: #999;padding-left: 5px;">${getNow()}</p>
							</div>
					</li>`
					);
				} else {
					$("#chatList").append(
						`<li class="me">
									<div class="entete">
										<h2>${chat.receiver}님에게 보냄</h2>
									</div>
									<div class="message-wrap">
										<div class="triangle"></div>
										<div class="message">${chat.message}</div>
										<p style="margin-top:3px;font-size: 14px;font-weight: 200;color: #999;padding-right: 5px;">${getNow()}</p>
									</div>
								</li>`
					);
				}
			});
		});//채팅 데이터 불러오기		
	ajaxResponse("GET", "/admin/getOrderList")
		.then(function(response) {
			console.log(response.result)
			if (Object.keys(response.result).length > 0) {
					if (!$('#alertIcon').hasClass('on')) {
						$('#alertIcon').addClass('on');
					}
				}
			$.each(response.result, function(key, order) {
				var priceList = [];
				var sum = 0;
				var seatNo = order[0].seatNo;
				$("#orderList").prepend(
					`<button class="accordion" data-orderId='${key}'>${seatNo}번 좌석 주문</button>
		 				<div class="panel"><p class="alert_date">주문 일시  ${getNow()}</p></div>`);
				$.each(order, function(index, detailOrder) {
					$("#orderList").children().next().first()
						.append(
							`<div class="alert_item">
								<div class="alert_lft">
									<img src="/image/download/${detailOrder.imgId}"/>
								</div>
								<div class="alert_rgt">
									<p class="item_txt">${detailOrder.itemName}</p>
									<div class="price_info">
										<p class="item_quantity">${detailOrder.quantity}개</p>
										<p class="item_price">${detailOrder.sellingPrice * detailOrder.quantity}원</p>
									</div>
								</div>
							</div>`
						);
					priceList.push(detailOrder.sellingPrice * detailOrder.quantity);
				});
				$.each(priceList, function(index, price) {
					sum = sum + price
				})
				$("#orderList").children().next().first()
					.append(`
						<div class="alert_bot">
						<p>Total <em>${sum}</em>원</p>
						<button class="served">주문 확인</button>
						</div>
						`);
			});
		});
	//주문데이터 불러오기
});

$('#orderList').on('click', '.served', function(e) {
	const orderId = $(e.currentTarget).parent().parent().prev().data('orderid');
	const params = {
		orderId : orderId,
	}
	console.log(params)
	ajaxResponse("POST", "/order/served", params)
		.then(function(response) {
			if(response.result){
			Swal.fire({
			  title: "서빙 완료!",
			  text: "정상적으로 처리되었습니다.",
			  icon: "success",
			  confirmButtonText: "확인",
			});
				$(e.currentTarget).parent().parent().prev().remove();
				$(e.currentTarget).parent().parent().remove();
			}
		});
});//DB에 Orders 테이블 served를 Y로 바꾸는 로직


$('#chatIcon').on('click', function(){
	$('#chatIcon').removeClass('on');
});
$('#alertIcon').on('click', function(){
	$('#alertIcon').removeClass('on');
});
