<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; utf-8">
    <title>Title</title>


</head>
<body>
单文件上传:<br/>
<form method="post" action="/upload" enctype="multipart/form-data">
    <input type="file" name = "file"/>
    <input type="submit" value="上传文件"/>
</form>

<br/>多文件上传:<br/>
<form method="post" action="/uploads" enctype="multipart/form-data">
    <input type="file" name = "file"/><br/>
    <input type="file" name = "file"/><br/>
    <input type="file" name = "file"/><br/>
    <input type="submit" value="上传文件"/>
</form>
</body>
</html>