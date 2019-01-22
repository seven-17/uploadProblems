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
	<script type="text/javascript">
		function submitForm() {
			$("#myform").submit();
		}
	</script>
</head>
<body>
	<div class="main ui container" style="padding: 30px;">
		<div class="ui top attached tabular menu">
			<a class="item" href="${pageContext.request.contextPath }/index.jsp">首页</a>
			<a class="item" href="${pageContext.request.contextPath }/showData?method=showDynamic&currentPage=1">动态</a>
			<a class="item" href="${pageContext.request.contextPath }/showData?method=showRank">排名</a>
		
			<c:if test="${empty session_user }">
				<a class="item active" href="${pageContext.request.contextPath }/login.jsp">登录</a>
			</c:if>
			<c:if test="${!empty session_user }">
				<a class="item" href="${pageContext.request.contextPath }/user?method=getUserInfo&user_id=${session_user.user_id}">个人中心</a>
			</c:if>
		</div>
		<div class="ui bottom attached segment">
			<div class="ui placeholder segment ">
				<div class="ui two column very relaxed stackable grid">
					<div class="column">
						<form id="myform" action="${pageContext.request.contextPath }/user?method=userLogin" method="post">
							<div style="text-align: center;color: red;">${errorInfo }</div>
							<div class="ui form">
								<div class="field">
									<label>用户名</label>
									<div class="ui left icon input">
										<input type="text" name="user_username" id="user_username" placeholder="Username">
										<i class="icon user"></i>
									</div>
								</div>
								<div class="field">
									<label>密码</label>
									<div class="ui left icon input">
										<input type="password" name="user_password" id="user_password" placeholder="Password">
										<i class="lock icon"></i>
									</div>
								</div>
								<div class="ui blue submit button" onclick="submitForm()">Login</div>
							</div>
						</form>
					</div>
					<div class="middle aligned column">
						<a href="${pageContext.request.contextPath }/register.jsp">
							<span class="ui big button" id="resgister">
								<i class="signup icon"></i>Resgister  
							</span>
						</a>
					</div>
				</div>
				<div class="ui vertical divider">Or</div>
			</div>
		</div>
	</div>
</body>
</html>