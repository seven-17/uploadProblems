<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>Henu-ACM题目数量统计</title>
	<link rel="stylesheet" type="text/css" href="css/icon.css" />
	<link rel="stylesheet" type="text/css" href="css/table.min.css" />
	<link rel="stylesheet" type="text/css" href="css/semantic.min.css" />
	<script src="js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/echarts.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/semantic.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<div class="main ui container" style="padding: 30px;">
		<!-- 导航开始 -->
		<div class="ui top attached tabular menu">
			<a class="item active" href="${pageContext.request.contextPath }/index.jsp">首页</a>
			<a class="item" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=1">动态</a>
			<a class="item" href="${pageContext.request.contextPath }/showData?method=showRank">排名</a>
		
			<c:if test="${empty session_user }">
				<a class="item" href="${pageContext.request.contextPath }/login.jsp">登录</a>
			</c:if>
			<c:if test="${!empty session_user }">
				<a class="item" href="${pageContext.request.contextPath }/user?method=getUserInfo&user_id=${session_user.user_id}">个人中心</a>
			</c:if>
		</div>
		<!-- 导航结束 -->

		<!-- 主体内容开始 -->
		<div class="ui bottom attached segment">
			<div class="ui two item stackable tabs menu">
				<a class="item active" data-tab="Specifications">总览</a>
				<a class="item" data-tab="note">公告</a>
			</div>

			<div class="ui tab active" data-tab="Specifications">
				<h4 class="ui horizontal divider header"><i class="bar chart icon"></i> Specifications </h4>
				
				<div id="main" style="width: 100%;height:400px; margin:0 auto"></div>
				<div id="line1" style="width:100%; height:600px; margin: auto"></div>

				<script type="text/javascript">
					var myChart = echarts.init(document.getElementById('line1'));
					
					$.ajax({
						async:false,
						dataType:"text",
						success:function(value){
							data = eval("("+value+")");
						},
						type:"POST",
						url:"${pageContext.request.contextPath}/showData?method=getIndexZheXianData"
					});
					
					var dateList = data.map(function (item) {
						return item[0];
					});
					var valueList = data.map(function (item) {
						return item[1];
					});
					option = {
						// Make gradient line here
						visualMap: [{
							show: false,
							type: 'continuous',
							seriesIndex: 0,
							min: 0,
							max: 40
						}],
						title: [{
	                        left: 'center',
	                        text: 'Henu-ACM最近一年做题数统计'
	                    }],
						tooltip: {
							trigger: 'axis'
						},
						xAxis: [{
							data: dateList
						}],
						yAxis: [{
							splitLine: {
								show: false
							}
						}],
						grid: [{
							bottom: '60%'
						}, {
							top: '60%'
						}],
						series: [{
							type: 'line',
							showSymbol: false,
							data: valueList
						}]
					};
					myChart.setOption(option);
				</script>
				<script type="text/javascript">
					var myChart = echarts.init(document.getElementById('main'));
					//console.log("show Chart");
					// 指定图表的配置项和数据
					// 使用刚指定的配置项和数据显示图表。
					var data = genData();
					var option = {
						title: {
							text: 'Henu-ACM最近一年做题数统计',
							subtext: '选择对应的队员 会出现在饼图里',
							x: 'center'
						},
						tooltip: {
							trigger: 'item',
							formatter: "{a} <br/>{b} : {c} ({d}%)"
						},
						legend: {
							type: 'scroll',
							orient: 'vertical',
							right: 10,
							top: 20,
							bottom: 20,
							data: data.legendData,
							selected: data.selected
						},
						series: [{
							name: '姓名',
							type: 'pie',
							radius: '55%',
							center: ['40%', '50%'],
							data: data.seriesData,
							itemStyle: {
								emphasis: {
									shadowBlur: 10,
									shadowOffsetX: 0,
									shadowColor: 'rgba(0, 0, 0, 0.5)'
								}
							}
						}]
					};
					function genData() {
						var legendData = [];
						var seriesData = [];
						var selected = {};
						
						$.ajax({
							async:false,
							dataType:"json",
							success:function(value){
								for (var i = 0; i < value.length; i++) {
									name = value[i].user_name;
									legendData.push(name);
									seriesData.push({
										name: name,
										value: value[i].num
									});
									selected[name] = i < 10;
								}
							},
							type:"POST",
							url:"${pageContext.request.contextPath}/showData?method=getBingTuData"
						});
						return {
							legendData: legendData,
							seriesData: seriesData,
							selected: selected
						};
					}
					myChart.setOption(option);
				</script>
			</div>
			<div class="ui tab" data-tab="note">
				<h4 class="ui horizontal divider header"><i class="tag icon"></i>Description</h4>
				<div class="ui message">
						<div class="header">注意</div>
						<ul class="list">
							<li>此系统主要为了刺激大家刷题,不要为排名而刷水题</li>
							<li>....</li>
						</ul>
					</div>
					<div class="ui message">
						<div class="header">提交反馈</div>
						<ul class="list">
							<li>如果您对本系统有任何意见,请联系以下任意邮箱</li>
							<li>王鹏程: 528007307@qq.com</li>
							<li>开发组欢迎各位提出您宝贵的建议</li>
						</ul>
					</div>
				<div class="ui message">
						<div class="header">已支持的OJ</div>
						<ul class="list">
							<li>HduOJ</li>
							<li>Code Forces</li>
							<li>敬请期待....</li>
						</ul>
					</div>
				<div class="ui message">
					<div class="header">使用方法</div>
					<ul class="list">
						<li>注册用户</li>
						<li>提交自己在每个OJ的真实id</li>
					</ul>
				</div>
				<div class="ui message">
					<div class="header">功能</div>
					<ul class="list">
						<li>实时刷新AC的数量，综合排名</li>
						<li>....</li>
					</ul>
				</div>
				<div class="ui message">
					<div class="header">开发这个网站的目的</div>
					<ul class="list">
						<li>可视化每个人的日常刷题记录</li>
						<li>让大家的努力有目共睹</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- 主体内容结束 -->
	</div>
	<script type="text/javascript">
		$(document).ready(function () {
			$('.tabs.menu .item').tab();
		});
	</script>
</body>
</html>