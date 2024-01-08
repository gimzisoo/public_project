const listSeat = $('.seat_grp ul li');
const infoModal = $('#infoModal');
const listChat = document.querySelectorAll('.btn_grp ul li');

listSeat.each(function(index, li) {
    const em = $('<em>').text(index + 1);
    $(li).find('a').before(em);
    $(li).attr("data-seatNo", index+1);
});

//회원정보 모달창 닫기
$('window').on('click', (e) => {
    if (e.currentTarget != infoModal) {
        infoModal.css("display",'none');
    }
});


/*let admimPageChange = true;

function changeAdminPage() {
	if(admimPageChange) {
		$('.admin_main').css("display", "none");
		$('.admin_dash').css("display", "block");
		$('.btn_grp li:nth-child(3) a').css("display", "block");
		$('.btn_grp li:nth-child(4) a').css("display", "none");
		admimPageChange = false;
	} else {
		$('.admin_main').css("display", "block");
		$('.admin_dash').css("display", "none");
		$('.btn_grp li:nth-child(3) a').css("display", "none");
		$('.btn_grp li:nth-child(4) a').css("display", "block");
		admimPageChange = true;
	}
}*/


/* 관리자 좌석현황 <-> 대시보드 전환 */
function changeAdminPage() {
	if($(".admin_main").hasClass('on')) {
		$(".admin_main").removeClass('on');
		$(".admin_dash").toggleClass('on');
		$(".admin_food").removeClass('on');
		$('.btn_grp li:nth-child(3) a').css("display", "block");
		$('.btn_grp li:nth-child(4) a').css("display", "none");
	}else if($(".admin_food").hasClass('on')) {
		$(".admin_food").removeClass('on');
		$(".admin_main").addClass('on');
		$('.btn_grp li:nth-child(3) a').css("display", "none");
		$('.btn_grp li:nth-child(4) a').css("display", "block");
	}
	else{
		$(".admin_main").toggleClass('on');
		$(".admin_dash").toggleClass('on');
		$('.btn_grp li:nth-child(3) a').css("display", "none");
		$('.btn_grp li:nth-child(4) a').css("display", "block");
	}
}

/* 관리자 메뉴 관리 화면으로 전환 */
function addFoodPage() {
	$(".admin_food").addClass('on')
	$(".admin_main").removeClass('on');
	$(".admin_dash").removeClass('on');
	$('.btn_grp li:nth-child(3) a').css("display", "block");
	$('.btn_grp li:nth-child(4) a').css("display", "none");
	}
	

function showAlert(){
	if($(".wrap_chat").hasClass('on')){
		$(".wrap_chat").removeClass('on');
		$(".wrap_alert").toggleClass('on');
	}else{
		$('.wrap_cont').toggleClass('lft');
		$('.py-5').toggleClass('lft');
		$('.menu-category').toggleClass('lft');
		$(".wrap_alert").toggleClass('on');
	}
}

function showChat(){
	if($(".wrap_alert").hasClass('on')){
		$(".wrap_alert").removeClass("on");
		$(".wrap_chat").toggleClass('on');
		$("#chatList").scrollTop($("#chatList")[0].scrollHeight);
	}else{
		$('.wrap_cont').toggleClass("lft");
		$('.py-5').toggleClass('lft');
		$('.menu-category').toggleClass('lft');
		$(".wrap_chat").toggleClass("on");
		$("#chatList").scrollTop($("#chatList")[0].scrollHeight);
	}
}
/* 아코디언 */
$(document).on("click", ".accordion", function() {
  $(this).toggleClass("active");
  var panel = $(this).next();

  if (panel.css("max-height") !== "0px") {
    panel.css("max-height", "0px");
  } else {
    panel.css("max-height", panel[0].scrollHeight + "px");
  }
});
/* 탭 메뉴 */
$(document).ready(function() {
  // 초기에 첫 번째 탭을 활성화
  $(".tab__item:first").addClass("active");
  $(".tab__content:first").addClass("active");

  // 탭을 클릭했을 때 내용 변경
  $(".tab__item").click(function(e) {
    e.preventDefault(); // 링크의 기본 동작 방지

    // 모든 탭 내용 부분을 숨김
    $(".tab__content").removeClass("active");

    // 모든 탭 버튼 비활성화
    $(".tab__item").removeClass("active");

    // 클릭한 탭에 active 클래스 추가
    $(this).addClass("active");

    // 클릭한 탭과 동일한 인덱스의 탭 내용을 표시
    var index = $(this).index();
    $(".tab__content:eq(" + index + ")").addClass("active");
  });
});
$('.close').on('click', function(){
	infoModal.css('display', 'none');
	$(".tab__item:first").addClass("active");
	$(".tab__item:last").removeClass("active");
  	$(".tab__content:first").addClass("active");
  	$('.tab__content:last').removeClass('active');
});