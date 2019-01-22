<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>注册</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" type="text/css" href="css/icon.css" />
    <link rel="stylesheet" type="text/css" href="css/table.min.css" />
    <link rel="stylesheet" type="text/css" href="css/semantic.min.css" />
    <link rel="stylesheet" type="text/css" href="css/grid.css">
    <script src="js/echarts.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/semantic.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/jquery.validate.min.js" type="text/javascript" ></script>
    <style type="text/css">
        body {
            background-color: #DADADA;
        }
        body>.grid {
            height: 100%;
        }
        .image {
            margin-top: -100px;
        }
        .column {
            max-width: 450px;
        }
        .error{
        	color:red;
        }
    </style>
    <script>
    $.validator.addMethod("checkEmailOnly",function(value, element, param){
		var flag = false;
		$.ajax({
			"async":false,
			"data":{"checkEmail":value},
			"dataType":"json",
			"type":"POST",
			"url":"${pageContext.request.contextPath}/user?method=checkEmail",
			"success":function(data){
				flag = data.isok;
			}
		});
		return flag;
	});
    $.validator.addMethod("checkUsernameOnly",function(value, element, param){
		var flag = false;
		$.ajax({
			"async":false,
			"data":{"checkUsername":value},
			"dataType":"json",
			"type":"POST",
			"url":"${pageContext.request.contextPath}/user?method=checkUsername",
			"success":function(data){
				flag = data.isok;
			}
		});
		return flag;
	});
       $().ready(function(){
    	  $("#myform").validate({
    		  rules:{
    			  "user_username":{
    				"required":true,
    				"checkUsernameOnly":true
    			  },
    			  "user_name":{
    				  "required":true,
    				  "rangelength":[2,6]
    			  },
    			  "user_email":{
    				  "required":true,
    				  "email":true,
    				  "checkEmailOnly":true
    			  },
    			  "user_password":{
    				  "required":true,
    				  "rangelength":[6,12]
    			  },
    			  "repassword":{
    				  "required":true,
  					  "equalTo":"#user_password"
    			  }
    		  },
    		  messages:{
    			  "user_username":{
      				"required":"请填写账号",
      				"checkUsernameOnly":"该账号已被注册"
      			  },
    			  "user_name":{
    				  "required":"请填写用户名",
    				  "rangelength":"请填写真实姓名"
    			  },
    			  "user_email":{
    				  "required":"请填写邮箱",
    				  "email":"请正确填写邮箱",
    				  "checkEmailOnly":"邮箱已被注册"
    			  },
    			  "user_password":{
    				  "required":"请输入密码",
    				  "rangelength":"密码的长度在6-12位"
    			  },
    			  "repassword":{
    				  "required":"请确认密码",
  					  "equalTo":"密码不一致"
    			  }
    		  }
    	  }) 
       });
	</script>
	<script type="text/javascript">
		function sumbitForm() {
			$("#myform").submit();
		}
	</script>
</head>
<body>
    <div class="ui middle aligned center aligned grid">
        <div class="column">
            <h2 class="ui teal image header">
                <img src="${pageContext.request.contextPath }/img/henu.jpg" class="image">
                <span class="content">
                    Register to your account
                </span>
            </h2>
            <form id="myform" class="ui large form" action="${pageContext.request.contextPath }/user?method=userRegister" method="post">
                <div class="ui stacked segment">
                	<div class="field">
                        <div class="ui left icon input">
                            <i class="user icon"></i>
                            <input type="text" name="user_username" id="user_username" placeholder="UserName">
                        </div>
                    </div>
                	<div class="field">
                        <div class="ui left icon input">
                            <i class="lock icon"></i>
                            <input type="password" name="user_password" id="user_password" placeholder="Password">
                        </div>
                    </div>
                    <div class="field">
                        <div class="ui left icon input">
                            <i class="lock icon"></i>
                            <input type="password" name="repassword" id="repassword" placeholder="Password again">
                        </div>
                    </div>
                    <div class="field">
                        <div class="ui left icon input">
                            <i class="user icon"></i>
                            <input type="text" name="user_name" id="user_name" placeholder="RealName">
                        </div>
                    </div>
                    <div class="field">
                        <div class="ui left icon input">
                            <i class="envelope outline icon"></i>
                            <input type="text" name="user_email" id="user_email" placeholder="E-mail address">
                        </div>
                    </div>
                    <a href="javascript:void(0);" onclick="sumbitForm()">
                        <span class="ui fluid large teal submit button">Register</span>
                    </a>
                </div>
                <div class="ui error message"></div>
            </form>
            <div class="ui message">
                Already have an account? <a href="${pageContext.request.contextPath }/login.jsp">Log-in</a>
            </div>
        </div>
    </div>
</body>

</html>