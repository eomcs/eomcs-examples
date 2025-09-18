<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String[] names = {"홍길동", "임꺽정", "유관순"};
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex04</title>
</head>
<body>
<h1>스크립트릿(scriptlet) 응용</h1>
<ul>
<%
for (String name : names) {
  out.println("<li>" + name + "</li>");
}
%>
</ul>
</body>
</html>

