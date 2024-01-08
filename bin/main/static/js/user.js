
// 로그아웃
$('#logoutBtn').on('click', function(){
   mqttClient.publish(mqtt_topic, JSON.stringify(
       {type: "LOGOUT",
        receiver: "admin"}
   ));
   location.href = "/logout";
});

// 채팅 모달
$("#chattingBtn").on('click', function(){
   $(".cont-bot-wrap").css('display','block');
   $(".chat-wrap").css('display','block');
   $("#chatList").scrollTop($("#chatList")[0].scrollHeight);
});

//주문버튼
function navBtn(element) {
    const thisNavLi = $(element).parent();
	
    $(".cont-modal-wrap, .cont-bot-wrap, .wrap_cart, .chat-wrap").css('display', 'none');
    $(".wrap_cart").removeClass('on');
    $('#orderBtn22, #rechargeBtn, #chattingBtn').removeClass('on');

    if (thisNavLi.hasClass('on')) {
        $('.cont-modal-wrap').hide();
        $(".wrap_cart").css('display', 'none');
        $(".chat-wrap").css('display', 'none');
        $(".wrap_recharge").css('display', 'none');
        thisNavLi.removeClass('on');
    } else {
        if (thisNavLi.attr('id') === 'orderBtn22') {
			getMenuList(); 
			removeTimeAll();
            $(".cont-modal-wrap, .cont-bot-wrap, .wrap_cart, .modal-food").css('display', 'block');
            $(".chat-wrap").css('display', 'none');
            $(".wrap_recharge").css('display', 'none');
            $('#orderBtn22').addClass('on');
            $('#rechargeBtn').removeClass('on');
            $("#orderBtn22").removeClass('on');
        } else if (thisNavLi.attr('id') === 'rechargeBtn') {
         $(".cont-modal-wrap, .cont-bot-wrap, .wrap_recharge").css('display', 'block');
         $(".wrap_cart").css('display', 'none');
         $(".chat-wrap").css('display', 'none');
         	$('.cont-modal-wrap').hide();
            $('#rechargeBtn').addClass('on');
            $("#chattingBtn").removeClass('on');
            $("#orderBtn22").removeClass('on');
            $('.addCart ul li').remove();
            $('.addCart').addClass('empty');
            $('.menu-total-price').css('display', 'none');
            $('.scroll').css('display', 'none');
            $('.menu-total-price em').text('0');
        } else if (thisNavLi.attr('id') === 'chattingBtn') {
			$('.cont-modal-wrap').hide();
            $(".cont-bot-wrap, .chat-wrap").css('display', 'block');
            $(".wrap_cart").css('display', 'none');
            $(".wrap_recharge").css('display', 'none');
            $(".wrap_cart").removeClass('on');
            $('#chattingBtn').toggleClass('on');
            $("#orderBtn22").removeClass('on');
            $('.addCart ul li').remove();
            $('.addCart').addClass('empty');
            $('.menu-total-price em').text('0');
             $('.menu-total-price').css('display', 'none');
             $('.scroll').css('display', 'none');
            removeTimeAll();
        }

        thisNavLi.addClass('on');
    }
}


// 시간 js
function ajaxResponse(method, url, params) {
    return new Promise(function(resolve, reject) {
        $.ajax({
            url: url,
            method: method,
            data: JSON.stringify(params),
            contentType: 'application/json',
            success: function(response) {
                resolve(response);
            },
            error: function(error) {
                reject(error);
            }
        });
    });
}


var remainingTimeElement = document.getElementById("remainingTime");

function formatTime(seconds) {
    var hours = Math.floor(seconds / 3600);
    var minutes = Math.floor((seconds % 3600) / 60);
    var remainingSeconds = seconds % 60;

    return hours + " : " + minutes + " : " + remainingSeconds ;
}


function updateCountdown(remainingTime) {
    remainingTimeElement.textContent = formatTime(remainingTime);
    if (remainingTime > 0) {
        remainingTime--;
        setTimeout(function () {
            updateCountdown(remainingTime);
        }, 1000);
    } else if (remainingTime <= 300 && remainingTime > 0) {
        remainingTimeElement.style.color = "red";
   }else {
        location.href = "/user/logout/"+localStorage.getItem("userId");
    }
}

function updateRemainingTime(userIdValue) {
   const data = {userId : userIdValue}; //JWT 토큰 구현 이후 userID가져와야함
   ajaxResponse('POST', '/user/getUser', data)
      .then(function(response) {     
         var userInfo = response.result;
        console.log("userId " + userInfo.userId);    
        console.log("seatNo " + userInfo.seatNo);    
         localStorage.setItem("userId", userInfo.userId);//테스트용 userId저장
         localStorage.setItem("seatNo", userInfo.seatNo);//테스트용 userId저장
         var remainingTime = userInfo.remainingTime;
         if (remainingTime >= 0) {
              var now = new Date().getTime();
              var loginTime = new Date(userInfo.loginTime).getTime();
              var durationTime = now - loginTime;
              remainingTime = remainingTime - Math.floor(durationTime / 1000);
              updateCountdown(remainingTime);
          } else {
 			Swal.fire({
			  title: "Error!",
			  text: "잔여시간이 없습니다.",
			  icon: "error",
			  confirmButtonText: "확인",
			});
                 location.href = "/user";
          } 
      })
      .catch(function(error) {
         console.error("로그인 정보 가져오는중 에러 발생: " + error);
      });
}

window.onload = function() {
   //const userInfoData = $('#userInfo').val();

   /*// 쿠키에서 모든 쿠키를 가져옵니다.
   var allCookies = document.cookie;

   // 각 쿠키를 분리합니다.
   var cookiesArray = allCookies.split(';');

   // userId 쿠키를 찾습니다.
   var userIdCookie = cookiesArray.find(function(cookie) {
      return cookie.trim().startsWith('userId=');
   });

   // userId 쿠키에서 값만 추출합니다.
   var userIdValue = userIdCookie ? userIdCookie.split('=')[1] : null;*/
   var userIdValue = $('#userId').text();

   // userId 값을 출력합니다.
   console.log('userId 값:', userIdValue);
   updateRemainingTime(userIdValue);

   var userIdElement = document.getElementById("userId");
   //var loggedInUserId = localStorage.getItem("userId"); 

   var loggedInUserId = userIdValue;
   userIdElement.textContent = loggedInUserId;
   
    //updateRemainingTime(userIdValue);
    
    var userIdElement = document.getElementById("userId");
    //var loggedInUserId = localStorage.getItem("userId"); 
    
    var loggedInUserId = userIdValue; 
    userIdElement.textContent = loggedInUserId;
    
    
    mqttClient.publish(mqtt_topic, JSON.stringify(
       {type: "LOGIN",
        receiver: "admin"}
   ));
   
   //채팅 가져오기

   const data = {
      receiver: userIdValue,
   }; //JWT 토큰 구현 이후 userID가져와야함
   console.log(data);
   ajaxResponse('POST', '/chat/getListById', data)
      .then(function(response) {
      var chatList = response.result;
      if (chatList != null) {
         console.log(chatList);
         $.each(response.result, function(index, chat) {
            if (chat.sender === 'admin') {
               $("#chatList").append(
                  `<li class="you">
               <div class="entete">
                  <h2>카운터</h2>
                  </div>
                  <div class="message-wrap">
                    <div class="triangle"></div>
                    <div class="message">${chat.message}</div>
                    <p style="margin-top:3px;font-size: 14px;font-weight: 200;color: #999;padding-left: 5px;">${chat.time}</p>
                 </div>
            </li>`
               );
            } else {
               $("#chatList").append(
                  `<li class="me">
                        <div class="entete">
                           <h2>${chat.sender}</h2>
                        </div>
                        <div class="message-wrap">
	                        <div class="triangle"></div>
	                        <div class="message">${chat.message}</div>
	                        <p style="margin-top:3px;font-size: 14px;font-weight: 200;color: #999;padding-right: 5px;">${chat.time}</p>
	                     </div>
                     </li>`
               );
            }
         });
      }
   });
}      

var totalPrice = 0;

// 장바구니 담기
function addCart(element) {
    const thisNavLi = $(element).parent();
    const itemId = $(element).data('menu-id');
    const itemName = $(thisNavLi).find(".food-name").text();
    const sellingPrice = parseFloat($(thisNavLi).find(".food-price").text().replace(",", ""));
    // 이미 장바구니에 존재하는지 확인
    const existingCartItem = $('.addCart ul li').filter(function () {
        return $(this).find('.itemId').text() === itemId;
    });
    $(".addCart").removeClass("empty")
	$('.menu-total-price').css('display','block');
    if (existingCartItem.length > 0) {
        // 이미 존재하면 수량만 증가
        const optionNumElement = existingCartItem.find('.optionNum');
        const optionNum = parseInt(optionNumElement.data('option-num'));
        optionNumElement.data('option-num', optionNum + 1);
        optionNumElement.text(optionNum + 1);
        updateCartItemPrice(existingCartItem);
        
    } else {
        // 존재하지 않으면 새로운 아이템 추가
        var cartItem = '<li>' +
            '<p class="itemId">' + itemId + '</p>' +
            '<p class="food-name">' + itemName + '</p>' +
            '<p class="food-price on">' + parseInt(sellingPrice).toLocaleString() + '</p>' +
            '<p class="food-price off" style="display:none;">' + sellingPrice + '</p>' +
            '<div class="food-option">' +
            '<span class="btn_option minus" onClick="updateOptionNum(this)">-</span>' +
            '<p><em class="optionNum" data-option-num="1">1</em></p>' +
            '<span class="btn_option plus" onClick="updateOptionNum(this)">+</span>' +
            '</div>' +
            '<a href="javascript:void(0);" class="remove-from-cart" onClick="removeCart(this)">X</a>' +
            '</li>';

        $('.addCart ul').append(cartItem);
        updateCartItemPrice(existingCartItem);
    }
/*
    updateCartItemPrice(existingCartItem); // 가격 업데이트 호출*/
   	menuScroll();
    updateTotalPrice();
    updateOrderButtonState();
}

function updateTotalPrice() {
    let totalPrice = 0;
	
    if ($('.orderMenu').is(':visible')) {
        $('.addCart ul li').each(function () {
            const itemPrice = $(this).find('.food-price.off').text();
            const quantity = $(this).find('.food-option .optionNum').data('option-num');
            totalPrice += itemPrice * quantity;
        });
    } else {
        totalPrice = totalChargePrice;
    }

    $("#total-price").text(parseInt(totalPrice).toLocaleString());
    $('.menu-total-price em').text(parseInt(totalPrice).toLocaleString()); // Total 가격 업데이트
    updateOrderButtonState();
}



// 주문하기 버튼 활성화
function updateOrderButtonState() {
    const orderButton = $('#orderBtn22');
    const rechargeButton = $('#rechargeBtn');
    
    if ($('.addCart ul li').length > 0) {
        orderButton.prop('disabled', false);
        rechargeButton.prop('disabled', false);
    } else {
        orderButton.prop('disabled', true);
        rechargeButton.prop('disabled', false);
    }
}

function menuScroll(){
	if ($('.addCart ul li').length < 3) {
		$('.scroll').css('display','none');
	} else {
		$('.scroll').css('display','block');
	}
}

// 메뉴옵션 수정
function updateOptionNum(element) {
    const optionNumElement = $(element).closest('.food-option').find('.optionNum');
    let optionNum = parseInt(optionNumElement.data('option-num'));

    if ($(element).hasClass('minus')) {
        if (optionNum > 1) {
            optionNum--;
        }
    } else if ($(element).hasClass('plus')) {
        optionNum++;
    }

    optionNumElement.data('option-num', optionNum);
    optionNumElement.text(optionNum);

    // 옵션 업데이트 시 가격도 함께 업데이트
    updateCartItemPrice($(element).closest('li'));
}

// 장바구니 아이템 가격 업데이트
function updateCartItemPrice(cartItem) {
    const optionNum = parseInt(cartItem.find('.optionNum').data('option-num'));
    const sellingPrice = parseFloat(cartItem.find('.food-price.off').text().replace(",", ""));
    const newPrice = optionNum * sellingPrice;
    const formattedPrice = parseInt(newPrice).toLocaleString();

    cartItem.find('.food-price.on').text(formattedPrice);
    $('.menu-total-price em').text(formattedPrice); 
}

//해당 메뉴 삭제
function removeCart(element) {
    $(element).closest('li').remove();
    if($('.addCart ul li').length == 0){
		$('.addCart').addClass('empty'); 
		$('.menu-total-price').css('display','none');
	}
	updateTotalPrice();
	menuScroll();
}    
    
// 장바구니 비우기
function removeCartAll() {
    $('.addCart ul li').remove();
    $('.addCart').addClass('empty'); 
    $('.menu-total-price').css('display','none');
    menuScroll();
    totalPrice = 0;
    updateTotalPrice();
}    

// 키테고리별 메뉴 출력       
function showCategory(no) {
  $(".menu-category li").removeClass('on');
  $("#menu0" + no).addClass('on');

  if (no == 1) {
    $(".food-list li").not('#MC001').hide();
     $(".food-list li[id='MC001']").show();
  } else if(no == 2){ 
   $(".food-list li").not('#MC002').hide();
    $(".food-list li[id='MC002']").show();
  } else if(no == 3){ 
   $(".food-list li").not('#MC003').hide();
    $(".food-list li[id='MC003']").show();
  } else {
    $(".food-list li").not('#MC004').hide();
    $(".food-list li[id='MC004']").show();
  }
}

function orderBtn(){
   $('.order-btn-list .recharge-order').hide();
   if ($('.addCart ul li').length > 0) {
        updateTotalPrice(); 
        $(".modal-payment").show();
        $(".modal-paymentList").show();
    } else {
		Swal.fire({
		  title: "Error",
		  text: "장바구니가 비어있습니다.",
		  icon: "error",
		  confirmButtonText: "확인",
		});
    }
}

function closePayment() {
	 $(".modal-payment").hide();
     $(".modal-paymentList").hide();
}
function rechargeBtn(){
   $('.order-btn-list .menu-order').hide();
   $('.modal-food').hide();
    $(".modal-payment").show();
    $(".modal-paymentList").show();
    updateTotalPrice();
    updateOrderButtonState();
}

var paymentMethodCode;

function payment(no){
   paymentMethodCode = "PM00" + no;
   $(".modal-paymentList").hide();
   $(".modal-order").show();
}

// 결제하기
function order() {
   const userId = $("#userId").text();
   const cartItems = [];
   const paymentMethodCodeValue = paymentMethodCode;

    $('.addCart ul li').each(function () {
        const itemId = $(this).find('.itemId').text(); 
        const quantity = $(this).find('.food-option .optionNum').data('option-num');
        const itemPrice = $(this).find('.food-price.off').text();
        const itemTotalPrice = itemPrice * quantity;

        cartItems.push({ itemId: itemId, quantity: quantity, price: itemTotalPrice });
       
    });
    
  
    const param = { userId: userId, items: cartItems , paymentMethodCode : paymentMethodCodeValue};
     
    console.log(param);
  
    $.ajax({
        url: '/user/order',
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(param),
        success: function(response) {
            if (response.rs == 'true') {
        Swal.fire({
            title: "Success!",
            text: "주문이 정상적으로 처리되었습니다.",
            icon: "success",
            confirmButtonText: "확인",
        });
                $('.addCart ul').empty();
                $(".modal-payment").hide();
            $(".modal-order").hide();
            $(".modal-paymentList").hide();
            $(".modal-order #total-price").empty();
                mqttClient.publish(mqtt_topic + "order", JSON.stringify({
                    type: "ORDER",
                    receiver: "admin"
                }));
            } else {
        Swal.fire({
            title: "Error!",
            text: "주문이 정삭적으로 처리되지 않았습니다.",
            icon: "error",
            confirmButtonText: "확인",
        });
            }
        },
        error: function(error) {
            console.error('주문 에러:', error);
        }
    });
    
    updateTotalPrice();
    $(".modal-payment").hide();
    /*$('.order-btn-list .menu-order').show();
    $('.order-btn-list .recharge-order').show();*/
    removeCartAll();
}

function cancle(){
   $(".modal-payment").hide();
   $(".modal-order").hide(); 
   $('.order-btn-list .menu-order').show();
   $('.order-btn-list .recharge-order').show();
   $(".modal-order #total-price").empty();
}

// 메뉴리스트 호출
function getMenuList() {
    $.ajax({
        url: "/user/getMenuList",
        type: "GET",
        success: function (menuList) {
            console.log("서버에서 받은 메뉴 리스트:", menuList);
            displayMenuList(menuList.menuList);
        },
        error: function (error) {
            console.error("오류: " + error);
        }
    });
}

// 호출된 메뉴 리스트 출력
function displayMenuList(menuList) {

    var foodList = document.querySelector(".food-list");
    
   $('.food-list').empty();


    if (menuList != null) {
        menuList.forEach(function(menu) {
            var row = '<li id="' + menu.MENUCATEGORYCODE + '">'+
               '<a href="javascript:void(0);" class="add-to-cart" onClick="addCart(this);" data-menu-id="' + menu.ITEMID +'">' +
                '</a>' +
                '<div class="img-wrap">' +
                '<img alt="상품이미지" src="/image/download/' + menu.IMGID + '"/>' +
                '</div>' +
                '<div class="food-info-wrap">' +
                '<p class="food-name">' + menu.ITEMNAME + '</p>' +
                '<p class="food-price-wrap">' +'<span>'+ parseInt(menu.SELLINGPRICE).toLocaleString() +'</span>' +  '<span class="food-price" style="display:none;">' + menu.SELLINGPRICE + '</span>' + '원 </p>' +
                '</div>' +
                '</li>';
            $('.food-list').append(row);
        });
    } else {
        console.error("메뉴가 없습니다.", menuList);
    }
     showCategory(1);
}

/*// 시간 충전
function recharge() {
    const userId = $('#userId').text(); 
    const chargeTime = parseInt($('#chargeTime').text());
    
    const paymentMethodCodeValue = paymentMethodCode;

    const param = {
        userId: userId,
        remainingTime: chargeTime * 3600,
        paymentMethodCode: paymentMethodCodeValue
    };

    $.ajax({
        url: '/user/recharge', 
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(param),
        success: function(response) {
            if (response.rs == 'true') {
                alert('충전이 정상적으로 이루어졌습니다.');
            } else {
                alert('충전이 정상적으로 이루어지지 않았습니다.');
            }
        },
        error: function(error) {
            console.error('충전 에러:', error);
        }
    });
}*/

// 시간 충전
function recharge() {
   const userId = $("#userId").text();
   const chargeItems = [];
   const chargeTime = parseInt($('#chargeTime').text());
   const paymentMethodCodeValue = paymentMethodCode;

    const quantity = chargeTime;
    const itemTotalPrice = chargeTime * 1000;

    chargeItems.push({ itemId: 'ITEM000001', quantity: quantity, price: itemTotalPrice });

   console.log(chargeItems);
    const param = { userId: userId, remainingTime: chargeTime * 3600, items: chargeItems , paymentMethodCode : paymentMethodCodeValue};
     
    
    console.log(param);
  
    $.ajax({
        url: '/user/recharge',
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(param),
        success: function(response) {
            if (response.rs == 'true') {
                $(".modal-payment").hide();
	            $(".modal-order").hide();
	            $(".modal-paymentList").hide();
	            $(".modal-order #total-price").empty();
	                mqttClient.publish(mqtt_topic + "charge", JSON.stringify({
                    type: "CHARGE",
                    receiver: "admin"
                }));

		    Swal.fire({
				  title: "충전이 완료 되었습니다!",
				  text: "",
				  icon: "success",
				  confirmButtonText: "확인",
				}).then((value) => {
				  if (value) {
				    window.location.reload();
				  }
				});
                 
            } else {
		    Swal.fire({
				  title: "충전 중 오류가 발생했습니다. ",
				  text: "관리자에게 문의해 주세요.",
				  icon: "error",
				  confirmButtonText: "확인",
				});
            }
        },
        error: function(error) {
            console.error('주문 에러:', error);
        }
    });
    totalChargePrice = itemTotalPrice;
    updateTotalPrice();
    updateTotalChargePrice();
    $(".modal-payment").hide();/*
    $('.order-btn-list .menu-order').show();
    $('.order-btn-list .recharge-order').show();*/
    removeChargeAll();
    
}

// 충전시간 조절
let chargeTime = 1;
let totalChargePrice = 1000;
const chargePrice = 1000;

updateTotalChargePrice();

$('.minus').on('click', function(e) {
    if (chargeTime > 1) {
        chargeTime--; 
        updateChargeTime();
        updateTotalChargePrice();
    }
});

$('.plus').on('click', function(e) {
    chargeTime++; 
    updateChargeTime();
    updateTotalChargePrice();
});

function updateChargeTime() {
    $('.charge_time em').text(chargeTime);
}



function updateTotalChargePrice() {
    totalChargePrice = chargeTime * chargePrice;
    const totalChargePrices = parseInt(totalChargePrice).toLocaleString();
    $('.charge-total-price em').text(totalChargePrices);
    
}


function updateChargeTime() {
    $('.charge_time em').text(chargeTime);
}


function removeTimeAll(){
	 $('.charge_time em').text('1');
	 $('.charge-total-price em').text('1,000');
}
/*function fetchMenuList() {
    $.ajax({
        url: "/user/getMenuList",
        type: "GET",
        success: function (menuList) {
            displayMenuList(menuList);
        },
        error: function (error) {
            console.error("Error fetching menu list: " + error);
        }
    });
}

function displayMenuList(menuList) {
    var menuContainer = $(".cont-food .food ul");

    menuContainer.empty();

    for (var i = 0; i < menuList.length; i++) {
        var menu = menuList[i];

        var menuItem = '<li>' +
            '<a href="javascript:void(0);">장바구니에 담기</a>' +
            '<div class="img-wrap">' +
            `<img alt="상품이미지" src="/image/download/${menu.imgId}">` +
            '</div>' +
            '<div class="food-info-wrap">' +
            '<p class="food-name">' + menu.itemName + '</p>' +
            '<p class="food-price">' + menu.sellingPrice + '</p>' +
            '</div>' +
            '</li>';

        menuContainer.append(menuItem);
    }
}

$(document).ready(function () {
    fetchMenuList();
});*/