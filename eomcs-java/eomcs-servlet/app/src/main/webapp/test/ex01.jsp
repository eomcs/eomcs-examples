<%@page import="java.sql.Date"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>외부 라이브러리 사용</title>
</head>
<body>
<h1>java-web-library.jar의 클래스 사용하기</h1>
<jsp:useBean id="calc" class="bitcamp.Calculator" scope="page"/>
10 + 20 = ${calc.plus(10, 20)}<br>


<%
HashMap<String,Object> map = new HashMap<>();
map.put("v1", 100);
map.put("v1", "String");
map.put("v1", Date.valueOf("2019-4.26"));
map.put("v1", true);
map.put("v1", 3.14f);
map.put("v1", null);
map.put("오호라...^^   ㅋㅋ", "aaa");

out.write(map.get("오호라...^^   ㅋㅋ"));

int i = 100;
//int i = 200;

%>




</body>
</html>