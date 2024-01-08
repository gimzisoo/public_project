function formatTime(seconds) {
	var hours = Math.floor(seconds / 3600);
	var minutes = Math.floor((seconds % 3600) / 60);
	var remainingSeconds = seconds % 60;
	if (hours === 0) {
		return minutes + " : " + remainingSeconds ;
	} else {
		return hours + " : " + minutes + " : " + remainingSeconds ;
	}
}
function getNow() {
	var today = new Date();
	var hours = ("0" + today.getHours()).slice(-2);
	var minutes = ("0" + today.getMinutes()).slice(-2);
	var seconds = ("0" + today.getSeconds()).slice(-2);
	return hours + ":" + minutes + ":" + seconds;
}
let timers = {}; // 타이머 ID를 저장할 객체

function updateCountdown(time, remainingTime, seatNo,loginTime) {
	if (remainingTime > 0) {
		time.text(formatTime(remainingTime));
		remainingTime--;
		const timerID = setInterval(function() {
			time.text(formatTime(remainingTime));
			remainingTime--;
		}, 1000);

		// 타이머 ID를 저장
		timers[seatNo] = timerID;
		console.log(timers);
	}else {
				clearInterval(timers[seatNo]); // 타이머 중지
				time.closest("li").removeClass("on");
				time.closest("li").find("p").first().text("");
				time.closest("li").find("p").last().text("");
				$(`option[value=${seatNo}]`).remove();
				delete timers[seatNo]; // 타이머 ID를 객체에서 제거
	}
}

function clearSeat(){
	$.each($("[data-seatNo].on"), function(index, seat) {
		var seat = $(seat);
		var seatNo = seat.find("em").text();
		console.log("seatNo+>" + seatNo);
		if (seat.hasClass("on")) {
			seat.removeClass("on");
			seat.find("p").first().text("");
			clearInterval(timers[seatNo]);
			delete timers[seatNo];
			seat.find("p").last().text("");
			$(`option[value=${seatNo}]`).remove();
		}
	});
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
function sortOptions() {
	//채팅창 좌석선택 옵션 정렬
	const optionsArray = $("#seatSelector option").toArray();
	// <option> 요소를 seatNo 속성으로 정렬
	optionsArray.sort(function(a, b) {
		const seatNoA = parseInt($(a).val(), 10);
		const seatNoB = parseInt($(b).val(), 10);
		return seatNoA - seatNoB;
	});
	// <select> 요소를 비우고 정렬된 <option> 요소를 다시 추가
	$("#seatSelector").empty();
	optionsArray.forEach(function(option) {
		$("#seatSelector").append(option);
	});
}

const addOption = () => {
	const existingOptions = $("#seatSelector option")
		.map(function() {
			return $(this).val();
		})
		.get(); //현재 로그인한 좌석 번호
	console.log(existingOptions);
	$.each($("[data-seatNo].on em"), function(index, seat) {
		const seatNumber = $(seat).text();
		console.log(seatNumber);
		if (!existingOptions.includes(seatNumber)) {
			$("#seatSelector").append(
				"<option value=" + seatNumber + ">" + seatNumber + "</option>"
			);
		}
	});
	sortOptions();
};

const mqtt_host = "www.chocomungco.store";
const mqtt_port = 9001; //websocket port : mosquitt.conf 파일에 설정됨
const mqtt_topic = "/public/";

const options = {
	hostname: "www.chocomungco.store",
	port: 9001,
	username: "chocomungco",
	password: "choco11",
	clean: true,
}; //mqtt 연결 설정
//console.log('Connecting mqtt client ');
//console.log('mqtt_topic -> ', mqtt_topic);

const mqttClient = mqtt.connect(options); //mqtt 연결
//console.log('mqttClient -> ', mqttClient);
const disconnect = () => {
	//console.log('mqtt 연결 끊음');
	mqttClient.end(); //mqtt연결 해제
};
mqttClient.on("error", (err) => {
	//console.log('Connection error: ', err)
	mqttClient.end();
});

mqttClient.on("reconnect", () => {
	//console.log('Reconnecting...')
}); //mqtt 디버깅

//구독을 등록
const subscribe = () => {
	mqttClient.subscribe(mqtt_topic + "#", (err) => {
		//console.log("Subscribe to a topic=>" +mqtt_topic);
		if (!err) {
			//console.log("error", err);
		} else {
		}
	});
};
//구독을 해제한다
const unsubscribe = () => {
	mqttClient.unsubscribe(mqtt_topic);
};
//서버에 메시지를 전송한다
const sendMessage = () => {
	if (!isNaN($("#seatSelector option:selected").val())) {
		const seatNo = $("#seatSelector option:selected").val();
		const message = $("#chatInputBox").val();
		var receiver = "";
		$.each($("[data-seatNo].on em"), function(index, seat) {
			if ($(seat).text() == seatNo) {
				receiver = $(seat).siblings(".uid").text();
			}
		});
		const param = {
			type: "CHAT",
			sender: "admin",
			receiver: receiver,
			message: message,
		};
		mqttClient.publish(mqtt_topic + "chat", JSON.stringify(param));
		$("#chatInputBox").val("");
		$("#chatList").append(
			`<li class="me">
                      <div class="entete">
                          <h2>좌석 ${seatNo}님에게 보냄</h2>
                      </div>
                      <div class="message-wrap">
	                      <div class="triangle"></div>
	                      <div class="message">${param.message}</div>
	                       <p style="margin-top:3px;font-size: 14px;font-weight: 200;color: #999;padding-right: 5px;">${getNow()}</p>
	                  </div>
                  </li>`
		);
		$("#chatList").scrollTop($("#chatList")[0].scrollHeight);
	} else {
		swal("","좌석을 선택해 주세요!", "warning");
	}
};
//메세지 수신한 데이터를 삽입
const recvMessage = (recv) => {
	if (Object.keys(recv).length > 0) {
		if (!$('#chatIcon').hasClass('on')) {
			$('#chatIcon').addClass('on');
		}
	}
	if (!$(".wrap_chat.on").length > 0) {
		$(document).Toasts("create", {
			title: recv.seatNo + "번 좌석 채팅이 도착했습니다.",
			autohide: true,
			delay: 5000,
			body: recv.message,
		});
	}
	console.log(recv);
	$("#chatList").append(
		`<li class="you">
          <div class="entete">
              <h2>${recv.seatNo}번 좌석(${recv.sender})</h2>
              </div>
              <div class="message-wrap">
	              <div class="triangle"></div>
	              <div class="message">${recv.message}</div>
	              <p>${getNow()}</p>
	          </div>
      </li>`
	);
	$("#chatList").scrollTop($("#chatList")[0].scrollHeight); //채팅이오면 스크롤 내려오게
};
const recvOrder = () => {
	var orderIds = [];
	$.each($('[data-orderId]'), function(index, orderId) {
		orderIds.push($(orderId).attr('data-orderId'));
	});

	ajaxResponse("GET", "/admin/getOrderList")
		.then(function(response) {
			var priceList = [];
			var sum = 0;
			if (Object.keys(response.result).length > 0) {
				if (!$('#alertIcon').hasClass('on')) {
					$('#alertIcon').addClass('on');
				}
			}
			$.each(response.result, function(key, order) {
				if (!orderIds.includes(key)) {
					orderIds.push(key);
					console.log(orderIds)
					var seatNo = order[0].seatNo;
					if (!$('.wrap_alert.on').length > 0) {
						$(document).Toasts('create', {
							title: seatNo + '번 좌석 주문이 도착했습니다.',
							autohide: true,
							delay: 5000,
							body: order.length + "건의 주문"
						});
					}
					//$(`li.on .uid:contains('${order[0].userId}')`).parent().find('em').text();
					console.log(seatNo)
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
				}

			});
		});
}; //주문리스트 받기

const recvLogin = () => {
	ajaxResponse("GET", "/admin/loggedInUserList")
		.then(function(response) {
			console.log(response.result);
			$.each(response.result, function(index, user) {
				var seat = $(`li[data-seatNo=${user.seatNo}]`);
				if (!seat.hasClass("on")) {
					seat.addClass("on");
					seat.find("p").first().text(user.userId);
					updateCountdown(
						seat.find("p").last(),
						user.remainingTime,
						user.seatNo,
						user.loginTime
					);
				}
			});
			countSeat();
			addOption();
		})
		.catch(function(error) {
			console.error("로그인 정보 가져오는중 에러 발생: " + error);
		});
}; //사용자 로그인시 관리자 좌석 동적으로 변경

const recvLogout = () => {
	clearSeat();
	ajaxResponse("GET", "/admin/loggedInUserList")
		.then(function(response) {
			$.each(response.result, function(index, user) {
				var seat = $(`li[data-seatNo=${user.seatNo}]`);
				var remainingTime = user.remainingTime;
				if(remainingTime > 0){
				  var now = new Date().getTime();
	              var loginTime = new Date(user.loginTime).getTime();
	              var durationTime = now - loginTime;
	              remainingTime = remainingTime - Math.floor(durationTime / 1000);
				}
				if (!seat.hasClass("on")) {
					seat.addClass("on");
					seat.find("p").first().text(user.userId);
					updateCountdown(
						seat.find("p").last(),
						remainingTime,
						user.seatNo
					);
				}
			});
			countSeat();
			addOption();
		})
		.catch(function(error) {
			console.error("로그아웃 정보 가져오는중 에러 발생: " + error);
		});
}; //사용자 로그아웃시 관리자 좌석 동적으로 변경

//설정 및 메서드 끝

mqttClient.on("connect", () => {
	//console.log('Connected')
	//구독 메시지 등록
	//메시지 수신 이벤트 핸들러 등록
	subscribe();
});

// 구독 메시지 수신
mqttClient.on("message", function(topic, message) {
	////console.log("mqtt message receive :", message.toString())
	const data = JSON.parse(message.toString());
	console.log(data);
	if (data.receiver === "admin" && data.type === "CHAT") {
		recvMessage(data);
	} else if (data.receiver === "admin" && data.type === "LOGIN") {
		recvLogin(data);
		incUsersNumber(data);
	} else if (data.receiver === "admin" && data.type === "LOGOUT") {
		recvLogout();
	} else if (data.receiver === "admin" && data.type === "ORDER") {
		recvOrder(data);
	} else if (data.receiver === "admin" && data.type === "CHARGE") {
		recvLogout();
	} //충전시 잔여시간 변경 기능 추가
});

$("#chatInputBox").on("keydown", (e) => {
	if (e.keyCode == 13) {
		e.preventDefault();
		if ($("#chatInputBox").val() != "" && $("#chatInputBox").val() != " ") {
			sendMessage();
		} else {
			alert("메세지를 입력해 주세요");
		}
	}
});
$("#send_chat_button").on("click", (e) => {
	if ($("#chatInputBox").val() != "" && $("#chatInputBox").val() != " ") {
		sendMessage();
	} else {
		swal("","메세지를 입력해 주세요!", "warning");
	}
});

// 로그인 시 금일 이용자수 1씩 증가
function incUsersNumber() {
	const todayUsers = $(".todayUsers");
	todayUsers.text(Number(todayUsers.text()) + 1);
	console.log("증가 후 : " + todayUsers.text());
}
