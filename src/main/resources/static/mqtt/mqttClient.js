function getNow() {
	var today = new Date();
	var hours = ('0' + today.getHours()).slice(-2);
	var minutes = ('0' + today.getMinutes()).slice(-2);
	var seconds = ('0' + today.getSeconds()).slice(-2);
	return hours + ':' + minutes + ':' + seconds;
}
    const mqtt_host = "www.chocomungco.store";
	const mqtt_port = 9001; //websocket port : mosquitt.conf 파일에 설정됨  
	const mqtt_topic = "/public/";

	const options = {
		hostname : 'www.chocomungco.store',
		port : 9001,
		username : 'chocomungco',
		password : 'choco11',
		clean : true,
	}//mqtt 연결 설정
	console.log('Connecting mqtt client ');
	console.log('mqtt_topic -> ', mqtt_topic);

	const mqttClient = mqtt.connect(options);//mqtt 연결
	console.log('mqttClient -> ', mqttClient);
	const disconnect = () => {
        console.log('mqtt 연결 끊음');
        mqttClient.end();//mqtt연결 해제
   }  
	mqttClient.on('error', (err) => {
        console.log('Connection error: ', err)
        mqttClient.end()
    });
      
    mqttClient.on('reconnect', () => {
        console.log('Reconnecting...')
    });//mqtt 디버깅
    
  //구독을 등록
    const subscribe = () => {
    	  mqttClient.subscribe(mqtt_topic+"chat", err => {
    		  console.log("Subscribe to a topic=>" +mqtt_topic);
    		  if (!err) {
    			  console.log("error", err);
          } else {
          }
        })
    }
  //구독을 해제한다
    const unsubscribe = () => {
    	  mqttClient.unsubscribe(mqtt_topic);
    }
  //서버에 메시지를 전송한다
    const sendMessage = () => {
    		const message = $("#chatInputBox").val();
            mqttClient.publish(mqtt_topic+"chat", JSON.stringify({
            	type:'CHAT',
            	sender : localStorage.getItem("userId"),
            	receiver :"admin",
            	message:message,
            	seatNo : localStorage.getItem("seatNo"),
            	}));
            $("#chatInputBox").val("");
            	$("#chatList").append(//h2변경 필요
					`<li class="me">
					<div class="entete">
						<h2>client</h2>
					</div>
					<div class="message-wrap">
						<div class="triangle"></div>
						<div class="message">${message}</div>
						<p style="margin-top:3px;font-size: 14px;font-weight: 200;color: #999;padding-left: 5px;">${getNow()}</p>
					</div>
				</li>`
				 );
			$("#chatList").scrollTop($("#chatList")[0].scrollHeight);
    };
    //메세지 수신한 데이터를 삽입
    const recvMessage = recv =>  {
  	  console.log(recv);
     $("#chatList").append(
		`<li class="you">
						<div class="entete">
							<h2>${recv.sender}</h2>
						</div>
						<div class="message-wrap">
							<div class="triangle"></div>
							<div class="message">${recv.message}</div>
							<p style="margin-top:3px;font-size: 14px;font-weight: 200;color: #999;padding-right: 5px;">${getNow()}</p>
						</div>
					</li>`
		);
	$("#chatList").scrollTop($("#chatList")[0].scrollHeight);//채팅이오면 스크롤 내려오게
    }
    //설정 및 메서드 끝
    
    mqttClient.on('connect', () => {
    	console.log('Connected')
        //구독 메시지 등록 
        //메시지 수신 이벤트 핸들러 등록
        subscribe();
    });
    
 // 구독 메시지 수신 
    mqttClient.on('message', function (topic, message) {
        console.log("mqtt message receive :", message.toString())
        	const data = JSON.parse(message.toString())
        	if(data.receiver === localStorage.getItem("userId")){//좌석에따라 수동적으로 바뀌는거 필요함
        		recvMessage(data);
        	}
    })
      
    $("#chatInputBox").on("keydown", e => {
    	  if (e.keyCode == 13) {
			  e.preventDefault();
			  if($("#chatInputBox").val() != "" && $("#chatInputBox").val() != " "){
				  sendMessage();
			  }else{
				  alert("메세지를 입력해 주세요")
			  }
        }
    });
    $("#send_chat_button").on("click", e => {
        if($("#chatInputBox").val() != "" && $("#chatInputBox").val() != " "){
				  sendMessage();
			  }else{
				  alert("메세지를 입력해 주세요")
			  }
    });
    $("#orderBtn").on("click", e => {
            mqttClient.publish("/public/order", JSON.stringify({
            	type:'ORDER',
            	receiver :"admin"}));
    });