

/*function myFetch(url, param, handler) {

	fetch(url, {
		method:'POST',
		headers: {
			"Content-Type" : "application/json; charset=UTF-8",
			},        
		body: JSON.stringify(param),
	})
	.then((response) => response.json())
	.then((data) => {
		if(data.status) {
			if (handler != null) {
				handler(data); // 응답데이터로 처리할 로직을 handler 함수에 정의
			}
		}
	})
}*/

/* fetch 함수 */
function myFetch(url, option,  handler) {

	fetch(url, option)
	.then((response) => response.json())
	.then((data) => {
		if(data.status) {
			if (handler != null) {
				handler(data); // 응답데이터로 처리할 로직을 handler 함수에 정의
			}
		}
	})
}


// 메뉴리스트 호출
$(document).ready(() =>{
	    $.ajax({
        url: "/admin/menulist/Y/N",
        type: "GET",
        success: function (response) {
            console.log("서버에서 받은 메뉴 리스트:", response.menuList);
            displayMenuList(response.menuList);
        },
        error: function (error) {
            console.error("오류: " + error);
        }
    });
});

function displayMenuList(menuList) {
	menuList.forEach((menu) => {
		const cardDate = menu;
		const menuList = $('.menuList');
		const cardItem = $('#cardItem').clone();
		cardItem.find('img').attr('src', '/image/download/' + cardDate.IMG_ID);
		cardItem.find('.fw-bolder.menuName').text(cardDate.ITEM_NAME);
		
		let menuPrice = parseInt(cardDate.SELLING_PRICE).toLocaleString() + '원';
		
		cardItem.find('.fw-bolder.menuPrice').text(menuPrice);
		cardItem.find('#deleteMenuBnt').attr('menuId', cardDate.ITEM_ID);
		cardItem.find('#deleteMenuBnt').attr('imgId', cardDate.IMG_ID);
		cardItem.attr('menuCategory', cardDate.MENU_CATEGORY_CODE);
		cardItem.show();
		
		menuList.append(cardItem);
		menuModal.removeClass('on'); 
	})
}

function displayMenuList2(menuListData) {
	menuListData.forEach((menu, index) => {
		const cardDate = menu;
		const menuListContainer  = $('.menuList');
		const cardItem = $('#cardItem').clone();
		cardItem.find('img').attr('src', '/image/download/' + cardDate.IMG_ID);
		cardItem.find('.fw-bolder.menuName').text(cardDate.ITEM_NAME);
		
		let menuPrice = parseInt(cardDate.SELLING_PRICE).toLocaleString() + '원';
		
		cardItem.find('.fw-bolder.menuPrice').text(menuPrice);
		cardItem.find('#deleteMenuBnt').attr('menuId', cardDate.ITEM_ID);
		cardItem.find('#deleteMenuBnt').attr('imgId', cardDate.IMG_ID);
		cardItem.attr('menuCategory', cardDate.MENU_CATEGORY_CODE);
		cardItem.show();
		
		//	menuList.prepend(cardItem);
		menuListContainer.children().eq(index + 1).after(cardItem);
		menuModal.removeClass('on'); 
	})
	
	console.log("menuList>>> " + menuList);
}


function showCategory(num) {
	
  $(".menu-category li").removeClass('on');
  $("#menu0" + num).addClass('on');
	
	if(num === 1) {
		$('.col.mb-5[menuCategory!=""]').show();
	} else if(num === 2) {
		$('.col.mb-5[menuCategory!=""]').hide();
        $('[menuCategory="MC001"]').show();
	} else if(num === 3) {
		$('.col.mb-5[menuCategory!=""]').hide();
        $('[menuCategory="MC002"]').show();
	} else if(num === 4) {
		$('.col.mb-5[menuCategory!=""]').hide();
        $('[menuCategory="MC003"]').show();
	} else if(num === 5) {
		$('.col.mb-5[menuCategory!=""]').hide();
        $('[menuCategory="MC004"]').show();
	}
}

/* 메뉴 관리 모달창 */
const menuModal = $("#menuModal");

/* 메뉴 관리 모달창 > select option 목록 이벤트 핸들러 */
$(".openMenuController").on("click" , () => {
	const menuSelectListBody = $("#tbody");
	const menuItem = $("#menu-item");
	const url = "/admin/menulist/N/N"; // 등록여부: N, 카테고리: N 인 모든 목록 조회
	
	myFetch(url,{method: "GET"}, data => {
		console.log(data);
		// 상품 리스트 테이블에 가져온 상품 목록을 하나씩 추가
		const menuListData = data.menuList; 
		menuListData.forEach((menu) => {
			menuItemClone = menuItem.clone();
			menuItemClone.find(".category").text(menu.MENU_CATEGORY);
			menuItemClone.find(".menuName").text(menu.ITEM_NAME);
			menuItemClone.find(".menuPrice").text(menu.SELLING_PRICE);
			menuItemClone.find(".menuId").text(menu.ITEM_ID);
			menuItemClone.show();
			menuSelectListBody.append(menuItemClone);
		})
		
		menuModal.addClass('on');
	} )
	
})

/* 메뉴 관리 모달창 > select option 목록 이벤트 핸들러 */
 $('.form-select').on('change', () => {
    let selectedValue =  $('.form-select').find(":selected").val();
    console.log('선택한 값: ' + selectedValue);
    
	const menuSelectListBody = $("#tbody");
	const menuItem = $("#menu-item");
	const url = "/admin/menulist/N/" + selectedValue;
	menuSelectListBody.empty();
	
	myFetch(url,{method: "GET"}, data => {
		console.log(data);
		const menuListData = data.menuList;
		menuListData.forEach((menu) =>{
			menuItemClone = menuItem.clone();
			menuItemClone.find(".category").text(menu.MENU_CATEGORY);
			menuItemClone.find(".menuName").text(menu.ITEM_NAME);
			menuItemClone.find(".menuPrice").text(menu.SELLING_PRICE);
			menuItemClone.find(".menuId").text(menu.ITEM_ID);
			menuItemClone.show();
			menuSelectListBody.append(menuItemClone);
		})
		
		menuModal.addClass('on');
	} )
    
  });

/* 메뉴 관리 모달창 닫기 */
$('#closeModalBtn').on('click', () => {
	console.log("닫힘");
    // 모달 창이 닫힐 때 id="selectedItem"의 값을 초기화
    $('.form-select').val('N');
    
    // 이미지의 src를 초기화
    $('#inputGroupFile02').val('');
    $('#preview').attr('src', '');
    $('#preview').css('display', 'none');
	removeSelectedInfo();
	 
	 menuModal.removeClass('on');  
});

function removeSelectedInfo() {
    $('.selectedName').val('');
    $('.selectedPrice').val('');
    $('.selectedCode').val('');
    $('#inputGroupFile02')[0].files = null;
    $('.preview')[0].src = "";
    $('.preview').css('display','none');
    $('.selectedMenu.list').empty();
}

/* 메뉴 행 선택 이벤트 */
var previousClickedRow = null;
let selectedItem = $('.selectedItem');
$(document).on('click', '#tbody tr', function () {
	console.log(" >>> " + $(this).find('.menuId').text());
     if (previousClickedRow !==null && 
     (previousClickedRow.find('.menuId').text() == $(this).find('.menuId').text())) {
		previousClickedRow.css('background-color', '');
		 $(this).css('background-color', '');
		 previousClickedRow = null;
		 salesCode.val("");
		 
	} else {
	    // 이전에 클릭한 행의 배경색을 원래대로 복원
	    if (previousClickedRow !== null) {
	        previousClickedRow.css('background-color', '');
	    } 
	    // 새로 클릭한 행의 배경색 변경
	    $(this).css('background-color', '#e6e6fa');
	
	    // 현재 클릭한 행을 이전에 클릭한 행으로 저장
	    previousClickedRow = $(this);
	
	    // 해당 행의 paymentId 값을 가져와서 콘솔에 출력
	    var currentItemId = $(this).find('.menuId').text();
	    console.log("currentItemId >> " + currentItemId);
	    selectedItem.find('.selectedCode').val($(this).find('.menuId').text());
	    selectedItem.find('.selectedName').val($(this).find('.menuName').text());
	    selectedItem.find('.selectedPrice').val($(this).find('.menuPrice').text());
		
	}

});

const fileList = [];
const itemIdList = [];
$('.addMenuIntoList').on('click', () =>{
	 if (!$('.form-control')[0].files[0]) {
		Swal.fire({
			  title: "이미지를 선택해주세요",
			  text: "",
			  icon: "warning",
			  confirmButtonText: "확인",
			});        
        $('.selectedName').val('');
        $('.selectedPrice').val('');
        $('.selectedCode').val('');
    } else if( !$('.selectedItem.selectedCode').val()){
		Swal.fire({
			  title: "등록할 상품을 선택해주세요",
			  text: "",
			  icon: "warning",
			  confirmButtonText: "확인",
			});   
	} else {
		        // "selectedName", "selectedPrice", "selectedCode"에서 값을 가져오기
        var nameValue = $('.selectedName').val();
        var priceValue = $('.selectedPrice').val();
        var codeValue = $('.selectedCode').val();
        const isRemovedIdExist = itemIdList.includes(codeValue);

        
        if(isRemovedIdExist) {
			Swal.fire({
			  title: "이미 추가된 상품입니다.",
			  text: "",
			  icon: "error",
			  confirmButtonText: "확인",
			});   
		} else {
			  // nameValue, priceValue, codeValue를 "tag"라는 데이터 객체로 생성
        var tag = $('<tag name="tag" id="' + codeValue + '" title="' + nameValue + ' ' + priceValue + '원" contenteditable="false" spellcheck="false" tabindex="-1" class="tagify__tag tagify--noAnim" value="' + nameValue + ' ' + priceValue + '원">' +
            '<x title="" class="tagify__tag__removeBtn" role="button" aria-label="remove tag"></x>' +
            '<div><span class="tagify__tag-text">' + nameValue + ' ' + priceValue + '원</span></div></tag>');
		
        var file = $('<input type="file" name="imageFile" class="insertedImage">')
       var itemInfo = $('<input class="insertedItem" name="selectedItem" value='+ codeValue+ '>')
         file[0].files = $('#inputGroupFile02')[0].files;
		fileList.push($('#inputGroupFile02')[0].files[0]);
		itemIdList.push(codeValue);
        console.log(">>>> " + $('#inputGroupFile02')[0].files[0].name);
        // "tag"를 ".selectedMenu.list ul"에 추가
        $('.selectedMenu.list').append(tag);

        // 선택적으로 입력값을 지우거나 새로운 요소를 추가한 후에 다른 작업 수행 가능
        $('.selectedName').val('');
        $('.selectedPrice').val('');
        $('.selectedCode').val('');
        $('#inputGroupFile02')[0].files = null;
        $('.preview')[0].src = "";
        $('.preview').css('display','none');
		}
	}
})

$('#resetBnt').on('click', () =>{
	    removeSelectedInfo();
})


/* 메뉴 등록 (menu_checked : N => Y) */
/*$("#addMenuBnt").on("click", () => {
	// 체크된 tr의 menuId 클래스명을 가진 요소의 텍스트 값을 저장할 배열을 만듭니다.
	let checkedMenuIds = [];
	
	// 'menuChecked' 클래스명을 가진 체크박스 중에서 체크된 항목을 찾습니다.
	$('.menuChecked #checkedBox:checked').each(function() {
	    // 각 체크된 항목의 부모 <tr>에서 menuId 클래스명을 가진 요소의 텍스트 값을 가져와서 배열에 추가합니다.
	    let menuIdText = $(this).closest('tr').find('.menuId').text();
	    checkedMenuIds.push(menuIdText);
	});
	
	
	if(checkedMenuIds.length == 0) {
		swal("등록하실 메뉴를 선택해주세요.")
	} else if(checkedMenuIds.length > 1) {
		swal("메뉴는 한 개만 선택해주세요.")
	} else {
		
	// 체크된 항목의 menuId 값을 확인합니다.
	$('#selectedItem').val(checkedMenuIds[0]);
	console.log($('#selectedItem').val());
		
		const formData = new FormData($("#menuForm")[0]);
		const url = "/admin/addMenu";
		
		fetch(url, {
			method:'POST',
			body: formData
		})
		.then((response) => response.json())
		.then((data) => {
			if(data.status) {
				swal(data.message); 
				console.log(data.menu);
				displayMenuList([data.menu]);

			}
		})
	}
})*/

/* 메뉴 삭제 (menu_checked : Y => N) */
function deleteMenu(element) {

Swal.fire({
    title: "메뉴를 삭제하시겠습니까?",
    icon: "question",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "삭제",
    cancelButtonText: "취소",
    focusCancel: true,
    dangerMode: true,
}).then((result) => {
    if (result.isConfirmed) {
        let menuId = element.getAttribute('menuId');
        let imgId = element.getAttribute('imgId');
        let url = "/admin/deleteMenu/" + menuId + '/' + imgId; // menuId = item테이블의 item_id

        myFetch(url, { method: "GET" }, (data) => {
            if (data.status) {
                Swal.fire({
                    title: "성공",
                    text: data.message,
                    icon: "success",
                });
                // 메뉴판에서 해당 메뉴 삭제하는 코드 추가
                element.closest('.col.mb-5').remove();
            }
        });
    }
});
}

/* 첨부파일 이벤트 핸들러 */
$(document).on("change", ".form-control", function(){
    let selectedFile = this.files[0];
    let preView = $("#preview")[0]; // jQuery 객체를 JavaScript DOM 요소로 변환

    if (selectedFile) {
        let reader = new FileReader();
        reader.onload = function(e) {
            preView.src = e.target.result;
            preView.style.display = "block";
        }
        reader.readAsDataURL(selectedFile);
    } else {
		Swal.fire({
			  title: "이미지 파일을 선택하세요.",
			  text: "",
			  icon: "warning",
			  confirmButtonText: "확인",
			});
        $(this).val(""); // 파일 입력 내용 지우기
        preView.src = ""; // 미리보기 이미지 제거
    }
});


/*대시보드 */
/****** 시간대별 이용자 수 (로그인 시간 기준) ******/
const getHourlyUsersUrl = "/admin/getHourlyUsers";
myFetch(getHourlyUsersUrl, {method: "GET"}, response => {
	const hourlyUsersData = response.data;


const areaChartLabels = [];
let hour = 0;
for(i=0; i<24; i++) {
	areaChartLabels.push(hour + "시");
	hour++;
}

createAreaChart($('#areaChart1'), areaChartLabels , hourlyUsersData.year.users);
createAreaChart($('#areaChart2'), areaChartLabels , hourlyUsersData.month.users);
createAreaChart($('#areaChart3'), areaChartLabels , hourlyUsersData.day.users);
})

//--------------
//- AREA CHART -
//--------------

function createAreaChart(chart, labels, data) {

	var areaChartCanvas = chart.get(0).getContext("2d");
		
	var areaChartData = {
	  labels: labels,
	  datasets: [
	    {
	      label: "Hourly Users",
	      backgroundColor: "rgba(60,141,188,0.9)",
	      borderColor: "rgba(60,141,188,0.8)",
	      pointRadius: false,
	      pointColor: "#3b8bba",
	      pointStrokeColor: "rgba(60,141,188,1)",
	      pointHighlightFill: "#fff",
	      pointHighlightStroke: "rgba(60,141,188,1)",
	      data: data,
	    },
	  ],
	};
	
	var areaChartOptions = {
	  maintainAspectRatio: false,
	  responsive: true,
	  legend: {
	    display: false,
	  },
	  scales: {
	    xAxes: [
	      {
	        gridLines: {
	          display: false,
	        },
	      },
	    ],
	    yAxes: [
	      {
	        gridLines: {
	          display: false,
	        },
	      },
	    ],
	  },
};

// This will get the first returned node in the jQuery collection.
new Chart(areaChartCanvas, {
  type: "line",
  data: areaChartData,
  options: areaChartOptions,
});
	
	
}

/****** 전일, 금일 이용자 수 (월별 매출) ******/
const getUserCountUrl = "/admin/getUserCount";

const lastdayUsers = $('.lastdayUsers');
const todayUsers = $('.todayUsers');


myFetch(getUserCountUrl, {method: "GET"}, response =>{
		const lastDayUserCnt = response.data.lastday;
		const todayUserCnt = response.data.today;
		
		console.log(lastDayUserCnt + " " +todayUserCnt);
		
		
		
		lastdayUsers.text(lastDayUserCnt);
		todayUsers.text(todayUserCnt);
})
	
	
/****** 파이차트 (많이 팔린 메뉴 - 연, 월, 일) ******/
const pieChartUrl = "/admin/chartPieData";

myFetch(pieChartUrl, {method: "GET"}, response => {
	const pieChartData = response.data;
	createPieChart($("#pieChart1"),pieChartData.year.top6Menu, pieChartData.year.top6Sales); // 연
	createPieChart($("#pieChart2"),pieChartData.month.top6Menu, pieChartData.month.top6Sales); // 월
	createPieChart($("#pieChart3"),pieChartData.day.top6Menu, pieChartData.day.top6Sales); // 일
		
})


function createPieChart(chart, labels, data) {
	
	if(labels == null) {
		chart.closest('.tab-pane').removeClass('on');
		$('.tab-pane.nonData').addClass('on');
	} else {
		// -------------
    // - PIE CHART -
    // -------------
    // Get context with jQuery - using jQuery's .get() method.

    var donutData = {
      /*labels: ["신라면", "참깨라면", "콜라", "사이다", "소떡소떡", "짜파게티"],*/
      labels: labels,
      datasets: [
        {
          /*data: [700, 500, 400, 600, 300, 100],*/
          data: data,
          backgroundColor: [
            "#f56954",
            "#00a65a",
            "#f39c12",
            "#00c0ef",
            "#3c8dbc",
            "#d2d6de",
          ],
        },
      ],
    };
    var donutOptions = {
      maintainAspectRatio: false,
      responsive: true,
	    plugins: {
	        datalabels: { // datalables 플러그인 세팅
	          formatter: function (value, context) {
	            var idx = context.dataIndex; // 각 데이터 인덱스
	
	            // 출력 텍스트
	            return context.chart.data.labels[idx] + value + '%';
	          },
	          align: 'top', // 도넛 차트에서 툴팁이 잘리는 경우 사용
	        },
	      },
      
    };
    

    var pieChartCanvas = chart.get(0).getContext("2d");
    var pieData = donutData;
    var pieOptions = {
      maintainAspectRatio: false,
      responsive: true,
    };

    // Create pie or douhnut chart
    // You can switch between pie and douhnut using the method below.

    new Chart(pieChartCanvas, {
      type: "pie",
      data: pieData,
      options: donutOptions,
    });
	}
}


/****** 스택 바 차트 (월별 매출), 라인 차트 (월별 이용자수) ******/
const url = "/admin/getChartData";
const labels = [];

const today = new Date();
const thisMonth = today.getMonth(); // 0부터 시작 
const lastMonthSalse = $('.lastMonthSalse');
const thisMonthSalse = $('.thisMonthSalse');


myFetch(url, {method: "GET"}, response =>{
	const menuData = response.data.menu;
	const pcData = response.data.pc;
	const total = response.data.total;
	const users = response.data.users;
	
	fetch("/admin/chartData",  {method: "GET"})
	.then((response) => response.json())
	.then((data) => {
		if(data.status) {
			let thisMonthData = data.data;
			menuData.push(thisMonthData.menu[0]);
			pcData.push(thisMonthData.pc[0]);
			total.push(thisMonthData.total[0]);
			users.push(thisMonthData.users[0]);
			
	lastMonthSalse.text(Number(total[thisMonth -1]).toLocaleString('ko-KR') + "원");
	thisMonthSalse.text(Number(total[thisMonth]).toLocaleString('ko-KR') + "원");

    //---------------------
    //- STACKED BAR CHART -
    //---------------------

    var stackedChartData = {
      labels: [
        "Jan", "Feb", "Mar","Apr", "May", "Jun"
        , "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
      ],

      datasets: [
        {
          label: "음식 판매",
          backgroundColor: "rgba(60,141,188,0.9)",
          borderColor: "rgba(60,141,188,0.8)",
          pointRadius: false,
          pointColor: "#3b8bba",
          pointStrokeColor: "rgba(60,141,188,1)",
          pointHighlightFill: "#fff",
          pointHighlightStroke: "rgba(60,141,188,1)",
          /* data: [28, 48, 40, 19, 24, 27, 30, 20, 15, 23, 31, 35], */
          data: menuData,
        },
        {
          label: "PC방 이용",
          backgroundColor: "rgba(210, 214, 222, 1)",
          borderColor: "rgba(210, 214, 222, 1)",
          pointRadius: false,
          pointColor: "rgba(210, 214, 222, 1)",
          pointStrokeColor: "#c1c7d1",
          pointHighlightFill: "#fff",
          pointHighlightStroke: "rgba(220,220,220,1)",
          /* data: [65, 59, 80, 81, 56, 55, 40, 90, 60, 57, 76, 83], */
          data: pcData,
        },
      ],
    };

    var barChartData = $.extend(true, {}, stackedChartData);
    var temp0 = stackedChartData.datasets[0];
    var temp1 = stackedChartData.datasets[1];
    barChartData.datasets[0] = temp1;
    barChartData.datasets[1] = temp0;

    var stackedBarChartCanvas = $("#stackedBarChart")
      .get(0)
      .getContext("2d");
    var stackedBarChartData = $.extend(true, {}, barChartData);

    var stackedBarChartOptions = {
      responsive: true,
      maintainAspectRatio: false,
      plugins: false,
      
      scales: {
        xAxes: [
          {
            stacked: true,
          },
        ],
            yAxes: [{
			    ticks: {
			        beginAtZero: true,
			        callback: function(value, index, values) {
			        if(values[0].toString().length > 9 && value != 0) return (Math.floor(value / 100000000)).toLocaleString("ko-KR") + "억";
			        else if(values[0].toString().length = 9 && value != 0) return (value / 100000000).toFixed(1) + "억";
			        else if(values[0].toString().length > 6 && value != 0) return (Math.floor(value / 10000)).toLocaleString("ko-KR") + "만";
			        else if(values[0].toString().length = 6 && value != 0) return (value / 10000).toFixed(1) + "만";
			        else return value.toLocaleString("ko-KR");
			        }
			    }
    }],
      },
    };

    new Chart(stackedBarChartCanvas, {
      type: "bar",
      data: stackedBarChartData,
      options: stackedBarChartOptions,
    });
    
    

    //-------------
    //- LINE CHART -
    //--------------

    var lineChartData = {
      labels: [
        "Jan", "Feb", "Mar","Apr", "May", "Jun"
        , "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
      ],

      datasets: [
        {
          label: "이용자수",
          backgroundColor: "rgba(60,141,188,0.9)",
          borderColor: "rgba(60,141,188,0.8)",
          pointRadius: false,
          pointColor: "#3b8bba",
          pointStrokeColor: "rgba(60,141,188,1)",
          pointHighlightFill: "#fff",
          pointHighlightStroke: "rgba(60,141,188,1)",
          /*data: [
            28, 48, 40, 19, 24, 27, 30, 20, 15, 23, 31, 35, 28, 48, 40, 19,
            24, 27, 30, 20, 15, 23, 31, 35,
          ],*/
          data: users,
        },
      ],
    };

    var lineChartDataOptions = {
      maintainAspectRatio: false,
      responsive: true,
      legend: {
        display: false,
      },
      scales: {
        xAxes: [
          {
            gridLines: {
              display: false,
            },
          },
        ],
        yAxes: [
          {
            gridLines: {
              display: false,
            },
          },
        ],
      },
    };

    var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
    var lineChartOptions = $.extend(true, {}, lineChartDataOptions);
    var lineChartData = $.extend(true, {}, lineChartData);
    lineChartData.datasets[0].fill = false;
    lineChartOptions.datasetFill = false;

    new Chart(lineChartCanvas, {
      type: "line",
      data: lineChartData,
      options: lineChartOptions,
    });
		}
	})

}) 

	
