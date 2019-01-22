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
    <link rel="stylesheet" type="text/css" href="css/grid.min.css">
    <link rel="stylesheet" type="text/css" href="css/card.min.css">
    <link rel="stylesheet" href="./css/label.min.css">
    <link rel="stylesheet" href="./css/image.min.css">
    <link rel="stylesheet" href="css/form.min.css">
    <script src="js/echarts.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/semantic.min.js" type="text/javascript" charset="utf-8"></script>
    <style>
        #myform{
	        margin: 0 auto;
	    }
    </style>
</head>
<body>
    <div class="main ui container" style="padding: 30px;">
        <!-- 导航开始 -->
        <div class="ui top attached tabular menu">
			<a class="item" href="${pageContext.request.contextPath }/index.jsp">首页</a>
			<a class="item" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=1">动态</a>
			<a class="item" href="${pageContext.request.contextPath }/showData?method=showRank">排名</a>
		
			<c:if test="${empty session_user }">
				<a class="item" href="${pageContext.request.contextPath }/login.jsp">登录</a>
			</c:if>
			<c:if test="${!empty session_user }">
				<a class="item active" href="${pageContext.request.contextPath }/user?method=getUserInfo&user_id=${session_user.user_id}">个人中心</a>
			</c:if>
		</div>
        <!-- 导航结束 -->

        <!-- 主体内容开始 -->
        <div class="ui bottom attached segment">
            <!-- 个人主页 -->
            <div class="ui celled grid">
                <div class="row">
                    <div class="six wide column">
                        <div class="ui raised segment"><a href="javascript:void(0);" class="ui blue ribbon label">ACM队员</a>
                            <div class="ui card">
                                <a class="image" href="javascript:void(0);">
                                	<c:if test="${empty user.user_photourl }">
                                		<img src="${pageContext.request.contextPath }/img/steve.jpg">
                                	</c:if>
                                    <c:if test="${!empty user.user_photourl }">
                                   		<img src="${pageContext.request.contextPath }/${user.user_photourl}">
                                    </c:if>
                                </a>
                                <div class="content">
                                    <a class="header" href="javascript:void(0);">${user.user_name }</a>
                                    <div class="meta">
                                        <a href="javascript:void(0);">${user.user_username }</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="ten wide column">
                        <h3 class="ui horizontal divider header">Information</h3>
                        <form class="ui form ">
                            <div class="fields">
                                <div class="field">
                                    <label>RealName</label>
                                    <input type="text" value="${user.user_name }" disabled="disabled">
                                </div>
                                <div class="field">
                                        <label>NickName</label>
                                        <input type="text" value="${user.user_username }" disabled="disabled">
                                    </div>
                                <div class="field">
                                    <label>E-mail</label>
                                    <input type="text" value="${user.user_email }" disabled="disabled">
                                </div>
                            </div>
                        </form>
                        <h3 class="ui horizontal divider header"> OJ ID </h3>
                        <div class="ui form" id="myform">
                            <div class="fields">
                                <div class="field">
                                    <label>HduOJ</label>
                                    <input type="text" placeholder="HduOJ" id="hdu" value="${user.user_hdu }">
                                </div>
                                <div class="field">
                                    <label>Codeforces</label>
                                    <input type="text" placeholder="Codeforces" id="codeforces" value="${user.user_cf }">
                                </div>
                                <div class="field">
                                    <label>POJ</label>
                                    <input type="text" placeholder="POJ" id="poj" value="${user.user_poj }">
                                </div>
                            </div>
                            
                            <div class="fields">
                            	<div class="field">
                                    <label>Vjudge</label>
                                    <input type="text" placeholder="Vjudge" disabled="disabled">
                                </div>
                                <div class="field">
                                    <label>Openjudge</label>
                                    <input type="text" placeholder="Openjudge" disabled="disabled">
                                </div>
                                <div class="field">
                                    <label>zzuliOJ</label>
                                    <input type="text" placeholder="zzuliOJ" disabled="disabled">
                                </div>
                            </div>
                            <div class="fields">
                                <div class="field">
                                    <label>ZOJ</label>
                                    <input type="text" placeholder="ZOJ" disabled="disabled">
                                </div>
                                <div class="field">
                                    <label>nyistOJ</label>
                                    <input type="text" placeholder="nyistOJ" disabled="disabled">
                                </div>
                                <div class="field">
                                    <label>zznuOJ</label>
                                    <input type="text" placeholder="zznuOJ" disabled="disabled">
                                </div>
                            </div>
                            <h3 class="ui horizontal divider header">
                            	<c:if test="${!empty session_user }"><%-- 判断用户是否登录 --%>
                            	 	<c:if test="${session_user.user_id==user.user_id }"><%-- 判断访问是否是本人访问自己的主页 --%>
                            			<button class="ui button blue" id="submit">Submit</button>
                            		</c:if>
                            	</c:if>
                            </h3>
                        </div>
                        <div class="ui modal">
                            <i class="close icon"></i>
                            <div class="header">
								提交重要信息
                            </div>
                            <div class="image content">
                                <div class="ui medium image">
                                    <c:if test="${empty user.user_photourl }">
                                		<img src="${pageContext.request.contextPath }/img/steve.jpg">
                                	</c:if>
                                    <c:if test="${!empty user.user_photourl }">
                                   		<img src="${pageContext.request.contextPath }/${user.user_photourl}">
                                    </c:if>
                                </div>
                                <div class="description">
                                    <div class="ui content">
                                    	<form id="myform1" class="ui large form" action="${pageContext.request.contextPath }/user?method=uploadInfo" method="post" enctype="multipart/form-data">
						                    <div class="field">
												<label>上传头像</label>
												<div class="ui left icon input">
													<input type="file" name="user_photourl">
													<i class="icon user"></i>
												</div>
											</div>
											<div class="field">
												<label>HDU</label>
												<div class="ui left icon input">
													<input type="text" id="hdu1" name="user_hdu" placeholder="your hdu id">
												</div>
											</div>
											<div class="field">
												<label>CodeForces</label>
												<div class="ui left icon input">
													<input type="text" id="codeforces1" name="user_cf" placeholder="your codeforces id">
												</div>
											</div>
											<div class="field">
												<label>POJ</label>
												<div class="ui left icon input">
													<input type="text" id="poj1" name="user_poj" placeholder="your poj id">
												</div>
											</div>
                                    	</form>
                                    </div>
                                </div>
                            </div>
                            <div class="actions">
                                <div class="ui black deny button">
									取消
                                </div>
                                <a href="javascript:void(0);" onclick="submitForm()">
	                                <span class="ui positive right labeled icon button">
										确认无误
	                                    <i class="checkmark icon"></i>
	                                </span>
                                </a>
                            </div>
                        </div>
                    </div>
                    <script type="text/javascript">
	                    $('#submit').click(function () {
	                        $('.ui.modal').modal('show');
	                        var hduEle = document.getElementById("hdu").value;
	                        document.getElementById("hdu1").value=hduEle;
	                        var pojEle = document.getElementById("poj").value;
	                        document.getElementById("poj1").value=pojEle;
	                        var cfEle = document.getElementById("codeforces").value;
	                        document.getElementById("codeforces1").value=cfEle;
	                    })
	                    function submitForm(){
	                    	$("#myform1").submit();
	                    }
                    </script>
                </div>
            </div>
            <h4 class="ui horizontal divider header"><i class="bar chart icon"></i> Specifications </h4>
            <div id="main" style="width: 100%;height:800px; margin: auto"></div>
            <script type="text/javascript">
            	data = eval("("+"${data}"+")");
            
                var myChart = echarts.init(document.getElementById('main'));

                var dateList = data.map(function (item) {
                    return item[0];
                });
                var valueList = data.map(function (item) {
                    return item[1];
                });

                option = {
                    visualMap: [{
                        show: false,
                        type: 'continuous',
                        seriesIndex: 0,
                        min: 0,
                        max: 400
                    }],
                    title: [{
                        left: 'center',
                        text: "${user.user_name}"+' 最近三个月刷题数'
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
        </div>
        <!-- 主体内容结束 -->
    </div>
</body>
</html>