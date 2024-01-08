<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %><!-- 태크라이브러리 -->

<sec:authorize access="isAuthenticated()">
<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<link rel="stylesheet" href="/css/user.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="/plugins/jquery/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


<style>
.payment_content {
   display: none;
   position: absolute;
   top: 50%;
   left: 50%;
   margin: -275px 0 0 -250px;
   padding: 50px 75px;
   width: 500px;
   height: 550px;
   box-sizing: border-box;
   border-radius: 5px;
   background-color: rgba(0,0,0,0.6);
}

.payment_content h2 {
    font-size: 28px;
    color: #fff;
}

.payment_form {
    margin-top: 30px;
}

.payment_form li {
    position: relative;
    height: 20px;
    color: #fff;
}

.payment_form li:last-child {
    height: 80px;
}

.payment_form li + li {
    margin-top: 30px;
}

.payment_form li:last-child > p {
	height: 30px;
	line-height: 30px;
}

.payment_form .charge_time {
	position: relative;
	margin-top: 10px;
	height: 40px;
}

.payment_form .charge_time p {
	height: 40px;
	line-height: 40px;
    text-align: center;
    font-size: 14px;
}
.payment_form .charge_time p em {
	padding: 0 5px;
	font-size: 22px;
	font-weight: bold;
	font-style: normal;
}
.payment_form li .btn_charge {
    position: absolute;
    display: block;
    top: 50%;
    margin-top: -15px;
    width: 30px;
    height: 30px;
    overflow: hidden;
    border-radius: 50%;
    background-color: #fff;
}

.payment_form li .btn_charge.minus {
	left: 30px;
	background: url("/images/minus-button.png") no-repeat center;
	background-size: 100%;
}
.payment_form li .btn_charge.plus {
	right: 30px;
	background: url("/images/plus-button.png") no-repeat center;
	background-size: 100%;
}

.payment_form li .btn_charge a {
    display: block;
    line-height: 30px;
    text-indent: -999em;
}
.payment_form li .charge_time {
    text-align: center;
}
.li_paymethod ul {
    padding: 0;
}

.li_paymethod li {
    margin-top: 30px;
    width: 350px;
    height: 50px;
    border: none;
    border-radius: 5px;
    background-color: #440977;
}
.li_paymethod a {
    display: block;
    line-height: 50px;
    font-size: 18px;
    color: #fff;
    text-align: center;
}
.canclePayment {
	position: absolute;
	top: 25px;
	right: 25px;
	text-indent: -999em;
	overflow: hidden;
	cursor: pointer;
	border-radius: 50%;
	background-color: none;
	background: url("/images/cancle.png") no-repeat center;
	background-size: 100%;
}
.canclePayment a {
	display: block;
	width: 40px;
	height: 40px;
}
</style>
<body>
    <div class="logo"><img src="/images/logo_w.png" alt="로고"></div>
    <div class="login_content" style="${showPaymentContent ? 'display:none;' : 'display:block;'}">
        <div class="id_pw_form">
            <h2>로그인</h2>
            <form id="loginForm" method="post" action="/login" >
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            
                <div class="ipt ipt_id">
                    <label class="placeLabel">아이디</label>
                    <span></span>
                    <input type="text" name="username" id="userId" placeholder="아이디">
                </div>
                <div class="ipt ipt_pwd">
                    <label class="placeLabel">비밀번호</label>
                    <span></span>
                    <input type="password" name="password" id="password" placeholder="비밀번호">
                </div>
                <button class="btn login_btn" type="submit" id="login">로그인</button>
            </form>
            
            <div class="btn kakao_btn">
                <a href="https://kauth.kakao.com/oauth/authorize?client_id=f9233fa5fb65f7da3addba11aa52d18a&redirect_uri=http://localhost:8282/auth/kakao/callback&response_type=code">
                    <img src="/images/kakao_login.png" alt="kakao">
                </a>
            </div>

            <div class="li_login">
                <ul>
                    <li><a href="javascript:void(0);">회원가입</a></li>
                    <li><a href="javascript:void(0);">아이디 / 비번찾기</a></li>
                </ul>
            </div>
        </div>
    </div>
	<div class="payment_content" style="${showPaymentContent ? 'display:block;' : 'display:none;'}">

        <h2>시간 충전</h2>
        <div class="payment_form">
            <ul>
                <li>
                    <p><span id="userId" class="getUserId">${principal.user.userId }</span>님!</p>
                </li>
                <li>
                    <p>충전 시간</p>
                    
                    <div class="charge_time">
                    	<span class="btn_charge minus"><a href="javascript:void(0);">-</a></span>
                    	<p>
	                        <em id="chargeTime">1</em>시간
	                    </p>
                        <span class="btn_charge plus"><a href="javascript:void(0);">+</a></span>
                    </div>
                    
                </li>
            </ul>
        </div>
        <div class="li_paymethod">
            <ul>
                <li id="card">
                    <a href="javascript:void(0);">
                        카드결제
                    </a>
                </li>
                <li id="cash">
                    <a href="javascript:void(0);">
                        계좌이체
                    </a>
                </li>
                <li id="smart">
                    <a href="javascript:void(0);">
                        간편결제
                    </a>
                </li>
            </ul>
        </div>
       <div class="canclePayment"><a>취소</a></div> 
    </div>
</body>
<script>
document.addEventListener('DOMContentLoaded', function() {
    var urlParams = new URLSearchParams(window.location.search);
    var exception = urlParams.get('exception');
    
    console.log(urlParams);
    console.log(exception);

    if (exception === 'failLogin') {
		Swal.fire({
			  title: "비밀번호가 틀렸습니다.",
			  text: "다시 확인해 주세요.",
			  icon: "error",
			  confirmButtonText: "확인",
			}).then((value) => {
		  if (value) {
			 location.href = "/";
		  }
		});
    	/*  swal("Error","비밀번호가 틀렸습니다.",'warning'); */
    } else if (exception === 'loginLock'){
    	/* swal("Error","잠긴 계정입니다.",'warning'); */
   			Swal.fire({
			  title: "잠긴 계정입니다.",
			  text: "관리자에게 문의해주세요.",
			  icon: "error",
			  confirmButtonText: "확인",
			}).then((value) => {
		  if (value) {
			 location.href = "/";
		  }
		});
    }
});
</script>

<script>
// 잔여시간 없음
window.addEventListener('DOMContentLoaded', (event) => {
    const showPaymentContent = ${showPaymentContent};
//     const userId = "${userId}";
//     $('.getUserId').text(userId);
    
    if (!showPaymentContent) {
        document.querySelector('.login_content').style.display ="none";
        document.querySelector('.payment_content').style.display = 'block';
    }
});

// 시간 충전
$('.li_paymethod li').on('click', function(e) {
    const userId = $('.getUserId').text();
    const chargeTime = $('#chargeTime').text();
    const paymentMethodCode = $(this).attr('id');

    const param = {
        userId: userId,
        remainingTime: chargeTime * 3600,
        paymentMethodCode: paymentMethodCode
    };
	console.log(">>>> " + userId +" "+ chargeTime * 3600+ " "+paymentMethodCode);
    fetch('/user/rechargeTime', {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify(param),
    })
    .then((rs) => rs.json())
    .then((json) =>{
        if (json.rs == "success") {
   			Swal.fire({
    			  title: "충전이 정상적으로 처리되었습니다.",
    			  text: "감사합니다.",
    			  icon: "success",
    			  confirmButtonText: "확인",
    			}).then((value) => {
			  if (value) {
				 location.href = "/";
			  }
			});
        } else {
   			Swal.fire({
  			  title: "충전이 정상적으로 처리되지 않았습니다.",
  			  text: "관리자에게 문의 해주세요.",
  			  icon: "error",
  			  confirmButtonText: "확인",
  			}).then((value) => {
  			  if (value) {
  				 location.href = "/";
  			  }
  			});
        }
    });
});




// 충전시간 조절
let chargeTime = 1;

$('.minus').on('click', function(e) {
    if (chargeTime > 1) {
        chargeTime--; 
        updateChargeTime();
    }
});

$('.plus').on('click', function(e) {
    chargeTime++; 
    updateChargeTime();
});


function updateChargeTime() {
    $('.charge_time em').text(chargeTime);
}


$('.canclePayment').on('click', function(e){
	$('#password').val('');
	$('.login_content').css('display', 'block');
	$('.payment_content').css('display', 'none');
});



// 로그인 폼 스타일
window.addEventListener('DOMContentLoaded', (event) => {
    const inputId = document.querySelectorAll('.ipt_id input');
    const inputPwd = document.querySelectorAll('.ipt_pwd input');

    inputId.forEach((input) => {
        input.addEventListener('focus', () => {
            const spanIcon = input.previousElementSibling;
            spanIcon.style.backgroundImage = 'url("/images/id_off.png")';
        });

        input.addEventListener('blur', () => {
            const spanIcon = input.previousElementSibling;
            spanIcon.style.backgroundImage = 'url("/images/id_on.png")';
        });
    });

    inputPwd.forEach((input) => {
        input.addEventListener('focus', () => {
            const spanIcon = input.previousElementSibling;
            spanIcon.style.backgroundImage = 'url("/images/key_on.png")';
        });

        input.addEventListener('blur', () => {
            const spanIcon = input.previousElementSibling;
            spanIcon.style.backgroundImage = 'url("/images/key_off.png")';
        });
    });
});

function failLogin() {
		Swal.fire({
			  title: "잔여 시간이 없습니다.",
			  text: "충전해 주세요.",
			  icon: "warning",
			  confirmButtonText: "확인",
			})
}
</script>

</html>