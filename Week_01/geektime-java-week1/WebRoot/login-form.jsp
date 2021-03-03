<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<head>
<jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
	<title>用户注册页面</title>
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
</head>
<body>
	<div class="container">
		<form class="form-signin" action="${contextPath}/user/register" method="post">
			<h1 class="h3 mb-3 font-weight-normal">用户注册</h1>
			<label for="name" class="sr-only">姓名</label> <input type="text" id="name" name="name" class="form-control" placeholder="请输入用户名" required autofocus> 
			<label for="email" class="sr-only">邮箱</label> <input type="text" id="email" name="email" class="form-control" placeholder="请输入用户邮箱" required>
			<label for="phone" class="sr-only">电话号</label> <input type="text" id="phone" name="phone" class="form-control" placeholder="请输入用户电话" required>
		
			<button class="btn btn-lg btn-primary btn-block" type="submit">注册</button>
			<p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>
		</form>
	</div>
</body>