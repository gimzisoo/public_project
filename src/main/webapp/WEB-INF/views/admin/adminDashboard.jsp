<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/plugins/chart.js/Chart.css"/>
<script src="/plugins/chart.js/Chart.min.js"></script>
 <script src="/plugins/jquery/jquery.min.js"></script>
 
<!--   <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.2/js/dataTables.buttons.min.js"></script>
<!-- <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.2/js/buttons.flash.min.js"></script> -->
<!-- <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script> -->
<!-- <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script> -->
<!-- <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script> -->
<!-- <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.2/js/buttons.html5.min.js"></script> -->
<!-- <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.2/js/buttons.print.min.js"></script> -->


<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.23.0/moment.min.js"></script> -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.min.js"></script> -->
<!-- <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@0.7.0"></script> -->
  <!-- 버전 2 (최신) -->
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2"></script>

<style>
.chartjs-render-monitor {
	max-height: 300px!important;
}
</style>
  
    
 <div class="admin_dash">
<div class="dashboard" style="padding: 20px 100px 20px 100px">



<!--       Small boxes (Stat box) -->
      <div class="row">
        <div class="col-lg-3 col-6">
          <!-- small box -->
          <div class="small-box bg-info">
            <div class="inner">
              <h3 class="data lastdayUsers" style="display: inline-block; margin-right: 5px;">150</h3><span style="font-size:20px;"> 명</span>

              <p>전일 사용자</p>
            </div>
            <div class="icon">
              <i class="ion ion-person"></i>
            </div>
          </div>
        </div>
        
<!--         ./col -->
        <div class="col-lg-3 col-6">
<!--           small box -->
          <div class="small-box bg-success">
            <div class="inner">
              <h3 class="data todayUsers" style="display: inline-block; margin-right: 5px;">150</h3><span style="font-size:20px;"> 명</span>

              <p>금일 사용자</p>
            </div>
            <div class="icon">
              <i class="ion ion-person-add"></i>
            </div>
          </div>
        </div>
        
<!--         ./col -->
        <div class="col-lg-3 col-6">
<!--           small box -->
          <div class="small-box bg-warning">
            <div class="inner">
              <h3 class="data lastMonthSalse">150<span style="font-size:20px;"> 백만원</span></h3>

              <p>전월 매출</p>
            </div>
            <div class="icon">
              <i class="ion ion-stats-bars"></i>
            </div>
          </div>
        </div>
<!--         ./col -->
        <div class="col-lg-3 col-6">
<!--           small box -->
          <div class="small-box bg-danger">
            <div class="inner">
              <h3 class="data thisMonthSalse">150<span style="font-size:20px;"> 백만원</span></h3>

              <p>금월 매출</p>
            </div>
            <div class="icon">
              <i class="ion ion-pie-graph"></i>
            </div>
          </div>
        </div>
      </div>
<!--       /.row -->
      
      
      <div class="row">
          <div class="col-8">
            <!-- interactive chart -->
            <div class="card card-primary card-outline">
              <div class="card-header">
                <h3 class="card-title">
                  <i class="far fa-chart-bar"></i>
                  Hourly Users
                </h3>
                
                 <div class="card-tools">
                  <ul class="nav nav-pills ml-auto">
                    <li class="nav-item">
                      <a class="nav-link active" href="#tap-areaChart1" data-toggle="tab">연</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="#tap-areaChart2" data-toggle="tab">월</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="#tap-areaChart3" data-toggle="tab">일</a>
                    </li>
                  </ul>
                </div>

              </div>
            
               <div class="card-body">
                <div class="tab-content p-0">
                
                   <div class="chart tab-pane active" id="tap-areaChart1"
                       style="position: relative; height: 100%;">
		                 <div class="chart">
		                   <canvas
		                     id="areaChart1"
		                     style="
		                       min-height: 200px;
		                       height: 200px;
		                       max-height: 250px;
		                       max-width: 100%;
		                     "
		                   ></canvas>
		                   </div>
                   </div>
                
                
                   <div class="chart tab-pane" id="tap-areaChart2"
                       style="position: relative; height: 100%;">
		                 <div class="chart">
		                   <canvas
		                     id="areaChart2"
		                     style="
		                       min-height: 200px;
		                       height: 200px;
		                       max-height: 250px;
		                       max-width: 100%;
		                     "
		                   ></canvas>
		                   </div>
                   </div>
                   
                  <div class="chart tab-pane" id="tap-areaChart3"
                       style="position: relative; height: 100%;">
		                 <div class="chart">
		                   <canvas
		                     id="areaChart3"
		                     style="
		                       min-height: 200px;
		                       height: 200px;
		                       max-height: 250px;
		                       max-width: 100%;
		                     "
		                   ></canvas>
		                   </div>
                   </div>
                 
                 </div>
               </div>
            
            <!-- /.card -->

          </div>
          </div>
          <!-- /.col -->
          
           <div class="col-4">
            <!-- pie chart -->
            <div class="card card-primary card-outline">
              <div class="card-header">
                <h3 class="card-title">
                  <i class="far fa-chart-bar"></i>
                  Top6 Menu
                </h3>
                 <div class="card-tools">
                  <ul class="nav nav-pills ml-auto">
                    <li class="nav-item">
                      <a class="nav-link active" href="#tap-pieChart1" data-toggle="tab">연</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="#tap-pieChart2" data-toggle="tab">월</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="#tap-pieChart3" data-toggle="tab">일</a>
                    </li>
                  </ul>
                </div>
                
              </div>
              
              <div class="card-body">
                <div class="tab-content p-0">
                  <!-- Morris chart - Sales -->
                  <div class="chart tab-pane active" id="tap-pieChart1"
                       style="position: relative; height: 100%;">
             		<canvas
                      id="pieChart1"
                      style="
                        min-height: 200px;
                        height: 200px;
                        max-height: 200px;
                        max-width: 100%;
                      "
                    ></canvas>
                   </div>
                   
                    <div class="chart tab-pane" id="tap-pieChart2"
                       style="position: relative; height: 100%;">
             		<canvas
                      id="pieChart2"
                      style="
                        min-height: 200px;
                        height: 200px;
                        max-height: 200px;
                        max-width: 100%;
                      "
                    ></canvas>
                   </div>
                   
                    <div class="chart tab-pane" id="tap-pieChart3"
                       style="position: relative; height: 100%;">
             		<canvas
                      id="pieChart3"
                      style="
                        min-height: 200px;
                        height: 200px;
                        max-height: 200px;
                        max-width: 100%;
                      "
                    ></canvas>
                   </div>
                   <div class="chart tab-pane nonData" id="tap-nonData"
                       style="position: relative; height: 100%;">
                       <div 
	                        style="
	                        min-height: 200px;
	                        height: 200px;
	                        max-height: 200px;
	                        max-width: 100%;
	                      "
                       >
                       <div class="icon" id="orderIcon" style="font-size: 70px; opacity: 0.5;">
			              <div class="icon ion-sad-outline"></div>
			           </div>
			              <p>주문 내역이 없습니다.</p>
	                       </div>
                   </div>
              	</div>

          	</div> <!-- /.card-body -->
        </div> <!-- /.card -->
       </div> <!-- /.col -->
      </div> <!-- /.row -->
      
      <div class="row">
        <div class="col-md-6">
          <!-- STACKED BAR CHART -->
          <div class="card card-success">
            <div class="card-header">
              <h3 class="card-title">Monthly Sales</h3>

              <div class="card-tools">
                <button
                  type="button"
                  class="btn btn-tool"
                  data-card-widget="collapse"
                >
                  <i class="fas fa-minus"></i>
                </button>
                <button
                  type="button"
                  class="btn btn-tool"
                  data-card-widget="remove"
                >
                  <i class="fas fa-times"></i>
                </button>
              </div>
            </div>
            <div class="card-body">
              <div class="chart">
                <canvas
                  id="stackedBarChart"
                  style="
                    min-height: 200px;
                    height: 200px;
                    max-height: 200px;
                    max-width: 100%;
                  "
                ></canvas>
              </div>
            </div>
            <!-- /.card-body -->
          </div>
          <!-- /.card -->
        </div>

        <div class="col-md-6">
          <!-- LINE CHART -->

          <div class="card card-info">
            <div class="card-header">
              <h3 class="card-title">Monthly Users</h3>

              <div class="card-tools">
                <button
                  type="button"
                  class="btn btn-tool"
                  data-card-widget="collapse"
                >
                  <i class="fas fa-minus"></i>
                </button>
                <button
                  type="button"
                  class="btn btn-tool"
                  data-card-widget="remove"
                >
                  <i class="fas fa-times"></i>
                </button>
              </div>
            </div>
            <div class="card-body">
              <div class="chart">
                <canvas
                  id="lineChart"
                  style="
                    min-height: 200px;
                    height: 200px;
                    max-height: 200px;
                    max-width: 100%;
                  "
                ></canvas>
              </div>
            </div>
            <!-- /.card-body -->
          </div>
          <!-- /.card -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
      </div>
      <!-- /.card -->
    </div>
    <!-- /.dashboard -->
 
 
 
 </div>

<!-- jQuery Mapael -->
<script src="/plugins/jquery-mousewheel/jquery.mousewheel.js"></script>
<script src="/plugins/raphael/raphael.min.js"></script>

<!-- <!-- FLOT CHARTS -->
<!-- <script src="/plugins/flot/jquery.flot.js"></script> -->
<!-- <script src="/plugins/flot/plugins/jquery.flot.resize.js"></script> -->
<!-- <script src="/plugins/flot/plugins/jquery.flot.pie.js"></script>  -->
