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
    <link rel="stylesheet" type="text/css" href="css/search.min.css">
    <script src="js/echarts.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/semantic.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/search.min.js" type="text/javascript" charset="utf-8"></script>
    <style type="text/css">
    	.extra span{
    		font-size:18px;
    	}
    	tr td{
    		text-align:center !important;
    	}
    	tr th{
    		text-align:center !important;
    	}
    </style>
    <script type="text/javascript">
    	function submitForm(value) {
    		document.getElementById("myform").action="${pageContext.request.contextPath }/showData?method=searchDynamic&currentPage="+value;
    		$("#myform").submit();
    	}
    </script>
</head>

<body>
    <div class="main ui container" style="padding: 30px;">
        <!-- 导航开始 -->
		<div class="ui top attached tabular menu">
			<a class="item" href="${pageContext.request.contextPath }/index.jsp">首页</a>
			<a class="item active" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=1">动态</a>
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
        	<div class="ui container vertical aligned ">
        		<div class="ui container right aligned category search">
					<form id="myform" action="${pageContext.request.contextPath }/showData?method=searchDynamic&currentPage=1" method="post">
						<div class="ui icon input">
							<input class="prompt" type="text" name="key" placeholder="关键字……" value="${lastSearch }">
							<i class="search icon"></i>
						</div>
						<div class="ui icon input">
							<input type="submit" value="搜索" style="cursor: pointer;">
							<i class="hand pointer icon"></i>
						</div>
					</form>
					<div class="results"></div>
				</div>
				
				<table class="ui celled table">
                    <thead>
                        <tr>
                            <th>姓名</th>
                            <th>昵称</th>
                            <th>OJ System</th>
                            <th>题目编号</th>
                            <th>时间</th>
                            <th>状态</th>
                        </tr>
                    </thead>
                    <tbody>
                    	<c:forEach items="${pageBean.list }" var="item">
							<tr>
	                            <td>
	                            	<a href="${pageContext.request.contextPath }/user?method=getUserInfo&user_id=${item.user_id}">${item.user_name }</a>
	                            </td>
	                            <td>${item.user_username }</td>
	                            <td>${item.OJ_system }</td>
	                            <td>
	                                <a href="${item.problem_url }" target="_blank" rel="noopener noreferrer">${item.problem_id }</a>
	                            </td>
	                            <td>${item.submit_time }</td>
	                            <c:if test="${item.submit_status=='Accepted' }">
					        		<td class="positive"><i class="icon checkmark"></i>${item.submit_status }</td>
					        	</c:if>
					        	<c:if test="${item.submit_status!='Accepted' }">
					        		<td class="negative"><i class="icon close"></i>${item.submit_status }</td>
					        	</c:if>
	                        </tr>
						</c:forEach>
                    </tbody>
                </table>
				
				<%-- 分页查询 --%>
				<div class="ui center aligned container" >
					<div class="ui pagination menu" style="margin: 0 auto;">
					
						<!-- 向前翻页 -->
						<c:if test="${pageBean.currentPage==1 }">
							<a class="icon item disabled" href="javascript:void(0);">
								<i class="left chevron icon"></i>
							</a>
						</c:if>
						<c:if test="${pageBean.currentPage!=1 }">
							<c:if test="${empty lastSearch }"><%-- 是否搜索 --%>
								<a class="icon item" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=${pageBean.currentPage-1 }">
									<i class="left chevron icon"></i>
								</a>
							</c:if>
							<c:if test="${!empty lastSearch }">
								<a class="icon item" onclick="submitForm(${pageBean.currentPage-1 })" href="javascript:void(0);">
									<i class="left chevron icon"></i>
								</a>
							</c:if>
						</c:if>
						
						
						<c:if test="${pageBean.currentPage+6>pageBean.totalPage }">
						
							<!-- 测试ok -->
							<c:if test="${pageBean.totalPage-6<1 }">
								<c:forEach begin="1" end="${pageBean.totalPage }" varStatus="status">
									
									<c:if test="${pageBean.currentPage==status.count }">
										<a class="item active" href="javascript:void(0);">${pageBean.currentPage }</a>
									</c:if>
									<c:if test="${pageBean.currentPage!=status.count }">
										<c:if test="${empty lastSearch }">
											<a class="item" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=${status.count }">${status.count }</a>
										</c:if>
										<c:if test="${!empty lastSearch }">
											<a class="item" onclick="submitForm(${status.count })" href="javascript:void(0);">${status.count }</a>
										</c:if>
									</c:if>
								
								</c:forEach>
							</c:if>
							
							<!-- 测试ok -->
							<c:if test="${pageBean.totalPage-6>=1 }">
								<c:forEach begin="${pageBean.totalPage-6 }" end="${pageBean.totalPage }" varStatus="status">
									<c:if test="${status.count==1 }">
										<c:if test="${empty lastSearch }">
											<a class="item" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=1">1</a>
											<a class="item" href="javascript:void(0);">...</a>
										</c:if>
										<c:if test="${!empty lastSearch }">
											<a class="item" onclick="submitForm(1)" href="javascript:void(0);">1</a>
											<a class="item" href="javascript:void(0);">...</a>
										</c:if>
									</c:if>
									<c:if test="${status.count!=1 }">
										<c:if test="${pageBean.currentPage==status.count+pageBean.totalPage-7 }">
											<a class="item active" href="javascript:void(0);">${pageBean.currentPage }</a>
										</c:if>
										<c:if test="${pageBean.currentPage!=status.count+pageBean.totalPage-7 }">
											<c:if test="${empty lastSearch }">
												<a class="item" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=${status.count+pageBean.totalPage-7 }">${status.count+pageBean.totalPage-7 }</a>
											</c:if>
											<c:if test="${!empty lastSearch }">
												<a class="item" onclick="submitForm(${status.count+pageBean.totalPage-7 })" href="javascript:void(0);">${status.count+pageBean.totalPage-7 }</a>
											</c:if>
										</c:if>
									</c:if>
								</c:forEach>
							</c:if>
							
						</c:if>
						
						
						
						<c:if test="${pageBean.currentPage+6<=pageBean.totalPage}">
							<c:forEach begin="${pageBean.currentPage }" end="${pageBean.currentPage+6 }" varStatus="status">
								<c:if test="${status.count==1 }"><!-- 成功输出 -->
									<c:if test="${pageBean.currentPage!=1 }">
										<a class="item" onclick="submitForm(1)" href="javascript:void(0);">1</a>
										<a class="item" href="javascript:void(0);">...</a>
									</c:if>
									<a class="item active" href="javascript:void(0);">${status.count+pageBean.currentPage-1 }</a>
								</c:if>
								<c:if test="${status.count!=1 }">
									<c:if test="${status.count>6 }">
										<c:if test="${empty lastSearch }">
											<a class="item" href="javascript:void(0);">...</a>
											<a class="item" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=${pageBean.totalPage }">${pageBean.totalPage }</a>
										</c:if>
										<c:if test="${!empty lastSearch }">
											<a class="item" href="javascript:void(0);">...</a>
											<a class="item" onclick="submitForm(${pageBean.totalPage })" href="javascript:void(0);">${pageBean.totalPage }</a>
										</c:if>
									</c:if>
									<c:if test="${status.count<=6 }">
										<c:if test="${empty lastSearch }">
											<a class="item" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=${status.count+pageBean.currentPage-1 }">${status.count+pageBean.currentPage-1 }</a>
										</c:if>
										<c:if test="${!empty lastSearch }">
											<a class="item" onclick="submitForm(${status.count+pageBean.currentPage-1 })" href="javascript:void(0);">${status.count+pageBean.currentPage-1 }</a>
										</c:if>
									</c:if>
								</c:if>
							</c:forEach>
						</c:if>
							
						<!-- 向后翻页 -->
						<c:if test="${pageBean.currentPage==pageBean.totalPage }">
							<a class="icon item disabled" href="javascript:void(0);">
								<i class="right chevron icon"></i>
							</a>
						</c:if>
						<c:if test="${pageBean.currentPage!=pageBean.totalPage }">
							<c:if test="${empty lastSearch }">
								<a class="icon item" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=${pageBean.currentPage+1 }">
									<i class="right chevron icon"></i>
								</a>
							</c:if>
							<c:if test="${!empty lastSearch }">
								<a class="icon item" onclick="submitForm(${pageBean.currentPage+1 })" href="javascript:void(0);">
									<i class="right chevron icon"></i>
								</a>
							</c:if>
						</c:if>
						
					</div>
				</div>
				<%-- 分页结束 --%>
				
        	</div>
        </div>
    </div>
</body>
</html>