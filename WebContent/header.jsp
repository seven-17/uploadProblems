<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/icon.css" />
<link rel="stylesheet" type="text/css" href="css/table.min.css" />
<link rel="stylesheet" type="text/css" href="css/semantic.min.css" />
<script src="js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/echarts.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/semantic.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="ui top attached tabular menu">
	<a class="item" href="${pageContext.request.contextPath }/index.jsp">首页</a>
	<a class="item" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=1">动态</a>
	<a class="item" href="${pageContext.request.contextPath }/showData?method=showRank">排名</a>

	<c:if test="${empty session_user }">
		<a class="item" href="${pageContext.request.contextPath }/login.jsp">登录</a>
	</c:if>
	<c:if test="${!empty session_user }">
		<a class="item" href="${pageContext.request.contextPath }/user?method=getUserInfo&user_id=${session_user.user_id}">个人中心</a>
	</c:if>
</div>
</body>
</html>