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
    <script src="js/echarts.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/semantic.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
    <div class="main ui container" style="padding: 30px;">
        <!-- 导航开始 -->
		<div class="ui top attached tabular menu">
			<a class="item" href="${pageContext.request.contextPath }/index.jsp">首页</a>
			<a class="item" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=1">动态</a>
			<a class="item active" href="${pageContext.request.contextPath }/showData?method=showRank">排名</a>
		
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
			<table class="ui very basic table">
				<thead>
					<tr>
						<th>Rank</th>
						<th>Name</th>
						<th>A Week</th>
						<th>A Month</th>
						<th>Total</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${rank }" var="rankBean" varStatus="status">
						<tr>
							<td style="padding-left:10px;">${status.count }</td>
							<td>
								<a class="ui red label">ACM</a>
								<a href="${pageContext.request.contextPath }/user?method=getUserInfo&user_id=${rankBean.user_id}">${rankBean.user_name }</a>
							</td>
							<td><b>${rankBean.aweekday }</b></td>
							<td><b>${rankBean.amonth }</b></td>
							<td><b>${rankBean.total }</b></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
        <!-- 主体内容结束 -->
    </div>
</body>
</html>