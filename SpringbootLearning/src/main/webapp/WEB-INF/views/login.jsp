<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; utf-8">
    <title>Title</title>


</head>
<body>
    <form method="get" action="/user/login">
         用户名: <input type="text" name = "name"/><br/>
         密码: <input type="text" name="pwd"/>
         <input type="submit" value="登陆"/>
    </form>
</body>
</html>